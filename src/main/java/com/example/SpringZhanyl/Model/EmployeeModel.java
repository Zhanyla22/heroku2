package com.example.SpringZhanyl.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmployeeModel {
    private Integer empId;
    private String name;
    private String lastName;
    private int salary;
    private int workExperienceYear;
    private String department;
}
