package ru.netology.pageObjects;

import com.codeborne.selenide.Selectors;
import org.openqa.selenium.Keys;
import ru.netology.classes.FormData;


import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;

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
        $(Selectors.byAttribute("type", "text")).setValue(firstLetters);
        $(Selectors.withText(cityName)).click();
    }

    //TODO
    public static void selectDayFromCalendar(long day) {
        $(Selectors.byClassName("input_type_tel")).click();
        String data = $(Selectors.byClassName("calendar__day_state_current")).getAttribute("data-day");
        long newDate = Long.valueOf(data) + BETWEEN_DAYS * day;
        System.out.println(newDate);
        $(Selectors.byAttribute("data-day", String.valueOf(newDate))).click();
    }

}
