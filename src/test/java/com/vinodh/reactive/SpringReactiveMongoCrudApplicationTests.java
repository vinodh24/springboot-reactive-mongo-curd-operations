package com.vinodh.reactive;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import  static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.vinodh.reactive.controller.PersonController;
import com.vinodh.reactive.dto.PersonDto;
import com.vinodh.reactive.service.PersonService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@WebFluxTest(PersonController.class)
class SpringReactiveMongoCrudApplicationTests {
   
	@Autowired
    private WebTestClient webTestClient;
   
	@MockBean
    private PersonService personService;

    @Test
    public void addPersonTest(){
		Mono<PersonDto> personDtoMono=Mono.just(new PersonDto("101","Vinodh","Sangavaram",new Date(),"software",100000D));
		when(personService.savePerson(personDtoMono)).thenReturn(personDtoMono);

		webTestClient.post().uri("/person")
				.body(Mono.just(personDtoMono),PersonDto.class)
				.exchange()
				.expectStatus().isOk();//200

	}


	@Test
	public void getProductsTest(){
		Flux<PersonDto> personDtoFlux=Flux.just(new PersonDto("101","Vinodh","Sangavaram",new Date(),"software",100000D),
				new PersonDto("101","Amma","Sangavaram",new Date(),"software",100000D));
		when(personService.getPersons()).thenReturn(personDtoFlux);

		Flux<PersonDto> responseBody = webTestClient.get().uri("/person")
				.exchange()
				.expectStatus().isOk()
				.returnResult(PersonDto.class)
				.getResponseBody();

		StepVerifier.create(responseBody)
				.expectSubscription()
				.expectNext(new PersonDto("101","Vinodh","Sangavaram",new Date(),"software",100000D))
				.expectNext(new PersonDto("101","Amma","Sangavaram",new Date(),"software",100000D))
				.verifyComplete();

	}


	@Test
	public void getPersonTest(){
		Mono<PersonDto> personDtoMono=Mono.just(new PersonDto("101","Vinodh","Sangavaram",new Date(),"software",100000D));
		when(personService.getPerson(any())).thenReturn(personDtoMono);

		Flux<PersonDto> responseBody = webTestClient.get().uri("/person/101")
				.exchange()
				.expectStatus().isOk()
				.returnResult(PersonDto.class)
				.getResponseBody();

		StepVerifier.create(responseBody)
				.expectSubscription()
				.expectNextMatches(p->p.getFirstName().equals("vinodh"))
				.verifyComplete();
	}

	@Test
	public void updatePersonTest(){
		Mono<PersonDto> productDtoMono=Mono.just(new PersonDto("101","Vinodh","Sangavaram",new Date(),"software",100000D));
		when(personService.updatePerson(productDtoMono,"101")).thenReturn(productDtoMono);

		webTestClient.put().uri("/person/update/101")
				.body(Mono.just(productDtoMono),PersonDto.class)
				.exchange()
				.expectStatus().isOk();//200
	}

	@Test
	public void deletePersonTest(){
    	given(personService.deletePerson(any())).willReturn(Mono.empty());
		webTestClient.delete().uri("/person/delete/101")
				.exchange()
				.expectStatus().isOk();//200
	}

}
