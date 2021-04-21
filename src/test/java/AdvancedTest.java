import com.testninja.reactwebdriver.ByReact;
import com.testninja.reactwebdriver.ByReactComponent;
import com.testninja.reactwebdriver.ReactWebDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class AdvancedTest {

    @ByReactComponent.FindBy(
            name = "Nav"
    )
    public WebElement nav;

    WebDriver driver;

    @BeforeClass
    public void setup() throws Exception {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://demo.themesberg.com/volt-pro-react/#/?status=accepted&expires=1621511475&p_sid=113942&p_aid=122967&p_link=3179&p_tok=324e05ce-599b-4b8c-8f28-3b4c5a66399d");
        Thread.sleep(5000);
        PageFactory.initElements(driver, this);
    }

    @AfterClass
    public void teardown() {
        //driver.quit();
    }

    @Test
    public void test() throws Exception {
        WebElement navLink = nav.findElement(ByReact.component("NavLink").props("{\"to\": \"#features\"}"));
        System.out.println(nav.toString());
    }
}
