# Selenium Framework Requirements

## 1. Overview
- Language: Java 17
- Build Tool: Maven (latest stable)
- Core Libraries: Selenium WebDriver (latest), TestNG, Allure Report
- Key Pillars: Maintainability, reusability, logging, reporting, auto-wait, retries, configurability, data-driven testing

## 2. Architecture & Design Principles
- Page Object Model with component abstraction for reusable UI widgets
- Custom wrapper layer for all Selenium interactions
- Extensive use of interfaces and generics for extensibility
- Thread-safe driver management via `ThreadLocal`
- Configuration-driven approach, no hardcoded values
- Centralized structured logging
- CI/CD friendly design with easy extensibility for new tools or browsers

## 3. Project Structure
```
src/main/java
  ├─ config/
  ├─ driver/
  ├─ elements/
  ├─ pages/
  ├─ utils/
  ├─ data/
  ├─ constants/
  ├─ logging/

src/test/java
  ├─ tests/
  ├─ listeners/
  ├─ data/

resources/
  ├─ config.properties
  ├─ test-data/*.json, *.csv
```

## 4. Core Framework Components
### 4.1 Element Action Wrappers
- Auto-wait, logging, error handling around every interaction
- Wrappers: Button, TextBox, Dropdown, Checkbox, RadioButton, iFrame, Table (optional), GenericElement
- JS fallbacks and retry logic baked in

### 4.2 Wait Management
- Central `WaitUtils` leveraging `FluentWait`/`WebDriverWait`
- Support visibility, clickability, invisibility, page load completion
- Configurable defaults sourced from properties

### 4.3 Retry Mechanisms
- Test-level retries via TestNG `IRetryAnalyzer`
- Operation-level retries via utility helper

### 4.4 Driver Management
- `DriverManager` with `ThreadLocal<WebDriver>`
- `WebDriverFactory` with Chrome/Firefox/Edge support
- WebDriverManager integration for binaries
- Headless mode toggle via config

### 4.5 Configuration Management
- Single `config.properties` (extendable to YAML)
- Configurable base URL, browser, timeouts, headless, environment, screenshot options
- No hardcoded literals

### 4.6 Constants & Enums
- Centralized constants
- Enums: BrowserType, EnvironmentType, ElementState, TestCategory

### 4.7 Logging
- SLF4J + Logback
- Action logging with locator, action, result
- Console + report readability
- Failure screenshots for Allure attachments

### 4.8 Reporting (Allure)
- Allure + TestNG integration
- `@Step` annotations and attachments
- Captures suite metadata, browser info, failure evidence

## 5. Utilities
- `FakerUtil` (datafaker)
- `DateUtil`
- `JsonHelper`
- `ExcelHelper`
- `CsvHelper`
- `RetryUtils`
- `ScreenshotUtil`
- `WaitUtils`

## 6. Data Management & Providers
- JSON/CSV DataProviders mapping to POJOs or `Map`
- Optional Excel support
- Dynamic faker data fallback
- Data stored under `src/test/resources/test-data/`

## 7. Test Structure & Standards
- Tests extend `BaseTest`
- No direct WebDriver usage; only wrappers
- Page Objects encapsulate interactions
- Retry analyzer applied
- Steps logged to Allure

## 8. Page Object Guidelines
- One class per page/screen
- Exposes business-level actions only
- Reusable components for headers/menus/modals

## 9. Error Handling & Recovery
- Graceful handling of Selenium exceptions
- Descriptive logging and automatic screenshots on failure
- Fail-fast philosophy with Allure evidence

## 10. Parallel Execution & Scalability
- Parallel TestNG execution
- Thread-safe drivers and configuration

## 11. Continuous Integration (CI)
- `mvn clean test`
- `mvn allure:serve`
- Environment overrides via system properties
- Ready for Jenkins/GitHub Actions

## 12. Quality & Maintainability
- Consistent naming and documentation
- No hardcoded literals
- SOLID principles
- Centralized error messages and constants

## 13. Deliverables
- Complete Maven project
- `README.md` with setup, execution, Allure instructions, sample data
- `config.properties` populated with defaults

## 14. Optional Enhancements
- REST Assured integration
- Docker grid support
- Visual testing utilities
- Slack/Email notifications
- Custom dashboards

## 15. Acceptance Criteria
- Maven execution success
- Automatic driver management
- Wrapped actions with auto-wait
- Config-driven behavior
- Allure report generation with screenshots
- Retry support at test and operation levels
- Data-driven tests for JSON/CSV
- Modular, maintainable, readable code
