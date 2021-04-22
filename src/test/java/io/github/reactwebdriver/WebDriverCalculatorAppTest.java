package io.github.reactwebdriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.NoSuchElementException;

import static org.testng.Assert.*;

public class WebDriverCalculatorAppTest {

    @ByReactComponent.FindBy(
            name = "t",
            props = "{\"name\": \"=\"}"
    )
    public WebElement equalToButton;

    @ByReactComponent.FindBy(
            name = "t",
            props = "{ \"name\": \"invalid\" }"
    )
    public WebElement invalidElement;

    WebDriver driver;
    ReactWebDriver reactWebDriver;

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        reactWebDriver = new ReactWebDriver((JavascriptExecutor) driver, "#root");
        PageFactory.initElements(driver, this);
    }

    @BeforeMethod
    public void refreshWebPage() {
        driver.get("https://ahfarmer.github.io/calculator/");
        reactWebDriver.waitForReactToLoad();
        PageFactory.initElements(driver, this);
    }

    @AfterClass
    public void teardown() {
        driver.quit();
    }

    @Test
    public void testFindByAnnotation() {
        assertEquals(driver.findElements(ByReact.component("t")).size(), 22);

        /* Invalid react locator */
        assertEquals(driver.findElements(ByReact.component("Calculator")).size(), 0);

        /* Full component name and props */
        assertEquals(driver.findElement(ByReact.component("t").props("{ \"name\": \"5\" }")).getText(), "5");

        /* wildcard component name and props */
        assertEquals(driver.findElement(ByReact.component("*").props("{ \"name\": \"3\" }")).getText(), "3");

        /* Using @Findy annotation */
        assertEquals(equalToButton.getText(), "=");
    }

    @Test
    public void testInteraction() {

        /* Calculate 5*3 and check the result is 15 */
        driver.findElement(ByReact.component("t").props("{ \"name\": \"5\" }")).click();
        driver.findElement(ByReact.component("t").props("{ \"name\": \"x\" }")).click();
        driver.findElement(ByReact.component("t").props("{ \"name\": \"3\" }")).click();
        equalToButton.click();
        assertEquals(driver.findElement(By.cssSelector(".component-display")).getText(), "15");
    }

    @Test
    public void testReactWebDriver() {
        ReactComponent reactComponent = reactWebDriver.getComponent(ByReact.component("t").props("{ \"name\": \"5\" }"));
        assertEquals(reactComponent.getProp("name"), "5");

        /* Get component by index*/
        ReactComponent fifthComponent = reactWebDriver.getComponent(ByReact.component("t"), 5);
        assertEquals(fifthComponent.getProp("name"), "%");
    }

    @Test
    public void testNoSuchElementFoundException() {
        try {
            driver.findElement(ByReact.component("t").props("{ \"name\": \"invalid\" }")).click();
            fail("No exception thrown for invalid selector");
        } catch (NoSuchElementException e) {
            assertEquals(e.getLocalizedMessage(), "Cannot locate an element using ByReactComponent [component=t] [props={ \"name\": \"invalid\" }] [state={}]");
        }
    }

    @Test
    public void testNoSuchElementFoundExceptionUsingFindBy() {
        try {
            invalidElement.click();
            fail("No exception thrown for invalid selector using @FindBy");
        } catch (NoSuchElementException e) {
            assertEquals(e.getLocalizedMessage(), "Cannot locate an element using ByReactComponent [component=t] [props={ \"name\": \"invalid\" }] [state={}]");
        }
    }

}
