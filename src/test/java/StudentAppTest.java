import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import lv.acodemy.page_object.AddStudentPage;
import lv.acodemy.page_object.MainPage;
import lv.acodemy.page_object.Notifications;
import lv.acodemy.utils.LocalDriverManager;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import static java.time.Duration.ofSeconds;
import static lv.acodemy.utils.ConfigurationProperties.getConfiguration;
import static org.assertj.core.api.Assertions.*;

@Slf4j
public class StudentAppTest {

    Faker fakeData = new Faker();

    WebDriverWait wait = new WebDriverWait(LocalDriverManager.getInstance(), ofSeconds(getConfiguration().getLong("wait.time")));
    MainPage mainPage = new MainPage();
    AddStudentPage addStudentPage = new AddStudentPage();
    Notifications notifications = new Notifications(wait);

    @Test
    public void createStudentTest() {
        LocalDriverManager.getInstance().manage().timeouts().implicitlyWait(ofSeconds(getConfiguration().getLong("wait.time")));
        logger.info("Will open now: " + getConfiguration().getString("app.url"));
        LocalDriverManager.getInstance().get(getConfiguration().getString("app.url"));

        mainPage.openAddStudentForm();

        addStudentPage.setNameField(fakeData.name().fullName());
        addStudentPage.setEmailField(fakeData.internet().emailAddress());
        addStudentPage.setGender("female");
        addStudentPage.clickSubmitButton();

        assertThat(notifications.getNotificationSuccessMessage()).isEqualTo("Student successfully added");
    }

    @AfterMethod
    public void tearDown() {
        LocalDriverManager.closeDriver();
    }
}
