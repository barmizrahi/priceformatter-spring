package org.example.controller;

import org.example.dto.PriceRequest;
import org.example.dto.PriceResponse;
import org.example.model.Currency;
import org.example.repository.CurrencyRepository;
import org.example.service.PriceFormatterService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PriceController {

    private final CurrencyRepository currencyRepository;
    private final PriceFormatterService formatterService;

    public PriceController(CurrencyRepository currencyRepository,
                           PriceFormatterService formatterService) {
        this.currencyRepository = currencyRepository;
        this.formatterService = formatterService;
    }

    @GetMapping("/")
    public String hello() {
        return "Hello from Spring Boot inside Docker!";
    }

    @GetMapping("/currencies")
    public List<Currency> getAllCurrencies() {
        return currencyRepository.findAll();
    }

    @PostMapping("/format")
    public PriceResponse formatPrice(@RequestBody PriceRequest request) {
        return formatterService.formatPrice(request);
    }
}

