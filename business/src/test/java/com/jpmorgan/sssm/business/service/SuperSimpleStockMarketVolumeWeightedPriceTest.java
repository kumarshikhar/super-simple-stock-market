package com.jpmorgan.sssm.business.service;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.concurrent.TimeUnit;

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
public class SuperSimpleStockMarketVolumeWeightedPriceTest {

	@Autowired
	private SuperSimpleStockMarketService superSimpleStockMarketService;

	@Test
	public void testVolumeWeightedPriceCalculation() throws InterruptedException {
		superSimpleStockMarketService
				.record(new TradeBuilder().withTimestamp(new DateTime()).withQuantity(100)
						.withDirection(TradeDirection.BUY).withPrice(101d).withStock(new Stock("GIN", StockType.PREFERRED))
						.build());
		superSimpleStockMarketService
				.record(new TradeBuilder().withTimestamp(new DateTime()).withQuantity(200)
						.withDirection(TradeDirection.SELL).withPrice(99d).withStock(new Stock("GIN", StockType.PREFERRED))
						.build());

		Thread.sleep(3000);

		superSimpleStockMarketService
				.record(new TradeBuilder().withTimestamp(new DateTime()).withQuantity(50).withDirection(TradeDirection.BUY)
						.withPrice(101d).withStock(new Stock("GIN", StockType.PREFERRED)).build());
		superSimpleStockMarketService
				.record(new TradeBuilder().withTimestamp(new DateTime()).withQuantity(20)
						.withDirection(TradeDirection.SELL).withPrice(99d).withStock(new Stock("GIN", StockType.PREFERRED))
						.build());

		// The last 2 trades should be included in this, after the 3 sec wait
		double volumeWeightedPrice = superSimpleStockMarketService.calculateVolumeWeightedPrice(new Stock("GIN",
				StockType.PREFERRED), 2000, TimeUnit.MILLISECONDS);
		assertThat(volumeWeightedPrice, is(100.43d));

		// All trades should be included in last 15 minutes
		volumeWeightedPrice = superSimpleStockMarketService.calculateVolumeWeightedPrice(new Stock("GIN",
				StockType.PREFERRED), 15, TimeUnit.MINUTES);
		assertThat(volumeWeightedPrice, is(99.81d));
	}
}
