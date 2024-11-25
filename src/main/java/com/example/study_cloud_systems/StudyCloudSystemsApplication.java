package com.example.study_cloud_systems;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class StudyCloudSystemsApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudyCloudSystemsApplication.class, args);
    }

}
