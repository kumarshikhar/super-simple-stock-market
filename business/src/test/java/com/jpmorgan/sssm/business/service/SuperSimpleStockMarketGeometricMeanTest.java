package com.jpmorgan.sssm.business.service;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jpmorgan.sssm.api.service.SuperSimpleStockMarketService;
import com.jpmorgan.sssm.business.SuperSimpleStockMarketStarter;
import com.jpmorgan.sssm.domain.Stock;
import com.jpmorgan.sssm.domain.StockType;
import com.jpmorgan.sssm.domain.TradeDirection;
import com.jpmorgan.sssm.domain.builder.TradeBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
@SpringApplicationConfiguration(classes = { SuperSimpleStockMarketStarter.class })
public class SuperSimpleStockMarketGeometricMeanTest {

	@Autowired
	private SuperSimpleStockMarketService superSimpleStockMarketService;

	@Test
	public void testVolumeWeightedPriceCalculation() throws InterruptedException {
		superSimpleStockMarketService
				.record(new TradeBuilder().withTimestamp(new DateTime()).withQuantity(100)
						.withDirection(TradeDirection.BUY).withPrice(101d).withStock(new Stock("GIN", StockType.PREFERRED))
						.build());
		superSimpleStockMarketService.record(new TradeBuilder().withTimestamp(new DateTime()).withQuantity(200)
				.withDirection(TradeDirection.SELL).withPrice(99d).withStock(new Stock("TEA", StockType.COMMON)).build());
		superSimpleStockMarketService.record(new TradeBuilder().withTimestamp(new DateTime()).withQuantity(50)
				.withDirection(TradeDirection.BUY).withPrice(100d).withStock(new Stock("POP", StockType.COMMON)).build());
		superSimpleStockMarketService.record(new TradeBuilder().withTimestamp(new DateTime()).withQuantity(100)
				.withDirection(TradeDirection.SELL).withPrice(250d).withStock(new Stock("JOE", StockType.COMMON)).build());

		double gbceAllShareIndex = superSimpleStockMarketService.calculateGbceAllShareIndex();

		assertThat(gbceAllShareIndex, is(125.74d));
	}
}
