package com.vinodh.reactive.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonDto {

	private String id;
    private String firstName;
    private String secondName;
    private Date dateOfBirth;
    private String profession;
    private Double salary;
}
