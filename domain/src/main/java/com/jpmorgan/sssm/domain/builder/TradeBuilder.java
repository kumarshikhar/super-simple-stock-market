package com.jpmorgan.sssm.domain.builder;

import org.joda.time.DateTime;

import com.jpmorgan.sssm.domain.Stock;
import com.jpmorgan.sssm.domain.Trade;
import com.jpmorgan.sssm.domain.TradeDirection;

public class TradeBuilder {

	private Stock stock;

	private DateTime timestamp;

	private Integer quantity;

	private TradeDirection tradeDirection;

	private Double price;

	public TradeBuilder withTimestamp(DateTime timestamp) {
		this.timestamp = timestamp;
		return this;
	}

	public TradeBuilder withQuantity(Integer quantity) {
		this.quantity = quantity;
		return this;
	}

	public TradeBuilder withDirection(TradeDirection tradeDirection) {
		this.tradeDirection = tradeDirection;
		return this;
	}

	public TradeBuilder withPrice(Double price) {
		this.price = price;
		return this;
	}

	public TradeBuilder withStock(Stock stock) {
		this.stock = stock;
		return this;
	}

	public Trade build() {
		return new Trade(stock, timestamp, quantity, tradeDirection, price);
	}

}
