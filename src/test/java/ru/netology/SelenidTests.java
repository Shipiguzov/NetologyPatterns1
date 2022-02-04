package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.classes.FormData;
import ru.netology.pageObjects.CardReceive;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Locale;

import static com.codeborne.selenide.Selenide.*;

public class SelenidTests {

    static long BETWEEN_DAYS = 86_400_000;
    private Faker faker;

    @BeforeAll
    static void setupAll() {
    }

    @BeforeEach
    void setup() {
        /*ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");*/
        faker = new Faker(new Locale("ru"));
        open("http://localhost:9999/");
    }

    @AfterEach
    void shutdown() {
    }

    //Positive tests

    //All data enters like text
    @Test
    void correctTest() {
        FormData data = new FormData(
                faker.address().city(),
                LocalDate.now().plusDays(5),
                faker.name().fullName(),
                faker.phoneNumber().phoneNumber(),
                true);
        CardReceive.fillCardForm(data);
        $(Selectors.byText("Запланировать")).click();
        $(Selectors.withText("Успешно!")).should(Condition.appear, Duration.ofSeconds(15));
    }

    //City selects by click after enter first letter
    @Test
    void correctTestCitySelectsByClick() {
        FormData data = new FormData(
                "",
                LocalDate.now().plusDays(5),
                faker.name().fullName(),
                faker.phoneNumber().phoneNumber(),
                true);
        CardReceive.fillCardForm(data);
        data.setCity(faker.address().city());
        CardReceive.selectCityFromList(data.getCity().substring(0,3), data.getCity());
        $(Selectors.byText("Запланировать")).click();
        $(Selectors.withText("Успешно!")).should(Condition.appear, Duration.ofSeconds(15));
    }

    //Data selects from calendar
    @Test
    void dateFromCalendar() {
        FormData data = new FormData(
                faker.address().city(),
                LocalDate.now().plusDays(7),
                faker.name().fullName(),
                faker.phoneNumber().phoneNumber(),
                true);
        CardReceive.fillCardForm(data);
        CardReceive.selectDayFromCalendar(7);
        $(Selectors.byText("Запланировать")).click();
        $(Selectors.withText("Успешно!")).should(Condition.appear, Duration.ofSeconds(15));
    }

    //Test with replace delivery
    @Test
    void testWithSameDate() {
        FormData data = new FormData(
                faker.address().city(),
                LocalDate.now().plusDays(7),
                faker.name().fullName(),
                faker.phoneNumber().phoneNumber(),
                true);
        CardReceive.fillCardForm(data);
        $(Selectors.byText("Запланировать")).click();
        CardReceive.selectDayFromCalendar(5);
        $(Selectors.byText("Запланировать")).click();
        $(Selectors.byText("Перепланировать")).click();
        $(Selectors.withText("Успешно!")).should(Condition.appear, Duration.ofSeconds(15));
    }

    //Negative tests
    //City name in English
    @Test
    void incorrectCity() {
        Faker fakerEnglish = new Faker(new Locale("en"));
        FormData data = new FormData(
                fakerEnglish.address().city(),
                LocalDate.now().plusDays(5),
                faker.name().fullName(),
                faker.phoneNumber().phoneNumber(),
                true);
        CardReceive.fillCardForm(data);
        $(Selectors.byText("Запланировать")).click();
        $(Selectors.byText("Доставка в выбранный город недоступна"))
                .should(Condition.appear, Duration.ofSeconds(15));
    }

    //Entered wrong data
    @Test
    void wrongDate() {
        FormData data = new FormData(
                faker.address().city(),
                LocalDate.now(),
                faker.name().fullName(),
                faker.phoneNumber().phoneNumber(),
                true);
        CardReceive.fillCardForm(data);
        $(Selectors.byText("Запланировать")).click();
        $(Selectors.withText("Заказ на выбранную дату невозможен"))
                .should(Condition.appear, Duration.ofSeconds(15));
    }

    //Empty phone number
    @Test
    void emptyPhoneNumberTest() {
        FormData data = new FormData(
                faker.address().city(),
                LocalDate.now().plusDays(5),
                faker.name().fullName(),
                "",
                true);
        CardReceive.fillCardForm(data);
        $(Selectors.byText("Запланировать")).click();
        $(Selectors.withText("Поле обязательно для заполнения"))
                .should(Condition.appear, Duration.ofSeconds(15));
    }

    //Test with wrong phone number
    @Test
    void wrongPhoneNumberTest() {
        FormData data = new FormData(
                faker.address().city(),
                LocalDate.now().plusDays(5),
                faker.name().fullName(),
                "+" + faker.phoneNumber().subscriberNumber(5),
                true);
        CardReceive.fillCardForm(data);
        $(Selectors.byText("Запланировать")).click();
        $(Selectors.withText("Поле обязательно для заполненияТелефон указан неверно. Должно быть 11 цифр, например, +79012345678."))
                .should(Condition.appear, Duration.ofSeconds(15));
    }

    //checkbox unchecked
    @Test
    void checkboxError() {
        FormData data = new FormData(
                faker.address().city(),
                LocalDate.now().plusDays(5),
                faker.name().fullName(),
                "+" + faker.phoneNumber().subscriberNumber(5),
                false);
        CardReceive.fillCardForm(data);
        $(Selectors.byText("Запланировать")).click();
        $(Selectors.byClassName("input_invalid"))
                .should(Condition.appear, Duration.ofSeconds(15));
    }
}
