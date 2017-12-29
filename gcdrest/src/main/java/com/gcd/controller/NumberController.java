package com.gcd.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gcd.common.bean.InputNumber;
import com.gcd.service.NumberService;

@RestController
public class NumberController {

	@Autowired
	private NumberService numberService;
	
	private static final Logger log = Logger.getLogger(NumberController.class);

	@RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json")
	public ResponseEntity<List<InputNumber>> list(@RequestHeader(value="key" , required=false) String key) {
		
		log.debug("Invoking rest method push..");

		HttpHeaders headers = new HttpHeaders();
		List<InputNumber> numberList = numberService.list(key);

		if (numberList == null) {
			log.debug("There were no numbers found in the database..");
			return new ResponseEntity<List<InputNumber>>(HttpStatus.NOT_FOUND);
		}
		headers.add("Number Of Records Found", String.valueOf(numberList.size()));
		return new ResponseEntity<List<InputNumber>>(numberList, headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/push", method = RequestMethod.POST,  headers = "Accept=application/json")
	public ResponseEntity<String> push(@RequestParam Integer i1, @RequestParam Integer i2, 
	                                   @RequestHeader(value="key" , required=false) String key) {
		log.debug("Invoking the push method...");
		HttpHeaders headers = new HttpHeaders();
		if (i1==0||i2==0) {
			log.debug("Numbers cannot be zero..");
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		numberService.push(i1, i2, key);
		return new ResponseEntity<String>("Success", headers, HttpStatus.CREATED);
	}
}