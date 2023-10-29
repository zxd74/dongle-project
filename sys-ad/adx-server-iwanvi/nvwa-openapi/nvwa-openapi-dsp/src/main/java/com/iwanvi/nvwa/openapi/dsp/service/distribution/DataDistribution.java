package com.iwanvi.nvwa.openapi.dsp.service.distribution;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DataDistribution {
	
	private static DataDistribution instance = null;
	
	private static final int TIME_OUT = 120;
	
	private DataDistribution() {
		
	}
	
	synchronized public static DataDistribution getInstance() {
		if(instance == null) {
			instance = new DataDistribution();
		}
		return instance;
	}
	
	public List<Customer> doWork(List<Customer> raw){
		List<Customer> result = null;
		try {
			if(raw == null || raw.isEmpty()) {
				throw new IllegalArgumentException("DataDistribution.dowWork raw can't be empty.");
			}
			
			int threadNum = raw.size();
			ExecutorService executorService = Executors.newFixedThreadPool(threadNum);
			CompletionService<Customer> completionService = new ExecutorCompletionService<>(executorService);
			for (int i = 0; i < threadNum; i++) {
				completionService.submit(new Task().setCustomer(raw.get(i)));
			}
			result = new ArrayList<>();
			for (int i = 0; i < threadNum; i++) {
				result.add(completionService.take().get(TIME_OUT, TimeUnit.MILLISECONDS));
			}
			executorService.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static void main(String[] args) {
		try {
			long l1 = System.currentTimeMillis();
			List<Customer> raw = new ArrayList<>();
			raw.add(new Customer(1, 80, "zhongmeng", "http://182.92.173.117:9140/adx/get/creative?t=1", "{}"));
			raw.add(new Customer(2, 30, "aipu", "182.92.173.117:9140/adx/get/creative?t=2", "{}"));
			raw.add(new Customer(3, 30, "zzsj", "http://182.92.173.117:9140/adx/get/creative?t=3", "{}"));
			raw.add(new Customer(4, 30, "imageter", "http://182.92.173.117:9149/adx/get/creative?t=4", "{}"));
			
			List<Customer> result = null;
			for (int i = 0; i < 10; i++) {
				result = DataDistribution.getInstance().doWork(raw);
				if(result == null || result.isEmpty()) {
					return;
				}
				Customer customer = null;
				for (int j = 0; j < result.size(); j++) {
					customer = result.get(j);
					
					System.out.println(customer.toString());
				}
			}
			
			
			System.out.println("--------complete elapsed------" + (System.currentTimeMillis() - l1));
			
			System.exit(-1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
