package com.jpmorgan.sssm.api.service;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.jpmorgan.sssm.domain.Stock;
import com.jpmorgan.sssm.domain.StockTicker;
import com.jpmorgan.sssm.domain.Trade;

public interface SuperSimpleStockMarketService {

	public Double calculateDividendYeild(StockTicker stockTicker, Double price);

	public Double calculatePE(StockTicker stockTicker, Double price);

	public List<Trade> getAllTrades();

	public void record(Trade build);

	public List<Trade> getTrades(long time, TimeUnit timeUnit);

	public List<Trade> getTrades(Stock stock, long time, TimeUnit timeUnit);

	public Double calculateVolumeWeightedPrice(Stock stock, long time, TimeUnit timeUnit);

	public Double calculateGbceAllShareIndex();

	public void resetTrades();

}
