package com.example.SpringZhanyl.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerModel {
    private Integer cId;

    private String name;

    private String lastName;

    private Integer age;

    private String city;
}
