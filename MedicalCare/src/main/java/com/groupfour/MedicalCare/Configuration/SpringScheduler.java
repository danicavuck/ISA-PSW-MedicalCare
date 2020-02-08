package com.groupfour.MedicalCare.Configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.text.SimpleDateFormat;

@Configuration
@EnableScheduling
public class SpringScheduler {
    private static Logger logger = LoggerFactory.getLogger(SpringScheduler.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

//    @Scheduled(fixedRate = 5000L)
//    public static void currentTime(){
//        logger.info(dateFormat.format(new Date()));
//    }
}
