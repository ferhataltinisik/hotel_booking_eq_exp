package equalexperts.helper;


import equalexperts.pages.BookingPage;
import equalexperts.utilities.Driver;
import com.google.common.base.Function;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class Helper {


    public static void selectDate(String date){
        BookingPage bookingPage = new BookingPage();
        String[] dateParts = date.split(" ");
        String day = dateParts[0];
        String clearedDay = removeChar(day);
        String month = dateParts[1];
        String year = dateParts[2];

        while (!bookingPage.yearOfTheCalendar.getText().contains(year)){
            bookingPage.nextDatePickerPage.click();
        }

        while (!bookingPage.monthOfTheYear.getText().contains(month)){
            bookingPage.nextDatePickerPage.click();
        }
        int count = bookingPage.daysOfCalendar.size();
        for (int i=0 ; i<count ; i++){
            String findDay= bookingPage.daysOfCalendar.get(i).getText();
            if (findDay.equalsIgnoreCase(clearedDay)){
                bookingPage.daysOfCalendar.get(i).click();
                break;
            }
        }
    }

    public static String getDate(int futureDay){
        return DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).format(LocalDate.now().plusDays(futureDay));
    }

    public static String removeChar(String str) {
        return str.replaceAll("0", "");
    }


    //trim price the price and convert Long
    public static Double convertPriceToLong(WebElement productPriceBlock){
        return Double.parseDouble(productPriceBlock.getText().trim().replaceAll("[^\\d.]+", ""));
    }

    // Check if the list contain booking
    public static boolean isBookingAvailable(List<WebElement> webElementList,String firstName, String lastName){
        boolean result = false;

        return webElementList.stream().anyMatch(x -> x.getText().contains(firstName)
                    && x.getText().contains(lastName)
            );
    }


    public static Double getMinValue(List<Double> list){
         return Collections.max(list);

    }

    public static Double getMaxValue(List<Double> list){
        return Collections.max(list);
    }


    //check if element is visible or not
    public static boolean isElementAvailable(WebElement element) {
        boolean flag = false;
        try {
            if (element.isDisplayed() || element.isEnabled())
                flag = true;
        } catch (NoSuchElementException e) {
            flag = false;
        }
        return flag;
    }

    public static void scrollDown() {
        ((JavascriptExecutor) Driver.getDriver()).executeScript("window.scrollBy(0,1000)");
    }

    //Generic method to parse text of elements in a list
    public static List<String> getElementsText(By locator) {
        List<WebElement> elems = Driver.getDriver().findElements(locator);
        List<String> elemTexts = new ArrayList<>();
        for (WebElement el : elems) {
            elemTexts.add(el.getText());
        }
        return elemTexts;
    }


    public static void wait(int secs) {
        try {
            Thread.sleep(1000 * secs);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //switches to new window by the exact title
    //returns to original window if windows with given title not found
    public static void switchToWindow(String targetTitle) {
        String origin = Driver.getDriver().getWindowHandle();
        for (String handle : Driver.getDriver().getWindowHandles()) {
            Driver.getDriver().switchTo().window(handle);
            if (Driver.getDriver().getTitle().equals(targetTitle)) {
                return;
            }
        }
        Driver.getDriver().switchTo().window(origin);
    }

    public static void hover(WebElement element) {
        Actions actions = new Actions(Driver.getDriver());
        actions.moveToElement(element).perform();
    }

    public static WebElement waitForVisibility(WebElement element, int timeToWaitInSec) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), timeToWaitInSec);
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static WebElement waitForClickability(WebElement element, int timeout) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), timeout);
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }


    public static void waitForPageToLoad(long timeOutInSeconds) {
        ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
            }
        };
        try {
            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), timeOutInSeconds);
            wait.until(expectation);
        } catch (Throwable error) {
            error.printStackTrace();
        }
    }

    public static WebElement fluentWait(final WebElement webElement, int timeInSec) {
        FluentWait<WebDriver> wait = new FluentWait<WebDriver>(Driver.getDriver())
                .withTimeout(Duration.ofSeconds(timeInSec))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class);
        WebElement element = wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return webElement;
            }
        });
        return element;
    }

    // Verifies whether the element matching the provided locator is displayed on page
    // fails if the element matching the provided locator is not found or not displayed

    public static void verifyElementDisplayed(By by) {
        try {
            assertTrue("Element not visible: " + by, Driver.getDriver().findElement(by).isDisplayed());
        } catch (NoSuchElementException e) {
            Assert.fail("Element not found: " + by);

        }
    }

    public void selectCheckBox(WebElement element, boolean check) {
        if (check) {
            if (!element.isSelected()) {
                element.click();
            }
        } else {
            if (element.isSelected()) {
                element.click();
            }
        }
    }
}

