package com.vinodh.reactive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinodh.reactive.dto.PersonDto;
import com.vinodh.reactive.service.PersonService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping
    public Flux<PersonDto> getProducts(){
        return personService.getPersons();
    }

    @GetMapping("/{id}")
    public Mono<PersonDto> getPerson(@PathVariable String id){
        return personService.getPerson(id);
    }

    @GetMapping("/salary-range")
    public Flux<PersonDto> getSalaryBetweenRange(@PathVariable("min") Double min, @PathVariable("max")Double max){
        return personService.getSalaryBetweenRange(min,max);
    }

    @PostMapping
    public Mono<PersonDto> savePerson(@RequestBody Mono<PersonDto> personDtoMono){
    	 PersonDto personDto= personDtoMono.block();
    	System.out.println("savePerson method called ..."+personDto);
        return personService.savePerson(personDtoMono);
    }

    @PutMapping("/update/{id}")
    public Mono<PersonDto> updatePerson(@RequestBody Mono<PersonDto> personDtoMono,@PathVariable String id){
        return personService.updatePerson(personDtoMono,id);
    }

    @DeleteMapping("/delete/{id}")
    public Mono<Void> deletePerson(@PathVariable String id){
        return personService.deletePerson(id);
    }



}
