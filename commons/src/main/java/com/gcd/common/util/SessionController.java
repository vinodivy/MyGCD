package com.gcd.common.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class SessionController {

	private static final long DURATION = TimeUnit.MINUTES.toMillis(1); 
	private static final Semaphore semaphore = new Semaphore(20);
	private static Map<String, Long> userSession = new HashMap<String, Long>();
	private static final Logger log = Logger.getLogger(SessionController.class);

	
	/*
	 * Stores user key and the current time in usersession map. A timer runs in the background,
	 * which will release a user session if its inactive after 3 mins.
	 * 
	 * Every user is identified with a key.
	 * */
	public void watchUserSession(String key) {
		
		log.debug("key is "+key);

		synchronized (userSession) {
			for (Iterator<Map.Entry<String, Long>> it = userSession.entrySet().iterator(); it.hasNext();) 
			{
				Entry<String, Long> entry = it.next();
				if (entry.getKey().equals(key)) {
					userSession.put(key, System.currentTimeMillis());
					return;
				}
			}
		}

		// hold on until you get the lock
		try {
			log.debug("Fetching lock...");
			semaphore.acquire();
			log.debug("Lock acquired...");
		} catch (InterruptedException e) {
			log.error("No of concurrent users has exceeded 20..");
		}
		synchronized (userSession) {
			userSession.put(key, System.currentTimeMillis());
		}
		
		Timer time = new Timer();
		ReleaseLockTask st = new ReleaseLockTask();
		time.schedule(st, 0, 5000);
	}

	static class ReleaseLockTask extends TimerTask {
		public void run() {
			synchronized (userSession) {
				for (Iterator<Map.Entry<String, Long>> it = userSession.entrySet().iterator(); it.hasNext();) {
					Entry<String, Long> entry = it.next();
					if ((System.currentTimeMillis() - entry.getValue()) > DURATION) {
						it.remove();
						semaphore.release();
					}
				}
			}

		}
	}

}

