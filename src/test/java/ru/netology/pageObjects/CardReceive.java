package ru.netology.pageObjects;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.Keys;
import ru.netology.classes.Cities;
import ru.netology.classes.FormData;


import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Formatter;
import java.util.Locale;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

/**
 * Class with method that fill creditcard receiving form
 */
public class CardReceive {

    final static long BETWEEN_DAYS = 86_400_000;

    /**
     * Method fill creditcard receiving form
     *
     * @param data class with all parameters needed to fill creditcard receive form
     */
    public static void fillCardForm(FormData data) {
        $(Selectors.byAttribute("type", "text")).setValue(data.getCity());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.YYYY");
        $("[class='input__control'][type='tel']").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[class='input__control'][type='tel']").append(data.getDate().format(formatter)).pressEnter();
        $("[name=\"name\"]").setValue(data.getFullName());
        $("[name=\"phone\"]").setValue(data.getPhone());
        $(Selectors.byClassName("checkbox__box")).click();
        $(Selectors.byText("Запланировать")).click();
    }

    public static void fillCardFormWithUnckeckedChekbox(FormData data) {
        $(Selectors.byAttribute("type", "text")).setValue(data.getCity());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.YYYY");
        $("[class='input__control'][type='tel']").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[class='input__control'][type='tel']").append(data.getDate().format(formatter)).pressEnter();
        $("[name=\"name\"]").setValue(data.getFullName());
        $("[name=\"phone\"]").setValue(data.getPhone());
        $(Selectors.byText("Запланировать")).click();
    }

    /**
     * Select city by click from droplist
     *
     * @param cityName - full city name
     */
    public static void selectCityFromList(int numberOfLetters, String cityName) {
        $(Selectors.byAttribute("type", "text")).setValue(cityName.substring(0, numberOfLetters));
        ElementsCollection elements = $$(".menu-item_type_block > .menu-item__control");
        for (SelenideElement element : elements) {
            Assertions.assertTrue(element.getText().toLowerCase(Locale.ROOT).contains(cityName.substring(0, numberOfLetters).toLowerCase()));
        }
        $(Selectors.withText(cityName)).click();
    }

    /**
     * Method change date from calendar
     *
     * @param day - how many day passes from choosen date
     */
    public static void selectDayFromCalendar(long day) {
        $(Selectors.byClassName("input_type_tel")).click();
        String data = $(Selectors.byClassName("calendar__day_state_current")).getAttribute("data-day");
        long newDate = Long.valueOf(data) + BETWEEN_DAYS * day;
        $(Selectors.byAttribute("data-day", String.valueOf(newDate))).click();
    }

    public String getRandomCity() {
        int number = (int) (Math.random() * Cities.values().length);
        return Cities.values()[number].getCityname();
    }

    public static void checkPopupWindow(LocalDate date) {
        $(Selectors.withText("Успешно!"))
                .should(Condition.appear, Duration.ofSeconds(15));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.YYYY");
        String actualResult = $(".notification__content").getText();
        Assertions.assertTrue(actualResult.contains(date.format(formatter)));
    }

    public static void clickButton() {
        $(Selectors.byText("Запланировать")).click();
    }
}
