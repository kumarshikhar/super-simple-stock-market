package com.jpmorgan.sssm.business.configurer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jpmorgan.sssm.domain.Stock;
import com.jpmorgan.sssm.domain.StockFactory;
import com.jpmorgan.sssm.domain.StockType;

@Configuration
public class SuperSimpleStockMarketConfigurer {

	@Value("${common.stocks}")
	private String commonStocks;

	@Value("${preffered.stocks}")
	private String preferredStocks;

	@Bean
	public StockFactory stockFactory() {
		StockFactory stockFactory = new StockFactory();
		for (String symbol : commonStocks.split(",")) {
			stockFactory.register(new Stock(symbol, StockType.COMMON));
		}
		for (String symbol : preferredStocks.split(",")) {
			stockFactory.register(new Stock(symbol, StockType.PREFERRED));
		}
		return stockFactory;
	}

}
