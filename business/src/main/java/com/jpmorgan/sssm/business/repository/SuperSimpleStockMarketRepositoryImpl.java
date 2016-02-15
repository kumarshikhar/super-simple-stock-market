package com.jpmorgan.sssm.business.repository;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;
import com.jpmorgan.sssm.api.repository.SuperSimpleStockMarketRepository;
import com.jpmorgan.sssm.domain.Stock;
import com.jpmorgan.sssm.domain.Trade;

@Repository
public class SuperSimpleStockMarketRepositoryImpl implements SuperSimpleStockMarketRepository {

	/*
	 * I could have made it synchronized, but I am not bringing the complexity as
	 * our legitimate trade store should provide ACID
	 */
	private final List<Trade> tradeStore = Lists.newArrayList();

	public void record(Trade trade) {
		tradeStore.add(trade);
	}

	public List<Trade> getAllTrades() {
		return Lists.newArrayList(tradeStore);
	}

	public List<Trade> getTrades(long time, TimeUnit timeUnit) {
		long currentTime = System.currentTimeMillis();
		List<Trade> trades = Lists.newArrayList();
		for (Trade trade : tradeStore) {
			if (trade.getTimestamp().isAfter(currentTime - timeUnit.toMillis(time))) {
				trades.add(trade);
			}
		}
		return trades;
	}

	public List<Trade> getTrades(Stock stock, long time, TimeUnit timeUnit) {
		long currentTime = System.currentTimeMillis();
		List<Trade> trades = Lists.newArrayList();
		for (Trade trade : tradeStore) {
			if (stock.equals(trade.getStock()) && trade.getTimestamp().isAfter(currentTime - timeUnit.toMillis(time))) {
				trades.add(trade);
			}
		}
		return trades;
	}

	public void resetTrades() {
		tradeStore.clear();
	}

}
