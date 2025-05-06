package org.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.logging.Level;
import java.util.logging.Logger;

public class HomePage extends BasePage {

    private final String URL = "https://www.mobile.bg";

    private static final Logger logger = Logger.getLogger(HomePage.class.getName());

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get(URL);
        acceptCookiesIfPresent();
    }


    private void acceptCookiesIfPresent() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 5);
            WebElement acceptButton = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.id("cookiescript_accept"))
            );

            acceptButton.click();
            logger.info("Cookies accepted from Home page" );

        }
        catch (Exception e) {
            logger.log(Level.WARNING, "Unexpected error while accepting cookies from home page", e);
        }
    }

}
