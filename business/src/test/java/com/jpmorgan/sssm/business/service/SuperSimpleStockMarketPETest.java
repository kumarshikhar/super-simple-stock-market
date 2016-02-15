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
public class SuperSimpleStockMarketPETest {

	@Autowired
	private SuperSimpleStockMarketService superSimpleStockMarketService;

	@Test
	public void testPEOfCommonStock() {
		double pe = superSimpleStockMarketService.calculatePE(new StockTickerBuilder().withSymbol("POP")
				.withLastDividend(8.0d).withFixedDividend(null).withParValue(100d).build(), 100.0);
		assertThat(pe, is(12.5d));
	}

	@Test
	public void testPEOfPreferredStock() {
		double pe = superSimpleStockMarketService.calculatePE(new StockTickerBuilder().withSymbol("GIN")
				.withLastDividend(8.0d).withFixedDividend(0.02).withParValue(100d).build(), 100.0);
		assertThat(pe, is(50d));
	}

	/**
	 * Unclear of what will be it's value for 0 dividend, from formula looks to
	 * be infinity
	 */
	@Test
	public void testPEOfCommonStockWithZeroDividend() {
		double pe = superSimpleStockMarketService.calculatePE(new StockTickerBuilder().withSymbol("POP")
				.withLastDividend(0.0d).withFixedDividend(null).withParValue(100d).build(), 100.0);
		assertThat(pe, is(Double.POSITIVE_INFINITY));
	}

}
