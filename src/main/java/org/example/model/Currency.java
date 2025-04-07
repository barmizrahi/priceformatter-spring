package org.example.model;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    private String code;
    @Getter
    private String symbol;
    @Getter
    private boolean symbolOnLeft;

    public Currency() {}

    public Currency(String code, String symbol, boolean symbolOnLeft) {
        this.code = code;
        this.symbol = symbol;
        this.symbolOnLeft = symbolOnLeft;
    }

}
