# Price Formatter Service

A simple Spring Boot application that formats a given price (in cents) into a human-readable string with currency symbols, net price, and VAT calculations.

## Features

- Accepts an amount in cents and a currency code.
- Calculates net price, VAT amount, and final price including VAT (19% by default).
- Formats prices with or without currency symbols.
- Returns clean and user-friendly output via a REST API.
- Validates inputs and handles errors gracefully.

## Endpoints

### `GET /currencies`

Returns the list of all available currencies stored in the database.

**Response:**
```json
[
  {
    "code": "EUR",
    "symbol": "€",
    "symbolOnLeft": true
  }
]
```

### `POST /format`

Formats the given price input into a structured response.

**Request Body:**
```json
{
  "amount": "1000",
  "currency": "EUR"
}
```

**Response:**
```json
{
  "value": 10,
  "formattedWithCurrency": "€10",
  "formattedWithoutCurrency": "10",
  "netValue": 8.4,
  "vatAmount": 1.6
}
```

## Technologies

- Java 17
- Spring Boot
- Spring Data JPA
- H2 (in-memory database)
- Gradle (Kotlin DSL)
- RESTful API (JSON)


## Error Handling

- Returns HTTP `400 Bad Request` for invalid amounts or unknown currencies.
- Returns HTTP `406 Not Acceptable` if the client sends unsupported `Accept` headers.

## Notes

- VAT rate is currently hardcoded as a static constant (`19%`).
- Currency table should be pre-populated manually or with a data initializer if desired.

