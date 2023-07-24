package com.pranitpatil.service;

import com.pranitpatil.dto.Trade;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TradeBokStorageServiceTest {

    private TradeBookService tradeBookService;

    @BeforeAll
    void init() {
        tradeBookService = TradeBookStorageService.getInstance();
    }

    @Test
    void test_whenTradeIsExecuted_thenItsPrintedProperly() {
        tradeBookService.executeTrade(new Trade("buyId1", "sellId1", 123, 4567));
        tradeBookService.executeTrade(new Trade("buyId2", "sellId2", 321, 4567));

        assertEquals("""
                        buyOrderId=buyId1, sellOrderId=sellId1, price=123, quantity=4567
                        buyOrderId=buyId2, sellOrderId=sellId2, price=321, quantity=4567""",
                tradeBookService.printTradeBook());
    }
}
