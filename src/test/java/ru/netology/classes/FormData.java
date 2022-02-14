package ru.netology.classes;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class FormData {
    private String city;
    private LocalDate date;
    private String fullName;
    private String phone;
    private Boolean checkbox;

}
