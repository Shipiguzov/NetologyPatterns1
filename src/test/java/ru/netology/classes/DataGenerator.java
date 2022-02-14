package ru.netology.classes;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.util.Locale;


public class DataGenerator {

    private static Faker faker = new Faker(new Locale("ru"));

    public static int randomNumber(int minRange, int maxRange) {
        return (int) (Math.random() * (maxRange - minRange + 1)) + minRange;
    }

    private static String getCityFromList(){
        int number = randomNumber(0, 9);
        return Cities.values()[number].getCityname();
    }

    public static FormData fillAllFieldCorrectly() {
        return new FormData(
                getCityFromList(),
                LocalDate.now().plusDays(5),
                faker.name().fullName(),
                faker.phoneNumber().phoneNumber(),
                true);
    }

    public static FormData fillAllFieldCorrectlyWithDoubleSurname() {
        return new FormData(
                getCityFromList(),
                LocalDate.now().plusDays(5),
                faker.name().nameWithMiddle(),
                faker.phoneNumber().phoneNumber(),
                true);
    }

    public static FormData fillAllFieldCorrectlyWithLetter() {
        return new FormData(
                getCityFromList(),
                LocalDate.now().plusDays(5),
                faker.name().fullName() + "ё",
                faker.phoneNumber().phoneNumber(),
                true);
    }

    public static FormData emptyCity() {
        return new FormData(
                "",
                LocalDate.now().plusDays(5),
                faker.name().fullName(),
                faker.phoneNumber().phoneNumber(),
                true);
    }

    public static FormData fillCityInEnglish(){
        Faker fakerEnglish = new Faker(new Locale("en"));
        return new FormData(
                fakerEnglish.address().city(),
                LocalDate.now().plusDays(5),
                faker.name().fullName(),
                faker.phoneNumber().phoneNumber(),
                true);
    }

    public static FormData fillNameInEnglish(){
        Faker fakerEnglish = new Faker(new Locale("en"));
        return new FormData(
                getCityFromList(),
                LocalDate.now().plusDays(5),
                fakerEnglish.name().fullName(),
                faker.phoneNumber().phoneNumber(),
                true);
    }

    public static FormData cityNotInList(){
        return new FormData(
                "Тольятти",
                LocalDate.now().plusDays(5),
                faker.name().fullName(),
                faker.phoneNumber().phoneNumber(),
                true);
    }

    public static FormData wrongDate(){
        return new FormData(
                getCityFromList(),
                LocalDate.now(),
                faker.name().fullName(),
                faker.phoneNumber().phoneNumber(),
                true);
    }

    public static FormData emptyPhoneNumber(){
        return new FormData(
                getCityFromList(),
                LocalDate.now().plusDays(5),
                faker.name().fullName(),
                "",
                true);
    }

    public static FormData wrongPhoneNumber(){
        return new FormData(
                getCityFromList(),
                LocalDate.now().plusDays(5),
                faker.name().fullName(),
                "+" + faker.phoneNumber().subscriberNumber(5),
                true);
    }

    public static FormData uncheckedCheckBox(){
        return new FormData(
                getCityFromList(),
                LocalDate.now().plusDays(5),
                faker.name().fullName(),
                "+" + faker.phoneNumber().subscriberNumber(5),
                false);
    }
}
