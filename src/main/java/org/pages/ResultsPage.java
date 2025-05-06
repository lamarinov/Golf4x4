package org.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResultsPage extends BasePage {
    private static final Logger logger = Logger.getLogger(ResultsPage.class.getName());

    public ResultsPage(WebDriver driver) {
        super(driver);
    }

    private String getOverallResult() {
        WebElement banner = find(By.xpath("/html/body/form[3]/div[1]"));
        return banner.getText();
    }


    private String incrementPageNumber(String url) {
        // Split query params if present
        String[] urlParts = url.split("\\?", 2);
        String base = urlParts[0];
        String query = urlParts.length > 1 ? "?" + urlParts[1] : "";

        // Pattern to match existing /p-N
        Pattern pattern = Pattern.compile("/p-(\\d+)$");
        Matcher matcher = pattern.matcher(base);

        if (matcher.find()) {
            // Extract current page, increment, replace
            int currentPage = Integer.parseInt(matcher.group(1));
            int nextPage = currentPage + 1;
            base = base.replaceAll("/p-\\d+$", "/p-" + nextPage);
        } else {
            // No /p-N → insert /p-2 (we're moving from page 1)
            base += "/p-2";
        }

        return base + query;
    }

    public int getNumberOfCars() {
        String input = getOverallResult();
        Pattern pattern = Pattern.compile("от общо (\\d+)");
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        return 0;
    }

    public List<Integer> countSpecial() {

        List<Integer> allCount = new ArrayList<>(Collections.emptyList());
        int countTOP = 0;
        int countVIP = 0;
        int countBEST = 0;

        while (true) {

            try {
                String currentURL = driver.getCurrentUrl();
                int top = findAll(By.xpath("//div[contains(@class, 'item') and contains(@class, 'TOP')]")).size();
                int vip = findAll(By.xpath("//div[contains(@class, 'item') and contains(@class, 'VIP')]")).size();
                int best = findAll(By.xpath("//div[contains(@class, 'item') and contains(@class, 'BEST')]")).size();
                countTOP += top;
                countVIP += vip;
                countBEST += best;
                if((top + vip +top) == 0) break;
                driver.get((incrementPageNumber(currentURL)));

                // Optional: wait a bit for DOM to update
                Thread.sleep(500);
            } catch (TimeoutException e) {
                logger.info("The page failed to load in time");
                break;
            } catch (Exception e) {
                logger.warning("Unexpected error during navigation: " + e.getMessage());
                break;
            }
        }
        allCount.add(countTOP);
        allCount.add(countVIP);
        allCount.add(countBEST);
        return allCount;

    }

    public int getNumberOfVIPCars() {
        List<WebElement> vipCars = findAll(By.className(".item.VIP."));
        return vipCars.size();
    }

    public int getNumberOfTOPCars() {
        List<WebElement> topCars = findAll(By.xpath("//div[contains(@class, 'item') and contains(@class, 'TOP')]"));
        return topCars.size();
    }
    public int getNumberOfBESTCars() {
        List<WebElement> bestCars = findAll(By.className(".item.BEST."));
        return bestCars.size();
    }

}
