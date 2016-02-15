package com.jpmorgan.sssm.business.service;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jpmorgan.sssm.api.service.SuperSimpleStockMarketService;
import com.jpmorgan.sssm.business.SuperSimpleStockMarketStarter;
import com.jpmorgan.sssm.domain.builder.StockTickerBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
@SpringApplicationConfiguration(classes = { SuperSimpleStockMarketStarter.class })
public class SuperSimpleStockMarketDividendYeildTest {

	@Autowired
	private SuperSimpleStockMarketService superSimpleStockMarketService;

	@Test
	public void testDividendYeildOfCommonStock() {
		double dividendYeild = superSimpleStockMarketService.calculateDividendYeild(
				new StockTickerBuilder().withSymbol("POP").withLastDividend(8.0d).withFixedDividend(null)
						.withParValue(100d).build(), 100.0);
		assertThat(dividendYeild, is(0.08d));
	}

	@Test
	public void testDividendYeildOfPreferredStock() {
		double dividendYeild = superSimpleStockMarketService.calculateDividendYeild(
				new StockTickerBuilder().withSymbol("GIN").withLastDividend(8.0d).withFixedDividend(0.02)
						.withParValue(100d).build(), 100.0);
		assertThat(dividendYeild, is(0.02d));
	}

	/**
	 * The requirement doesn't provide enough details on how to calculate
	 * dividend for these!
	 */
	@Test
	public void testDividendYeildOfCommonStockWithZeroLastDividend() {
		double dividendYeild = superSimpleStockMarketService.calculateDividendYeild(
				new StockTickerBuilder().withSymbol("POP").withLastDividend(0.0d).withFixedDividend(null)
						.withParValue(100d).build(), 100.0);
		assertThat(dividendYeild, is(0.0d));
	}
}
