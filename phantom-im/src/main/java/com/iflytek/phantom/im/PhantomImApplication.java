package com.iflytek.phantom.im;

import com.iflytek.phantom.im.core.engine.IMEngineProducerPool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableDiscoveryClient
public class PhantomImApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(PhantomImApplication.class, args);
    }

    @Autowired
    private IMEngineProducerPool pool;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        pool.start();
    }
}
