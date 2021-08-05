package com.vinodh.reactive.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;

import com.vinodh.reactive.dto.PersonDto;
import com.vinodh.reactive.repository.PersonRepository;
import com.vinodh.reactive.utils.AppUtils;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;


    public Flux<PersonDto> getPersons(){
        return personRepository.findAll().map(AppUtils::entityToDto);
    }

    public Mono<PersonDto> getPerson(String id){
        return personRepository.findById(id).map(AppUtils::entityToDto);
    }

    public Flux<PersonDto> getSalaryBetweenRange(double min,double max){
        return personRepository.findBySalaryBetween(Range.closed(min,max));
    }

    public Mono<PersonDto> savePerson(Mono<PersonDto> productDtoMono){
        System.out.println("service method called ...");
      return  productDtoMono.map(AppUtils::dtoToEntity)
                .flatMap(personRepository::insert)
                .map(AppUtils::entityToDto);
    }

    public Mono<PersonDto> updatePerson(Mono<PersonDto> personDtoMono,String id){
       return personRepository.findById(id)
                .flatMap(p->personDtoMono.map(AppUtils::dtoToEntity)
                .doOnNext(e->e.setId(id)))
                .flatMap(personRepository::save)
                .map(AppUtils::entityToDto);

    }

    public Mono<Void> deletePerson(String id){
        return personRepository.deleteById(id);
    }
}
