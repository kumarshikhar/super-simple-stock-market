package com.jpmorgan.sssm.domain;

import org.joda.time.DateTime;

public class Trade {
	private final Stock stock;

	private final DateTime timestamp;

	private final Integer quantity;

	private final TradeDirection tradeDirection;

	private final Double price;

	public Trade(Stock stock, DateTime timestamp, Integer quantity, TradeDirection tradeDirection, Double price) {
		super();
		this.stock = stock;
		this.timestamp = timestamp;
		this.quantity = quantity;
		this.tradeDirection = tradeDirection;
		this.price = price;
	}

	public Stock getStock() {
		return stock;
	}

	public DateTime getTimestamp() {
		return timestamp;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public TradeDirection getTradeDirection() {
		return tradeDirection;
	}

	public Double getPrice() {
		return price;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((quantity == null) ? 0 : quantity.hashCode());
		result = prime * result + ((stock == null) ? 0 : stock.hashCode());
		result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
		result = prime * result + ((tradeDirection == null) ? 0 : tradeDirection.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Trade other = (Trade) obj;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (quantity == null) {
			if (other.quantity != null)
				return false;
		} else if (!quantity.equals(other.quantity))
			return false;
		if (stock == null) {
			if (other.stock != null)
				return false;
		} else if (!stock.equals(other.stock))
			return false;
		if (timestamp == null) {
			if (other.timestamp != null)
				return false;
		} else if (!timestamp.equals(other.timestamp))
			return false;
		if (tradeDirection != other.tradeDirection)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Trade [stock=" + stock + ", timestamp=" + timestamp + ", quantity=" + quantity + ", tradeDirection="
				+ tradeDirection + ", price=" + price + "]";
	}

}
