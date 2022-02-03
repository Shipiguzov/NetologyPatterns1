package ru.netology.pageObjects;

import com.codeborne.selenide.Selectors;
import com.github.javafaker.Faker;
import ru.netology.classes.FormData;


import java.text.Normalizer;
import java.time.LocalDate;
import java.util.Locale;

import static com.codeborne.selenide.Selenide.$;

/** Class with method that fill creditcard receiving form
 *
 */
public class CardReceive {

    /** Method fill creditcard receiving form
     *
     * @param data class with all parameters needed to fill creditcard receive form
     */
    public static void fillCardForm(FormData data) {

        $(Selectors.byAttribute("type", "text")).setValue(data.getCity());
        $("[class='input__control'][type='tel']").setValue(data.getDate().toString());
        $("[name=\"name\"]").setValue(data.getFullName());
        $("[name=\"phone\"]").setValue(data.getPhone());
        if (data.getCheckbox())
            $(Selectors.byClassName("checkbox__box")).click();
        $(Selectors.byText("Запланировать")).click();
    }

    public static void setNextDay(LocalDate date){
        $("[class='input__control'][type='tel']").setValue(date.toString());
    }

}
