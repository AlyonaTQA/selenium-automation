import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import static lv.acodemy.utils.ConfigurationProperties.getConfiguration;

@Slf4j
public class SauceDemoTest {

    ChromeDriver driver = new ChromeDriver();

    @Test
    public void loginSauceDemoTest() {
        logger.info("Will open now: " + getConfiguration().getString("sauce.demo.url"));
        driver.get(getConfiguration().getString("sauce.demo.url"));

        WebElement usernameInput = driver.findElement(By.id("user-name"));
        usernameInput.sendKeys("standard_user");
        WebElement passwordInput = driver.findElement(By.id("password"));
        passwordInput.sendKeys("secret_sauce");
        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();

        WebElement shoppingCartContainer = driver.findElement(By.id("shopping_cart_container"));
        Assertions.assertThat(shoppingCartContainer.isDisplayed()).as("Must be visible").isTrue();
    }

    @Test
    public void incorrectLoginSauceDemoTest() {
        logger.info("Will open now: " + getConfiguration().getString("sauce.demo.url"));
        driver.get(getConfiguration().getString("sauce.demo.url"));

        WebElement usernameInput = driver.findElement(By.id("user-name"));
        usernameInput.sendKeys("standard_user1");
        WebElement passwordInput = driver.findElement(By.id("password"));
        passwordInput.sendKeys("secret_sauce");
        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();

        WebElement errorMessage = driver.findElement(By.xpath("//h3[@data-test='error']"));
        Assertions.assertThat(errorMessage.getText()).isEqualTo("Epic sadface: Username and password do not match any user in this service");
    }

    @AfterTest
    public void tearDown() {
        driver.close();
        driver.quit();
    }
}
