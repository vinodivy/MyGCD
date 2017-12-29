package com.gcd.soap.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.gcd.common.bean.GCD;

@Component
public class GCDDao {

	@PersistenceContext
	private EntityManager em;

	@Transactional
	public void insertGCD(GCD gcd) {
		em.persist(gcd);
	}

	@Transactional
	public List<Integer> gcdList() {
		List<Integer> gcdList = em.createQuery("SELECT gcd from GCD").getResultList();
		if (gcdList.isEmpty()) {
			gcdList.add(0);
		}
		return gcdList;
	}
}
