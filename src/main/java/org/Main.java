package org;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.pages.HomePage;
import org.pages.ResultsPage;
import org.pages.SearchPage;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        try {

            HomePage  homePage = new HomePage(driver);
            homePage.open();

            SearchPage searchPage = new SearchPage(driver);
            searchPage.open();
            searchPage.searchForBrand("VW");
            searchPage.click4x4();
            searchPage.pressSearchButton();
            Thread.sleep(1000);

            ResultsPage resultsPage = new ResultsPage(driver);
            List<Integer> countSpecial = resultsPage.countSpecial();
            logger.info("All Cars: " + resultsPage.getNumberOfCars());
            logger.info("Top Cars: " + countSpecial.get(0));
            logger.info("Vip Cars: " + countSpecial.get(1));
            logger.info("Best Cars: " + countSpecial.get(2));

        } catch (Exception e) {
            logger.log(Level.SEVERE, "A General error occurred", e);;
        } finally {
            driver.quit();
        }
    }
}