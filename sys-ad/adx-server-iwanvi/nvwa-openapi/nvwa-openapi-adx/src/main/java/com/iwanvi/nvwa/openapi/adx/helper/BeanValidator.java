/*
 * Copyright 2019 iwanvi.com All right reserved.
 */
package com.iwanvi.nvwa.openapi.adx.helper;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * @author wangwp
 */
@Component
public class BeanValidator {
	@Autowired
	private Validator validator;

	public void validate(Object bean) {
		Set<ConstraintViolation<Object>> constraintViolations = validator.validate(bean);

		if (constraintViolations != null && !constraintViolations.isEmpty()) {
			String errorMsg = constraintViolations.iterator().next().getMessage();
			throw new RuntimeException(errorMsg);
		}
	}

	public void validate(Object bean, Class<?>... groups) {
		Set<ConstraintViolation<Object>> constraintViolations = validator.validate(bean, groups);

		if (constraintViolations != null && !constraintViolations.isEmpty()) {
			String errorMsg = constraintViolations.iterator().next().getMessage();
			throw new RuntimeException(errorMsg);
		}
	}
}
