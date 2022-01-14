package equalexperts.pages;

import equalexperts.helper.Helper;
import equalexperts.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class BookingPage extends BasePage{
    Helper helper;

    @FindBy(css = "#firstname")
    public WebElement firstNameInput;

    @FindBy(css = "#lastname")
    public WebElement lastNameInput;

    @FindBy(css = "#totalprice")
    public WebElement totalPriceInput;

    @FindBy(css = "#depositpaid")
    public WebElement depositPaidDropBox;

    @FindBy(xpath = "//option[contains(text(), 'true')]")
    public WebElement depositPaidTrue;

    @FindBy(xpath = "//option[contains(text(), 'true')]")
    public WebElement depositPaidFalse;
//Calendars
    @FindBy(css = "#checkin")
    public WebElement checkInDateInput;

    @FindBy(xpath = "//a[@data-handler='prev']")
    public WebElement datePickerPrevElement;

    @FindBy(xpath = "//a[@data-handler='next']")
    public WebElement nextDatePickerPage;

    @FindBy(css = "#checkout")
    public WebElement checkOutDateInput;

    @FindBy(xpath = "//a[@Class='ui-state-default']")
    public List<WebElement> daysOfCalendar;

    @FindBy(xpath = "//span[@Class='ui-datepicker-month']")
    public WebElement monthOfTheYear;

    @FindBy(xpath = "//span[@class='ui-datepicker-year']")
    public WebElement yearOfTheCalendar;

//end of calendars
    @FindBy(xpath = "//input[@value=' Save ']")
    public WebElement saveBookingButton;


    @FindBy(xpath = "//div[@class='row'][(@id)]")
    public List<WebElement> bookingList;


    public void enterFirstName(String firstName){
        firstNameInput.sendKeys(firstName);
    }

    public void enterLastName(String lastName){
        lastNameInput.sendKeys(lastName);
    }

    public void enterPrice(Integer price){
        totalPriceInput.sendKeys(price.toString());
    }

    public void selectDepositPaid(String option){
        Helper.waitForClickability(depositPaidDropBox, 3000).click();
        Select objSelect =new Select(depositPaidDropBox);
        objSelect.selectByVisibleText(option);
    }

    public void clickOnCheckInDateInput() {

        checkInDateInput.click();
    }


    public void clickOnCheckOutDateInput() {

        checkOutDateInput.click();
    }


    public void saveBooking(){

        saveBookingButton.click();
    }


    public void selectMonthOfYear(String month){
        while (!monthOfTheYear.getText().contains(month)){
            nextDatePickerPage.click();
        }
    }

    public void selectYearOfCalendar(String year){
        while (!yearOfTheCalendar.getText().contains(year)){
            nextDatePickerPage.click();
        }
    }

    public List<WebElement> getBookingList(){
       return bookingList;
    }


    @FindBy(xpath = "//div[@id='37361']/div/input")
    WebElement xyz;

    public WebElement getDeleteButtonElement(String id){
        String xPath = "//div[@id='" + id + "']/div/input";
        return Driver.getDriver().findElement(By.xpath(xPath));
    }

    public void deleteBookingFromList(String firstName) throws InterruptedException {
        Thread.sleep(5000);
        for (WebElement booking : bookingList) {
            if (booking.getText().contains(firstName)){
                String id = booking.getAttribute("id");
                getDeleteButtonElement(id).click();
            }
        }
    }

}
