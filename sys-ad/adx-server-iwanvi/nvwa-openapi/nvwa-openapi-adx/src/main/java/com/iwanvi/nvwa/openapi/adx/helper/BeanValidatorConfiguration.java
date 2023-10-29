/*
 * Copyright 2019 iwanvi.com All right reserved.
 */
package com.iwanvi.nvwa.openapi.adx.helper;

import javax.validation.Validation;
import javax.validation.Validator;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author wangwp
 */
@Configuration
public class BeanValidatorConfiguration {

	@Bean
	public Validator validator() {
		Validator validator = Validation.byProvider(HibernateValidator.class).configure().failFast(true)
				.buildValidatorFactory().getValidator();
		return validator;
	}
}
