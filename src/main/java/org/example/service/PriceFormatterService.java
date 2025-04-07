package org.example.service;

import org.example.dto.PriceRequest;
import org.example.dto.PriceResponse;
import org.example.model.Currency;
import org.example.repository.CurrencyRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

@Service
public class PriceFormatterService {

    private static final BigDecimal VAT_RATE = new BigDecimal("0.19");
    private final CurrencyRepository currencyRepository;

    public PriceFormatterService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public PriceResponse formatPrice(PriceRequest request) {
        BigDecimal cents;
        cents = validateAmouthFromUser(request);
        BigDecimal value = cents.divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
        BigDecimal netValue = value.divide(BigDecimal.ONE.add(VAT_RATE), 2, RoundingMode.HALF_UP);
        BigDecimal vatAmount = value.subtract(netValue);

        Currency currency = currencyRepository.findByCode(request.getCurrency())
                .orElseThrow(() -> new IllegalArgumentException("Unknown currency: " + request.getCurrency()));

        String formattedWithCurrency = formatWithCurrency(value, currency);
        String formattedWithoutCurrency = formatPlain(value);

        return new PriceResponse(
                stripTrailingZeros(value),
                formattedWithCurrency,
                formattedWithoutCurrency,
                stripTrailingZeros(netValue),
                stripTrailingZeros(vatAmount)
        );
    }

    private BigDecimal validateAmouthFromUser(PriceRequest request) {
        BigDecimal cents;
        try{
            cents = new BigDecimal(request.getAmount());
        } catch (Exception e){
            throw new IllegalArgumentException("Invalid Amount");
        }
        if (cents.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        return cents;
    }

    public BigDecimal stripTrailingZeros(BigDecimal value) {
        return value.stripTrailingZeros().scale() < 0 ? value.setScale(0) : value.stripTrailingZeros();
    }

    public String formatWithCurrency(BigDecimal value, Currency currency) {
        String formatted = formatPlain(value);
        return currency.isSymbolOnLeft()
                ? currency.getSymbol() + formatted
                : formatted + currency.getSymbol();
    }

    public String formatPlain(BigDecimal value) {
        DecimalFormat formatter = new DecimalFormat("#,##0.##");
        formatter.setRoundingMode(RoundingMode.HALF_UP);
        return formatter.format(value);
    }

    @RestControllerAdvice
    public class GlobalExceptionHandler {

        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<String> handleBadRequest(IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

}
