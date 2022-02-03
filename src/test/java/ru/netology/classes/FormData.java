package ru.netology.classes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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
