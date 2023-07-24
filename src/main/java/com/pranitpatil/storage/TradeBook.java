package com.pranitpatil.storage;

import com.pranitpatil.dto.Trade;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class TradeBook {
    private final List<Trade> trades = new ArrayList<>();

}
