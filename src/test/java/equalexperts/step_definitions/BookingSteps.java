package equalexperts.step_definitions;



import com.github.javafaker.Faker;
import equalexperts.helper.Helper;
import equalexperts.pages.BookingPage;
import equalexperts.utilities.ConfigurationReader;
import equalexperts.utilities.Driver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;


public class BookingSteps {
    Helper helper;
    Faker faker = new Faker();
    BookingPage bookingPage = new BookingPage();
    String firstName = faker.name().firstName();
    String lastName = faker.name().lastName();
    Integer price = faker.number().numberBetween(40, 200);
    String deposit = "true";


    @Given("I am on the Equalexperts homepage")
    public void i_am_on_the_equalexperts_homepage() {
        Driver.getDriver().get(ConfigurationReader.get("base_url"));
    }


    @When("I enter customer information {string} {string}")
    public void i_enter_customer_information(String checkInDate, String checkOutDate) {
        bookingPage.enterFirstName(firstName);
        bookingPage.enterLastName(lastName);
        bookingPage.enterPrice(price);
        bookingPage.selectDepositPaid(deposit);
        bookingPage.clickOnCheckInDateInput();
        Helper.selectDate(checkInDate);
        bookingPage.clickOnCheckOutDateInput();
        Helper.selectDate(checkOutDate);
    }

    @When("i submit the booking")
    public void i_submit_the_booking() throws InterruptedException {
        Thread.sleep(1000);
        bookingPage.saveBooking();
    }


    @Then("the booking is successfully stored")
    public void theBookingIsSuccessfullyStored() throws InterruptedException {
        Thread.sleep(5000);
        Assert.assertTrue(helper.isBookingAvailable(bookingPage.getBookingList(), firstName, lastName ));
    }

    @Given("for after {int} days i wants make a booking for {int} nights")
    public void forAfterDaysIWantsMakeABookingForNights(int dayOfFuture, int duration) throws InterruptedException {
        String checkInDate1 = Helper.getDate(duration);
        int futureDayOfCheckOut = dayOfFuture + duration;
        String checkOutDate2 = Helper.getDate(futureDayOfCheckOut);
        bookingPage.enterFirstName(firstName);
        bookingPage.enterLastName(lastName);
        bookingPage.enterPrice(price);
        bookingPage.selectDepositPaid(deposit);
        bookingPage.clickOnCheckInDateInput();
        Helper.selectDate(checkInDate1);
        bookingPage.clickOnCheckOutDateInput();
        Helper.selectDate(checkOutDate2);
    }


    @Then("the booking is deleted successfully")
    public void theBookingIsDeletedSuccessfully() throws InterruptedException {
        Thread.sleep(500);
        Assert.assertFalse(Helper.isBookingAvailable(bookingPage.getBookingList(), firstName, lastName));
    }

    @When("i try to delete a booking from the booking list")
    public void iTryToDeleteABookingFromTheBookingList() throws InterruptedException {
        Thread.sleep(5000);
        bookingPage.deleteBookingFromList(firstName);
    }


    @When("i try make a booking {int} days in advance for {int} nights with non-negative {int}")
    public void i_try_make_a_booking_days_in_advance_for_nights_with_non_negative(Integer dayOfFuture, Integer duration, Integer negativePrice) {
        String checkInDate1 = Helper.getDate(duration);
        int futureDayOfCheckOut = dayOfFuture + duration;
        String checkOutDate2 = Helper.getDate(futureDayOfCheckOut);
        bookingPage.enterFirstName(firstName);
        bookingPage.enterLastName(lastName);
        bookingPage.enterPrice(negativePrice);
        bookingPage.clickOnCheckInDateInput();
        Helper.selectDate(checkInDate1);
        bookingPage.clickOnCheckOutDateInput();
        Helper.selectDate(checkOutDate2);
    }



    @Then("the booking is not stored")
    public void theBookingIsNotStored() throws InterruptedException {
        Thread.sleep(5000);
        Assert.assertFalse(Helper.isBookingAvailable(bookingPage.getBookingList(), firstName, lastName ));
    }


    @When("i try to create a booking without mandatory filed")
    public void iTryToCreateABookingWithoutMandatoryFiled() {
        // implement non-mandatory fields
    }

    @Then("i verify warning messages")
    public void iVerifyWarningMessages() {
        // since there is not warning message in web page the implementation cant be performed
        // I would use a jeneric method with java steam to verify error messages
    }



}
