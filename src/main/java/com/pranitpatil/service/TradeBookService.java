package com.pranitpatil.service;

import com.pranitpatil.dto.Trade;

public interface TradeBookService {

    Trade executeTrade(Trade trade);

    String printTradeBook();
}
