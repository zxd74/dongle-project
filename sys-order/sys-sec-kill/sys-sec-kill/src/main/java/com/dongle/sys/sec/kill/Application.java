package com.dongle.sys.sec.kill;

import com.dongle.sys.sec.kill.config.SecKillActivity;
import com.dongle.sys.sec.kill.filter.Filter;
import com.dongle.sys.sec.kill.model.Product;
import com.dongle.sys.sec.kill.model.ProductLimit;
import com.dongle.sys.sec.kill.model.User;
import com.dongle.sys.sec.kill.service.SecKillService;
import com.dongle.sys.sec.kill.service.UserService;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class Application {

    public static void main(String[] args) throws ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入产品数量");
        long count = scanner.nextLong();
        System.out.println("输入用户数量");
        long users = scanner.nextLong();

        Product product = new Product();
        product.setCount(count);
        product.setName("产品A");
        product.setPrice(100D);
        ProductLimit limit = new ProductLimit();
        limit.setStart(new Date());
        SecKillActivity activity = new SecKillActivity(product,limit);
        SecKillService service = new SecKillService(activity);
        CountDownLatch downLatch = new CountDownLatch(1);
        for (long i = 0; i < users; i++) {
            User user = new User();
            user.setDate(new Date());
            user.setId("用户" + i);
            new Thread(new UserService(user,service,downLatch)).start();
        }
        System.out.println("开始抢购。。。");
        downLatch.countDown();


    }
}
