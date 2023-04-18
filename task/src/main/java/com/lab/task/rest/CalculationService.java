package com.lab.task.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.lab.task.model.Calculation;

@Stateless
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/")
public class CalculationService{
    
    @PersistenceContext(unitName = "persistUnit")
    private EntityManager entityManager;

    
    
    @POST
    @Path("/calc")
    public int createCalculation(Calculation calculation){
        int answer = 0;

        switch (calculation.getOperation()){
            case "+": 
                answer = calculation.getNumber1() + calculation.getNumber2();
                break;
            case "-": 
                answer = calculation.getNumber1() - calculation.getNumber2();
                break;
            case "*": 
                answer = calculation.getNumber1() * calculation.getNumber2();
                break;
            case "/": 
                answer = calculation.getNumber1() / calculation.getNumber2();
                break;
            default: 
                throw new IllegalArgumentException("Invalid operation input");
        }

        entityManager.persist(calculation);
        return answer;
    }

    @GET
    @Path("calculations")
    public List<Calculation> getAllCalculations(){
        TypedQuery<Calculation> query = entityManager
        .createQuery("select c from Calculation c", Calculation.class);
        return query.getResultList();
    }
    


}