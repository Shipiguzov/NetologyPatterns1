package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.*;
import ru.netology.classes.DataGenerator;
import ru.netology.classes.FormData;
import ru.netology.pageObjects.CardReceive;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.codeborne.selenide.Selenide.*;

public class SelenidTests {

    @BeforeAll
    static void setupAll() {
    }

    @BeforeEach
    void setup() {
        /*ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");*/
        open("http://localhost:9999/");
    }

    @AfterEach
    void shutdown() {
    }

    //Positive tests

    //All data enters like text
    @Test
    void correctTest() {
        FormData data = DataGenerator.fillAllFieldCorrectly();
        CardReceive.fillCardForm(data);
        $(Selectors.byText("Запланировать")).click();
        CardReceive.checkPopupWindow(data.getDate());
    }

    //All data enters like text with double surname
    @Test
    void correctTestWithDoubleSurname() {
        FormData data = DataGenerator.fillAllFieldCorrectlyWithDoubleSurname();
        CardReceive.fillCardForm(data);
        $(Selectors.byText("Запланировать")).click();
        CardReceive.checkPopupWindow(data.getDate());
    }

    //All data enters like text, name consist "ё" letter
    //Test will be fail with "ё" letter
    //POSITIVE TEST FAIL
    @Test
    void correctTestWithLetter() {
        FormData data = DataGenerator.fillAllFieldCorrectlyWithLetter();
        CardReceive.fillCardForm(data);
        $(Selectors.byText("Запланировать")).click();
        CardReceive.checkPopupWindow(data.getDate());
    }

    //City selects by click after enter first letter
    @Test
    void correctTestCitySelectsByClick() {
        FormData data = DataGenerator.emptyCity();
        CardReceive.fillCardForm(data);
        Faker faker = new Faker(new Locale("ru"));
        data.setCity(faker.address().city());
        int numberOfLetters = 3;
        CardReceive.selectCityFromList(numberOfLetters, data.getCity());
        $(Selectors.byText("Запланировать")).click();
        CardReceive.checkPopupWindow(data.getDate());
    }

    //Data selects from calendar
    @Test
    void dateFromCalendar() {
        FormData data = DataGenerator.fillAllFieldCorrectly();
        CardReceive.fillCardForm(data);
        CardReceive.selectDayFromCalendar(7);
        $(Selectors.byText("Запланировать")).click();
        $(Selectors.withText("Успешно!")).should(Condition.appear, Duration.ofSeconds(15));
        CardReceive.checkPopupWindow(data.getDate());
    }

    //Test with replace delivery
    @Test
    void testWithSameDate() {
        int daysPlus = 5;
        FormData data = DataGenerator.fillAllFieldCorrectly();
        CardReceive.fillCardForm(data);
        $(Selectors.byText("Запланировать")).click();
        CardReceive.checkPopupWindow(data.getDate());;
        CardReceive.selectDayFromCalendar(daysPlus);
        $(Selectors.byText("Запланировать")).click();
        $(Selectors.byText("Перепланировать")).click();
        CardReceive.checkPopupWindow(data.getDate().plusDays(daysPlus));
    }

    //Negative tests
    //City name in English
    @Test
    void incorrectCity() {
        FormData data = DataGenerator.fillCityInEnglish();
        CardReceive.fillCardForm(data);
        $(Selectors.byText("Запланировать")).click();
        $(Selectors.byText("Доставка в выбранный город недоступна"))
                .should(Condition.appear, Duration.ofSeconds(15));
    }

    //Name in English
    @Test
    void englishName() {
        FormData data = DataGenerator.fillNameInEnglish();
        CardReceive.fillCardForm(data);
        $(Selectors.byText("Запланировать")).click();
        $(Selectors.withText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."))
                .should(Condition.appear, Duration.ofSeconds(15));
    }

    // City not on list
    @Test
    void cityNotFromList() {
        FormData data = DataGenerator.cityNotInList();
        CardReceive.fillCardForm(data);
        $(Selectors.byText("Запланировать")).click();
        $(Selectors.byText("Доставка в выбранный город недоступна"))
                .should(Condition.appear, Duration.ofSeconds(15));
    }

    //Entered wrong data
    @Test
    void wrongDate() {
        FormData data = DataGenerator.wrongDate();
        CardReceive.fillCardForm(data);
        $(Selectors.byText("Запланировать")).click();
        $(Selectors.withText("Заказ на выбранную дату невозможен"))
                .should(Condition.appear, Duration.ofSeconds(15));
    }

    //Empty phone number
    @Test
    void emptyPhoneNumberTest() {
        FormData data = DataGenerator.emptyPhoneNumber();
        CardReceive.fillCardForm(data);
        $(Selectors.byText("Запланировать")).click();
        $(Selectors.withText("Поле обязательно для заполнения"))
                .should(Condition.appear, Duration.ofSeconds(15));
    }

    //Test with wrong phone number
    //NEGATIVE TEST NOT FAIL
    @Test
    void wrongPhoneNumberTest() {
        FormData data = DataGenerator.wrongPhoneNumber();
        CardReceive.fillCardForm(data);
        $(Selectors.byText("Запланировать")).click();
        $(Selectors.withText("Поле обязательно для заполненияТелефон указан неверно. Должно быть 11 цифр, например, +79012345678."))
                .should(Condition.appear, Duration.ofSeconds(15));
    }

    //checkbox unchecked
    @Test
    void checkboxError() {
        FormData data = DataGenerator.uncheckedCheckBox();
        CardReceive.fillCardForm(data);
        $(Selectors.byText("Запланировать")).click();
        $(Selectors.byClassName("input_invalid"))
                .should(Condition.appear, Duration.ofSeconds(15));
    }
}
