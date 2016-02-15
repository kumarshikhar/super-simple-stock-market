package com.jpmorgan.sssm.domain.builder;

import com.jpmorgan.sssm.domain.StockTicker;

public class StockTickerBuilder {

	private String symbol;

	private Double lastDividend;

	private Double fixedDividend;

	private Double parValue;

	public StockTickerBuilder withSymbol(String symbol) {
		this.symbol = symbol;
		return this;
	}

	public StockTickerBuilder withLastDividend(Double lastDividend) {
		this.lastDividend = lastDividend;
		return this;
	}

	public StockTickerBuilder withFixedDividend(Double fixedDividend) {
		this.fixedDividend = fixedDividend;
		return this;
	}

	public StockTickerBuilder withParValue(Double parValue) {
		this.parValue = parValue;
		return this;
	}

	public StockTicker build() {
		return new StockTicker(symbol, lastDividend, fixedDividend, parValue);
	}

}
