package com.gcd.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gcd.common.bean.InputNumber;
import com.gcd.common.messaging.MessageSender;
import com.gcd.dao.NumberDao;

import com.gcd.common.util.SessionController;

@Service("NumberService")
public class NumberServiceImpl implements NumberService {
	

	@Autowired
	private NumberDao numberDao;
	
	@Autowired
	private MessageSender messageSender;
	
	@Autowired
	private SessionController sessionController;
	
	private static final Logger log = Logger.getLogger(NumberServiceImpl.class);

	
	public List<InputNumber> list(String key) {
		log.debug("Invoking the Number service list method..");
		sessionController.watchUserSession(key);
		System.out.println("Key from list "+key);
		//List<InputNumber> numberList = numberDao.list();
		List<InputNumber> numberList = numberDao.list();
		return numberList;
	}

	public void push(Integer i1, Integer i2, String key) {
		log.debug("Invoking the Number service push method..");				
		
		InputNumber number = new InputNumber();
		number.setNumber1(i1);
		number.setNumber2(i2);
		number.setUserkey(key);
		System.out.println("key from push "+key);
		
		sessionController.watchUserSession(key);
		//numberDao.push(number);	
		numberDao.push(number);	
		
		log.info("Pushing the numbers to Queue...");		
		messageSender.send(number,"GCDQueue");	
	}
}