package com.dongle.sys.sec.kill.service;

import com.dongle.sys.sec.kill.model.User;

import java.util.concurrent.CountDownLatch;

public class UserService implements Runnable{

    private User user;
    private SecKillService service;
    private CountDownLatch countDownLatch;

    public UserService(User user, SecKillService service, CountDownLatch countDownLatch) {
        this.user = user;
        this.service = service;
        this.countDownLatch = countDownLatch;
    }

    public void run(){
        try {
            countDownLatch.await();
            service.grad(user);
        }catch (Exception ignore){}
    }
}
