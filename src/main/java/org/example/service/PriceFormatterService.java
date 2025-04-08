package org.example.service;

import org.example.dto.PriceRequest;
import org.example.dto.PriceResponse;
import org.example.model.Currency;
import org.example.repository.CurrencyRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import static org.example.util.Const.*;

@Service
public class PriceFormatterService {

    private final CurrencyRepository currencyRepository;

    public PriceFormatterService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public PriceResponse formatPrice(PriceRequest request) {
        BigDecimal cents;
        //if not valid then throw Exception
        cents = validateAmountFromUser(request);
        BigDecimal value = cents.divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
        BigDecimal netValue = value.divide(BigDecimal.ONE.add(VAT_RATE), 2, RoundingMode.HALF_UP);
        BigDecimal vatAmount = value.subtract(netValue);
        // Retrieve the currency object from the repository by its code
        String currencyCode = request.getCurrency().trim().toUpperCase();
        Currency currency = currencyRepository.findByCode(currencyCode)
                .orElseThrow(() -> new IllegalArgumentException("Unknown currency: " + currencyCode));
        // Format the value with the currency symbol
        String formattedWithCurrency = formatWithCurrency(value, currency);
        // Format the value without the currency symbol (plain number)
        String formattedWithoutCurrency = formatPlain(value);
        // Return the formatted price response back to the server
        return new PriceResponse(
                stripTrailingZeros(value),
                formattedWithCurrency,
                formattedWithoutCurrency,
                stripTrailingZeros(netValue),
                stripTrailingZeros(vatAmount)
        );
    }


    private BigDecimal validateAmountFromUser(PriceRequest request) {
        //validate that the amount is a valid number(contains only numbers) and not negative
        try {
            BigDecimal cents = new BigDecimal(request.getAmount());
            if (cents.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Amount cannot be negative");
            }
            return cents;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid amount format");
        }
    }


    private BigDecimal stripTrailingZeros(BigDecimal value) {
        //Strips any trailing zeros from a BigDecimal value.
        // If the scale is less than 0, it sets the scale to 0
        return value.stripTrailingZeros().scale() < 0 ? value.setScale(0) : value.stripTrailingZeros();
    }

    private String formatWithCurrency(BigDecimal value, Currency currency) {
        String formatted = formatPlain(value);
        //Simple method that place the Symbol on the left or on the right of the value base of what in the DB
        return currency.isSymbolOnLeft()
                ? currency.getSymbol() + formatted
                : formatted + currency.getSymbol();
    }

    private String formatPlain(BigDecimal value) {
        // if we have more than 2 digit after the Dot then remain only 2,
        // most currencies dont have that tiny currency (.005)
        DecimalFormat formatter = new DecimalFormat("#,##0.##");
        formatter.setRoundingMode(RoundingMode.HALF_UP);
        return formatter.format(value);
    }

}
