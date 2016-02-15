package com.jpmorgan.sssm.domain;

import java.util.Map;

import com.google.common.collect.Maps;

public class StockFactory {

	private Map<String, Stock> stocks = Maps.newHashMap();

	public void register(Stock stock) {
		stocks.put(stock.getSymbol(), stock);
	}

	public Stock getStock(String symbol) {
		Stock stock = stocks.get(symbol);
		if (stock != null) {
			return stock;
		}
		throw new RuntimeException(String.format("%s is still not configured!", symbol));
	}

}
