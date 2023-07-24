package com.pranitpatil.service;

import com.pranitpatil.dto.Trade;
import com.pranitpatil.storage.TradeBook;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TradeBookStorageService implements TradeBookService {

    private TradeBook tradeBook;

    private static final TradeBookStorageService INSTANCE = new TradeBookStorageService();

    public static TradeBookStorageService getInstance() {
        return INSTANCE;
    }

    private TradeBookStorageService() {
        tradeBook = new TradeBook();
    }

    @Override
    public Trade executeTrade(Trade trade) {
        tradeBook.getTrades().add(trade);
        log.debug("Executed Trade {}", trade);
        return trade;
    }

    @Override
    public String printTradeBook() {
        return tradeBook.getTrades()
                .toString()
                .replace("[", "")
                .replace("], ", "\n")
                .replace("]", "")
                .replace("Trade", "");
    }
}
