import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.Keys;

import static org.openqa.selenium.Keys.*;


public class DeliveryCardTest {


    String generateDate(int days, String pattern) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
        Configuration.holdBrowserOpen = true;
        Configuration.browserSize = "1600x900";
        Configuration.browser = "Chrome";

    }

    @Test
    void shouldTestForm() {

        String planningDate = generateDate(3, "dd.MM.uuuu");
        $("[data-test-id=city] input").setValue("Москва");
        $(byName("name")).setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79270000000");
        $("[data-test-id=agreement]").click();
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(planningDate);
        $(byText("Забронировать")).click();
        $(withText("Успешно!")).shouldBe(Condition.appear, Duration.ofSeconds(15));
    }
    @Test
    public void shouldTestSuccess() {

        String planningDate = generateDate(5, "dd.MM.uuuu");
        $("[data-test-id=city] input").val("Омск");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(planningDate);
        $(byName("name")).setValue("Петров Петя");
        $("[data-test-id=phone] input").setValue("+79050000001");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[class='notification__content']").shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15));
    }
    @Test
    public void shouldTestInvalidCity() {

        String planningDate = generateDate(5, "dd.MM.uuuu");
        $("[data-test-id=city] input").val("Киев");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(planningDate);
        $(byName("name")).setValue("Петров Петя");
        $("[data-test-id=phone] input").setValue("+79050000001");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[class='input__sub']").shouldHave(Condition.text("Доставка в выбранный город недоступна"), Duration.ofSeconds(15));
    }
}
