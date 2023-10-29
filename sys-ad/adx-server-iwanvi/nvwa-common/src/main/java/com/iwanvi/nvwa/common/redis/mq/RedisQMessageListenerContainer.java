package com.iwanvi.nvwa.common.redis.mq;

import com.iwanvi.nvwa.common.redis.server.RedisDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class RedisQMessageListenerContainer implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(RedisQMessageListenerContainer.class);

    @Autowired
    private RedisDao redisDao;
    private String queueName;
    private Executor taskExecutor;
    private Executor subscriptionExecutor;
    private RedisQMessageListener messageListener;
    private volatile boolean running = false;
    private volatile boolean initialized = false;

    public void start(){
        if(!running && initialized){
            running = true;
            subscriptionExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    while (running){
	                    try {
		                    final String message = redisDao.rightPop(queueName);
		                    if(message != null){
		                        taskExecutor.execute(new Runnable() {
		                            @Override
		                            public void run() {
		                                messageListener.onMessage(message);
		                            }
		                        });
		                    }else {
		                        try {
		                            Thread.sleep(10000);
		                        } catch (InterruptedException e) {
		                            e.printStackTrace();
		                        }
		                    }
	                    } catch (Exception e) {
		                    logger.info("-------------------can not connect to redis--------------------");
	                    }
                    }
                }
            });

            logger.info("Started listening for RedisQ messages, queueName: {}", queueName);
        }
    }

    public void stop(){
        running = false;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if(taskExecutor == null){
            taskExecutor = Executors.newCachedThreadPool();
        }
        if(subscriptionExecutor == null){
            subscriptionExecutor = taskExecutor;
        }
        initialized = true;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public void setTaskExecutor(Executor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    public void setSubscriptionExecutor(Executor subscriptionExecutor) {
        this.subscriptionExecutor = subscriptionExecutor;
    }

    public void setMessageListener(RedisQMessageListener messageListener) {
        this.messageListener = messageListener;
    }
}
