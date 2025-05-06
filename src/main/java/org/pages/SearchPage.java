package org.pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SearchPage extends BasePage {

    private final String URL = "https://www.mobile.bg/search/avtomobili-dzhipove";

    private static final Logger logger = Logger.getLogger(SearchPage.class.getName());

    public SearchPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get(URL);
        acceptCookiesIfPresent();
    }

    public void searchForBrand(String brand)  {
        WebElement searchElement = find(By.name("marka"));
        searchElement.sendKeys(brand);
        WebElement activeFound = find(By.xpath("//*[@id=\"akSearchMarki\"]/div[2]/div[128]/span[1]"));
        activeFound.click();
    }

    public void searchForModel(String model) {
        WebElement modelMenu = find(By.name("model_show"));
        modelMenu.click();
        WebElement modelCheckBox = find(By.xpath("//*[@id=\"akSearchModeli\"]/div/label[18]/input"));
        modelCheckBox.click();
    }

    public void click4x4() {
        WebElement checkBox4x4 = find(By.xpath("//*[@id=\"eimg88\"]/span"));
        checkBox4x4.click();
    }

    public void pressSearchButton() {
        WebElement pressSearchButton = find(By.xpath("/html/body/form/div[1]/div[2]/item[4]/a"));
        pressSearchButton.click();
    }

    private void acceptCookiesIfPresent() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 5);
            WebElement acceptButton = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.id("cookiescript_accept"))
            );

            acceptButton.click();
            logger.info("Cookies accepted from search page" );

        }
        catch (Exception e) {
            logger.log(Level.WARNING, "Unexpected error while accepting cookies from search page", e);
        }
    }
}

