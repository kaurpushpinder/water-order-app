package com.rubiconwater.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author pushpinder
 *
 */
@SpringBootApplication
@EnableScheduling
public class WaterUtilityAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(WaterUtilityAppApplication.class, args);
	}

}
