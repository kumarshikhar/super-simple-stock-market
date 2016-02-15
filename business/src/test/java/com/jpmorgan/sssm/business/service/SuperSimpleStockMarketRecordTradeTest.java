package com.jpmorgan.sssm.business.service;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;
import org.junit.Before;
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
import com.jpmorgan.sssm.domain.Trade;
import com.jpmorgan.sssm.domain.TradeDirection;
import com.jpmorgan.sssm.domain.builder.TradeBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
@SpringApplicationConfiguration(classes = { SuperSimpleStockMarketStarter.class })
public class SuperSimpleStockMarketRecordTradeTest {

	@Autowired
	private SuperSimpleStockMarketService superSimpleStockMarketService;

	@Before
	public void resetTradeStore() {
		superSimpleStockMarketService.resetTrades();
	}

	@Test
	public void testRecordTrade() {
		List<Trade> trades = superSimpleStockMarketService.getAllTrades();
		assertThat(trades.size(), is(0));

		Trade trade = new TradeBuilder().withTimestamp(new DateTime()).withQuantity(10).withDirection(TradeDirection.BUY)
				.withPrice(101d).withStock(new Stock("GIN", StockType.PREFERRED)).build();
		superSimpleStockMarketService.record(trade);
		trades = superSimpleStockMarketService.getAllTrades();
		assertThat(trades.size(), is(1));
		assertThat(trades.iterator().next(), is(trade));

		Trade trade2 = new TradeBuilder().withTimestamp(new DateTime()).withQuantity(20)
				.withDirection(TradeDirection.SELL).withPrice(99d).withStock(new Stock("JOE", StockType.COMMON)).build();
		superSimpleStockMarketService.record(trade2);
		trades = superSimpleStockMarketService.getAllTrades();
		assertTrue(trades.contains(trade2));
		assertTrue(trades.contains(trade));
	}

	@Test
	public void getGetAllTradesInLastNTime() throws InterruptedException {
		List<Trade> trades = superSimpleStockMarketService.getAllTrades();
		assertThat(trades.size(), is(0));

		Trade trade = new TradeBuilder().withTimestamp(new DateTime()).withQuantity(10).withDirection(TradeDirection.BUY)
				.withPrice(101d).withStock(new Stock("GIN", StockType.PREFERRED)).build();
		superSimpleStockMarketService.record(trade);
		trades = superSimpleStockMarketService.getTrades(2000, TimeUnit.MILLISECONDS);
		assertThat(trades.size(), is(1));
		assertThat(trades.iterator().next(), is(trade));

		Thread.sleep(1000);

		Trade trade2 = new TradeBuilder().withTimestamp(new DateTime()).withQuantity(20)
				.withDirection(TradeDirection.SELL).withPrice(99d).withStock(new Stock("JOE", StockType.COMMON)).build();
		superSimpleStockMarketService.record(trade2);
		trades = superSimpleStockMarketService.getTrades(2000, TimeUnit.MILLISECONDS);
		assertThat(trades.size(), is(2));
		assertTrue(trades.contains(trade2));
		assertTrue(trades.contains(trade));

		trades = superSimpleStockMarketService.getTrades(new Stock("JOE", StockType.COMMON), 2000, TimeUnit.MILLISECONDS);
		assertThat(trades.size(), is(1));
		assertTrue(trades.contains(trade2));

		trades = superSimpleStockMarketService.getTrades(new Stock("GIN", StockType.PREFERRED), 2000,
				TimeUnit.MILLISECONDS);
		assertThat(trades.size(), is(1));
		assertTrue(trades.contains(trade));

		Thread.sleep(1000);

		trades = superSimpleStockMarketService.getTrades(2000, TimeUnit.MILLISECONDS);
		assertThat(trades.size(), is(1));
		assertThat(trades.iterator().next(), is(trade2));
	}
}
