package com.iflytek.phantom.im;

import com.iflytek.phantom.im.core.engine.IMEngineConsumerPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
//@EnableDiscoveryClient
public class PhantomImApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(PhantomImApplication.class, args);
    }

    @Autowired
    private IMEngineConsumerPool pool;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        pool.get().consumer();
    }
}
