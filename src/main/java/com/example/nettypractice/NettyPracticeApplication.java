package com.example.nettypractice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;

@SpringBootApplication
public class NettyPracticeApplication {

    public static void main(String[] args) {
//        SpringApplication.run(NettyPracticeApplication.class, args);
        SpringApplication application = new SpringApplication(NettyPracticeApplication.class);
        application.addListeners(new ApplicationPidFileWriter("./netty-practice.pid"));
        application.run(args);
    }

}
