package com.vinodh.reactive.repository;

import org.springframework.data.domain.Range;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.vinodh.reactive.dto.PersonDto;
import com.vinodh.reactive.model.Person;

import reactor.core.publisher.Flux;

@Repository
public interface PersonRepository extends ReactiveMongoRepository<Person,String> {
    Flux<PersonDto> findBySalaryBetween(Range<Double> priceRange);
}
