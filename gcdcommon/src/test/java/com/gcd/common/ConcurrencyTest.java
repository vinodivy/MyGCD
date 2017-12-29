package com.gcd.common;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.gcd.common.util.SessionController;

public class ConcurrencyTest {
	
	private static Logger logger = Logger.getLogger(SessionController.class);

	@Test
	public void testTwentyConcurrentUsers() {
		SessionController sessionController = new SessionController();
		for (int i = 1; i < 30; i++) {
			sessionController.watchUserSession("User " + i);
			logger.info("User " + i);
		}
	}

}
