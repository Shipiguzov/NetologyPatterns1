package ru.netology.pageObjects;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.Keys;
import ru.netology.classes.FormData;


import java.time.format.DateTimeFormatter;

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
        if (data.getCheckbox())
            $(Selectors.byClassName("checkbox__box")).click();
        $(Selectors.byText("Запланировать")).click();
    }

    /**
     * Select city by click from droplist
     *
     * @param firstLetters - first letters of city
     * @param cityName     - full city name
     */
    public static void selectCityFromList(String firstLetters, String cityName) {
        //TODO complete check cities from list
        ElementsCollection elements = $$(Selectors.byCssSelector(".menu-item__control"));
        for (SelenideElement element : elements) {
            Assertions.assertTrue(element.getText().contains(firstLetters));
        }
        $(Selectors.byAttribute("type", "text")).setValue(firstLetters);
        $(Selectors.withText(cityName)).click();
    }

    /**
     * Method change date from calendar
     * @param day - how many day passes from choosen date
     */
    public static void selectDayFromCalendar(long day) {
        $(Selectors.byClassName("input_type_tel")).click();
        String data = $(Selectors.byClassName("calendar__day_state_current")).getAttribute("data-day");
        long newDate = Long.valueOf(data) + BETWEEN_DAYS * day;
        $(Selectors.byAttribute("data-day", String.valueOf(newDate))).click();
    }

}
