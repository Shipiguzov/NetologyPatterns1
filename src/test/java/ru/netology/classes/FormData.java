package ru.netology.classes;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class FormData {
    String city;
    LocalDate date;
    String fullName;
    String phone;
    Boolean checkbox;

}
