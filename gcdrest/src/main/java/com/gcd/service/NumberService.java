package com.gcd.service;

import java.util.List;

import com.gcd.common.bean.InputNumber;

public interface NumberService {
	public List<InputNumber> list(String key);

	public void push(Integer i1, Integer i2, String key);
}
