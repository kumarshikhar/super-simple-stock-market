package com.jpmorgan.sssm.business;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * This is the starter class for running the super simple stock application
 * 
 * @author Kumar Shikhar
 * 
 */
@SpringBootApplication
@ComponentScan(basePackages = { "com.jpmorgan.sssm" })
public class SuperSimpleStockMarketStarter {

	public static void main(String[] args) {
		SpringApplication.run(SuperSimpleStockMarketStarter.class, args);
	}
}
