package org.example.config;

import org.example.model.Currency;
import org.example.repository.CurrencyRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {
    //runs when the app is up
    // I choose to insert to the DB if the symbol should be on the left or on the right,
    // for others currencies then Shekel
    @Bean
    CommandLineRunner initDatabase(CurrencyRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(new Currency("USD", "$", false));
                repository.save(new Currency("EUR", "€", false));
                repository.save(new Currency("GBP", "£", false));
                repository.save(new Currency("ILS", "₪", true));
            }
        };
    }
}

