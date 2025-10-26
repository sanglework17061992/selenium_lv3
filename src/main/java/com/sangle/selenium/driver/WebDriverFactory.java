package com.sangle.selenium.driver;

import com.sangle.selenium.config.FrameworkConfig;
import com.sangle.selenium.constants.BrowserType;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

class WebDriverFactory {

    WebDriver createWebDriver(FrameworkConfig config) {
        BrowserType browserType = config.browserType();
        return switch (browserType) {
            case CHROME -> createChromeDriver(config.headless(), config.pageLoadTimeoutSeconds());
            case FIREFOX -> createFirefoxDriver(config.headless(), config.pageLoadTimeoutSeconds());
            case EDGE -> createEdgeDriver(config.headless(), config.pageLoadTimeoutSeconds());
        };
    }

    private WebDriver createChromeDriver(boolean headless, long pageLoadTimeoutSeconds) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        if (headless) {
            options.addArguments("--headless=new");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");
        }
        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().pageLoadTimeout(java.time.Duration.ofSeconds(pageLoadTimeoutSeconds));
        return driver;
    }

    private WebDriver createFirefoxDriver(boolean headless, long pageLoadTimeoutSeconds) {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
    options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        if (headless) {
            options.addArguments("-headless");
        }
        WebDriver driver = new FirefoxDriver(options);
        driver.manage().timeouts().pageLoadTimeout(java.time.Duration.ofSeconds(pageLoadTimeoutSeconds));
        return driver;
    }

    private WebDriver createEdgeDriver(boolean headless, long pageLoadTimeoutSeconds) {
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        if (headless) {
            options.addArguments("--headless=new");
            options.addArguments("--disable-gpu");
        }
        WebDriver driver = new EdgeDriver(options);
        driver.manage().timeouts().pageLoadTimeout(java.time.Duration.ofSeconds(pageLoadTimeoutSeconds));
        return driver;
    }
}
