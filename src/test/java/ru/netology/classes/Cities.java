package ru.netology.classes;

public enum Cities {
    MOSKOW("Москва"), KEMEROVO("Кемерово"), SIMPHEROPOL("Симферополь"),
    SMOLENSK("Смоленск"), TOMBOV("Томбов"), KIROV("Киров"), KRASNODAR("Краснодар"),
    KRASNOYARSK("Красноярск"), KURSK("Курск"), KURGAN("Курган");

    private final String cityname;

    Cities(String cityname) {
        this.cityname = cityname;
    }

    public String getCityname() {
        return cityname;
    }
}
