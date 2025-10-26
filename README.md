# Selenium Test Automation Framework

A maintainable, configurable Selenium + TestNG automation framework with automatic waits, resilient element wrappers, structured logging, and Allure reporting.

## Tech Stack
- Java 17
- Maven
- Selenium WebDriver
- TestNG
- Allure Report
- SLF4J + Logback
- DataFaker, Jackson, OpenCSV, Apache POI

## Key Features
- Page Object Model with reusable element wrapper classes (buttons, text boxes, dropdowns, tables, etc.)
- Thread-safe WebDriver management via `ThreadLocal`
- Config-driven setup (`src/main/resources/config.properties`) with system property overrides
- Auto-wait and retry utilities to stabilize UI actions and assertions
- Structured logging with Allure step integration and screenshots on failure
- Data-driven testing helpers for JSON, CSV, and Excel sources
- TestNG retry analyzer and listeners for reporting hooks

## Project Layout
```
src/main/java
  ├─ config        # Framework configuration loader
  ├─ constants     # Enums and shared constants
  ├─ driver        # DriverManager and factory
  ├─ elements      # Wrapped Selenium element abstractions
  ├─ pages         # Page Object Models
  ├─ utils         # Waits, retries, data helpers, screenshots, etc.
  └─ logging       # Step-level logging helpers

src/test/java
  ├─ tests         # TestNG test classes + BaseTest
  ├─ listeners     # Test listeners and retry analyzer
  └─ data          # Test data POJOs and TestNG data providers

src/main/resources
  ├─ config.properties
  └─ logback.xml

src/test/resources/test-data
  ├─ users.json
  └─ users.csv
```

## Getting Started
1. Install Java 17 and Maven.
2. Clone the repository and navigate to the project root.
3. Adjust `src/main/resources/config.properties` as needed, or override values via `-D` system properties (e.g. `-Dbrowser=FIREFOX`).

## Running Tests
```bash
mvn clean test
```

Use system properties to customize execution:
```bash
mvn clean test -Dbrowser=EDGE -Dheadless=true -Denvironment=UAT
```

## Allure Reporting
Generate and serve the Allure report after a test run:
```bash
mvn allure:serve
```

To generate the report artifacts without serving:
```bash
mvn allure:report
```

## Sample Data-Driven Usage
- JSON data: `TestDataProviders#userDataFromJson`
- CSV data: `TestDataProviders#userDataFromCsv`

These providers rely on helper utilities in `com.sangle.selenium.utils` and wrap results via `DataProviderUtils`.

## Continuous Integration
- Execute `mvn clean test` to run the suite.
- Persist Allure results from `target/allure-results/` and publish using your CI tooling.
- Browser, timeouts, headless mode, and environment can be controlled with environment variables or Maven system properties.

## Next Steps
- Extend `pages` with application-specific models and components.
- Add additional listeners or integrations (Slack, email, REST Assured, Dockerized grid) as needed.
- Update `testng.xml` to include full suites and parallel execution strategies for your pipeline.
