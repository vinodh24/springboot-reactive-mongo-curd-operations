package com.vinodh.reactive.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Person")
public class Person {
    @Id
    private String id;
    private String firstName;
    private String secondName;
    private Date dateOfBirth;
    private String profession;
    private Double salary;
}
