package com.jpmorgan.sssm.api.repository;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.jpmorgan.sssm.domain.Stock;
import com.jpmorgan.sssm.domain.Trade;

public interface SuperSimpleStockMarketRepository {

	public void record(Trade trade);

	public List<Trade> getAllTrades();

	public List<Trade> getTrades(long time, TimeUnit timeUnit);

	public List<Trade> getTrades(Stock stock, long time, TimeUnit timeUnit);

	public void resetTrades();

}
