package ru.netology.classes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

import java.time.LocalDate;

@Value
public class FormData {
    String city;
    LocalDate date;
    String fullName;
    String phone;
}
