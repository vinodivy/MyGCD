package com.gcd.soap.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gcd.soap.dao.GCDDao;
import com.gcd.common.messaging.MessageConsumer;
import com.gcd.common.bean.GCD;
import com.gcd.common.bean.InputNumber;

/**
 * Service class to fetch the GCD values from dao components
 * 
 * @author Vinod
 *
 */
@Component
public class Gcdservice {


	@Autowired
	GCDDao gcdDao;

	@Autowired
	private MessageConsumer messageConsumer;

	private static final Logger logger = Logger.getLogger(Gcdservice.class);
	
	/**
	 * Returns the last inserted gcd value from the queue.
	 * @return 0 if there are no numbers in queue.
	 */
	public int gcd(String queueName) {
		int gcd=0;

		logger.debug("Invoking gcd soap service...");
		InputNumber numbers = messageConsumer.getMessage(queueName);

		if (null != numbers){
			gcd = calculateGCD(numbers.getNumber1(), numbers.getNumber2());
		    GCD gcdmodel = new GCD();
		    gcdmodel.setGcd(gcd);
		    gcdmodel.setKey(numbers.getUserkey());
		    gcdDao.insertGCD(gcdmodel);
		    //gcdDao.insertGCD(gcdmodel);
		}
		else {
			logger.debug("The queue is empty and no numbers to process");
		}
		return gcd;
	}
	
	/*
	 * Returns the list of all gcd values from the database
	 * */
	/** Returns the list of all gcd values from the database
	 * @return list of all gcd values from database.
	 */
	public List<Integer> gcdList() {

		List<Integer> gcdList = new ArrayList<Integer>();
		gcdList = gcdDao.gcdList();
		return gcdList;

	}
	
	/*
	 * 
	 * */
	/** Returns the sum of all gcd values from the database
	 * @return 0 if no gcd values are found in database.
	 */
	public int gcdSum() {
		List<Integer> list = gcdList();
		int count = 0;
		/*int sum = list.stream().mapToInt(Integer::intValue).sum();
		return sum;*/
		for(Integer val : list){
			count+=val;
		}
		return count;
	}

	/**
	 * Calculates the GCD of two numbers
	 * 
	 * @param number1
	 * @param number2
	 * @return int the calculated GCD
	 */
	private int calculateGCD(int number1, int number2) {
		if (number2 == 0) {
			return number1;
		}
		return calculateGCD(number2, number1 % number2);
	}

}
