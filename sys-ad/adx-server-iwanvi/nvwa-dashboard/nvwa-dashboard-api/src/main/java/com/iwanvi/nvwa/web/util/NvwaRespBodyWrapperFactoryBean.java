package com.iwanvi.nvwa.web.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

@Configuration
public class NvwaRespBodyWrapperFactoryBean implements InitializingBean {
	@Autowired
	private RequestMappingHandlerAdapter adapter;

	@Override
	public void afterPropertiesSet() throws Exception {
		List<HandlerMethodReturnValueHandler> returnValueHandlers = adapter.getReturnValueHandlers();
		List<HandlerMethodReturnValueHandler> handlers = new ArrayList<>(returnValueHandlers);

		for (int i = 0; i < returnValueHandlers.size(); i++) {
			HandlerMethodReturnValueHandler handler = returnValueHandlers.get(i);
			if (handler instanceof RequestResponseBodyMethodProcessor) {
				NvwaRespBodyWrapper wrapper = new NvwaRespBodyWrapper(handler);
				handlers.set(i, wrapper);
				break;
			}
		}
		adapter.setReturnValueHandlers(handlers);
	}

}
