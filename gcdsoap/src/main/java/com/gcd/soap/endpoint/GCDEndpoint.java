package com.gcd.soap.endpoint;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.gcd.soap.service.Gcdservice;

/** Soap endpoint for the web service.
 * @author Vinod
 *
 */
@Endpoint
public class GCDEndpoint
{
private static final String TARGET_NAMESPACE = "http://gcd.com/soap/endpoint";
	
	@Autowired
	Gcdservice gcdService;

	
	/**
	 * Handles the request for getting gcd from the queue
	 * @return the gcd.
	 * @return 0 if queue is empty
	 */
	@PayloadRoot(localPart="gcdRequest", namespace=TARGET_NAMESPACE)
	public @ResponsePayload GcdResponse gcd(){
		int gcd = gcdService.gcd("GCDQueue");
		GcdResponse response = new GcdResponse();
		response.setGcd(gcd);
		return response;
	}
	
	
	/**
	 * Returns a list of all the computed greatest common divisors from a database. 
	 * @return List of int - the gcd
	 */
	@PayloadRoot(localPart="gcdListRequest", namespace=TARGET_NAMESPACE)
	public @ResponsePayload GcdListResponse gcdList(){
		List<Integer> gcdList = this.gcdService.gcdList();
		GcdListResponse response = new GcdListResponse();
		response.getGcd().addAll(gcdList);
		return response;
	}
	
	
	/**
	 * Returns the sum of all computed greatest common divisors from a database.
	 * 
	 * @return int - sum of GCD
	 */
	@PayloadRoot(localPart="gcdSumRequest", namespace=TARGET_NAMESPACE)
	public @ResponsePayload GcdSumResponse gcdSum(){
		int gcdSum = this.gcdService.gcdSum();
		GcdSumResponse response = new GcdSumResponse();
		response.setGcd(gcdSum);
		return response;
	}
	
	
	public void setGcdSoapService(Gcdservice gcdService){
		this.gcdService = gcdService;
	}
}