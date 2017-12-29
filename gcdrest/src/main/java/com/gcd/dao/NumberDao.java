package com.gcd.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.gcd.common.bean.InputNumber;

@Component
public class NumberDao {
	
	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public void push(InputNumber inputNumber){
		System.out.println("EM value is %%%%%%%% "+em);
		em.persist(inputNumber);
	}
	
	@Transactional
	public List<InputNumber> list(){
		return em.createQuery("SELECT number1, number2 from InputNumber").getResultList();
	}
	
	
	
	
}