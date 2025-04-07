package org.example.model;

import jakarta.persistence.*;

@Entity
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private String symbol;
    private boolean symbolOnLeft;

    public Currency() {}

    public Currency(String code, String symbol, boolean symbolOnLeft) {
        this.code = code;
        this.symbol = symbol;
        this.symbolOnLeft = symbolOnLeft;
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getSymbol() {
        return symbol;
    }

    public boolean isSymbolOnLeft() {
        return symbolOnLeft;
    }
}
