package equalexperts.step_definitions;


import equalexperts.helper.Helper;
import equalexperts.utilities.Driver;
import equalexperts.utilities.MyScreenRecorder;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.util.concurrent.TimeUnit;

public class Hook {
Helper helper;
    @Before
    public void setUp() throws Exception {
        //Maximize current window
        Driver.getDriver().manage().window().maximize();
        Driver.getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        // Record every test case
        MyScreenRecorder.startRecord("equalexperts-test-record");
    }

    //close the browser after test cases are executed
    @After
    public void tearDown(Scenario scenario) throws Exception {
        if(scenario.isFailed()) {
            final byte[] screenshot = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image.png", "screenshot");
        }
        Driver.closeDriver();
        MyScreenRecorder.stopRecord();
    }
}
