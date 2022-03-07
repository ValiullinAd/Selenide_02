

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;

import java.beans.PropertyEditor;
import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import java.time.LocalDate;

public class DeliveryCardTest {
        @Test
        void shouldTest() {
            LocalDate today = LocalDate.now();
            int year = today.getYear();
            int month = today.getMonthValue();
            int day = today.getDayOfMonth();
            Configuration.holdBrowserOpen = true;
            Configuration.browserSize = "1600x900";
            Configuration.browser = "Chrome";
            open("http://localhost:9999");

            $("[data-test-id=city] input").setValue("Москва");
            $(byName("name")).setValue("Иванов Иван");
            $("[data-test-id=phone] input").setValue("+79270000000");
            $("[data-test-id=agreement]").click();
            $("[data-test-id=date] input").doubleClick();
            $("[data-test-id=date] input").sendKeys(" ");
            //$("[data-test-id=date] input").sendKeys(""+day+month+year);
            //$("[data-test-id=date] input").setValue(""+today);
            $("[data-test-id=date] input").setValue("20.03.2022");
            //$("[data-test-id=date] input").setValue(""+day+month+year);
            $(byText("Забронировать")).click();
            $(withText("Успешно!")).shouldBe(Condition.appear, Duration.ofSeconds(15));
        }
}

