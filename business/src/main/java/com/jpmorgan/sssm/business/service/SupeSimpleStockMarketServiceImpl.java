package com.jpmorgan.sssm.business.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.jpmorgan.sssm.api.repository.SuperSimpleStockMarketRepository;
import com.jpmorgan.sssm.api.service.SuperSimpleStockMarketService;
import com.jpmorgan.sssm.domain.Stock;
import com.jpmorgan.sssm.domain.StockFactory;
import com.jpmorgan.sssm.domain.StockTicker;
import com.jpmorgan.sssm.domain.StockType;
import com.jpmorgan.sssm.domain.Trade;

@Service
public class SupeSimpleStockMarketServiceImpl implements SuperSimpleStockMarketService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SupeSimpleStockMarketServiceImpl.class);

	@Value("${round.to.decimal.places}")
	private int decimalPlacesToRoundTo;

	@Autowired
	private SuperSimpleStockMarketRepository simpleStockMarketRepository;

	@Autowired
	private StockFactory stockFactory;

	public Double calculateDividendYeild(StockTicker stockTicker, Double price) {
		return getDividend(stockTicker) / price;
	}

	/**
	 * I am not sure of the formula, it says price/dividend - but what exactly is
	 * dividend? Using dividend definition from dividendYeild method!
	 * 
	 */
	public Double calculatePE(StockTicker stockTicker, Double price) {
		return price / getDividend(stockTicker);
	}

	public void record(Trade trade) {
		simpleStockMarketRepository.record(trade);
	}

	public List<Trade> getAllTrades() {
		return simpleStockMarketRepository.getAllTrades();
	}

	public List<Trade> getTrades(long time, TimeUnit timeUnit) {
		return simpleStockMarketRepository.getTrades(time, timeUnit);
	}

	public List<Trade> getTrades(Stock stock, long time, TimeUnit timeUnit) {
		return simpleStockMarketRepository.getTrades(stock, time, timeUnit);
	}

	public Double calculateVolumeWeightedPrice(Stock stock, long time, TimeUnit timeUnit) {
		double allTradePrice = 0.0;
		long totalQuantity = 0;
		for (Trade trade : getTrades(stock, time, timeUnit)) {
			allTradePrice += (trade.getPrice() * trade.getQuantity());
			totalQuantity += trade.getQuantity();
		}

		LOGGER.info(String.format("Rounding result to %s decimal places", decimalPlacesToRoundTo));
		return new BigDecimal(allTradePrice / totalQuantity).setScale(2, RoundingMode.HALF_EVEN).doubleValue();

	}

	public Double calculateGbceAllShareIndex() {
		List<Trade> allTrades = simpleStockMarketRepository.getAllTrades();
		double allPriceMultiplied = 1.0d;
		for (Trade trade : allTrades) {
			allPriceMultiplied *= trade.getPrice();
		}

		LOGGER.info(String.format("Rounding result to %s decimal places", decimalPlacesToRoundTo));
		return new BigDecimal(Math.pow(Math.E, Math.log(allPriceMultiplied) / allTrades.size())).setScale(2,
				RoundingMode.HALF_EVEN).doubleValue();
	}

	public void resetTrades() {
		simpleStockMarketRepository.resetTrades();
	}

	private Double getDividend(StockTicker stockTicker) {
		StockType stockType = stockFactory.getStock(stockTicker.getSymbol()).getType();
		switch (stockType) {
		case COMMON:
			return stockTicker.getLastDividend();
		case PREFERRED:
			return stockTicker.getFixedDividend() * stockTicker.getParValue();
		default:
			throw new RuntimeException(String.format("Stock type [%s] not supported!", stockType));
		}
	}
}
