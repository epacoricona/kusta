package micro.desafio.kusta.infrastructure.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.*;

import micro.desafio.kusta.domain.entity.Person;
import micro.desafio.kusta.infrastructure.handler.AnimalHandler;
import micro.desafio.kusta.infrastructure.handler.DecisionEntityHandler;
import micro.desafio.kusta.infrastructure.handler.PersonHandler;
import org.springframework.http.MediaType;

@Configuration
public class RouterFunctionConfig {
	
    @Bean
    public RouterFunction<ServerResponse> routes(PersonHandler personHandler, AnimalHandler animalHandler, DecisionEntityHandler decisionEntityHandler){
        return RouterFunctions.route(RequestPredicates.GET("/api/person"),personHandler::findAll)
                .andRoute(RequestPredicates.GET("/api/person/{id}"),personHandler::findById)
                .andRoute(RequestPredicates.POST("/api/person"),personHandler::save)
        		.andRoute(RequestPredicates.PUT("/api/person"),personHandler::update)
        		.andRoute(RequestPredicates.DELETE("/api/person/{id}"),personHandler::delete)

        		.andRoute(RequestPredicates.GET("/api/animal"),animalHandler::findAll)
        		.andRoute(RequestPredicates.GET("/api/animal/{id}"),animalHandler::findById)
                .andRoute(RequestPredicates.POST("/api/animal"),animalHandler::save)
        		.andRoute(RequestPredicates.PUT("/api/animal"),animalHandler::update)
        		.andRoute(RequestPredicates.DELETE("/api/animal/{id}"),animalHandler::delete)
        		
        		.andRoute(RequestPredicates.GET("/api/decision/{source}"),decisionEntityHandler::findDecisionEntity)

        		
        		;
	}
}
