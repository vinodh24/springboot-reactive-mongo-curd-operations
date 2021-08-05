package com.vinodh.reactive.utils;

import org.springframework.beans.BeanUtils;

import com.vinodh.reactive.dto.PersonDto;
import com.vinodh.reactive.model.Person;

public class AppUtils {

    public static PersonDto entityToDto(Person person) {
    	PersonDto personDto = new PersonDto();
        BeanUtils.copyProperties(person, personDto);
        return personDto;
    }

    public static Person dtoToEntity(PersonDto personDto) {
        Person person = new Person();
        BeanUtils.copyProperties(personDto, person);
        return person;
    }
}
