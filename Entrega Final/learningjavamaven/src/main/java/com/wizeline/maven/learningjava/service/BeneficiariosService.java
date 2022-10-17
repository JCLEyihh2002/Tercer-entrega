package com.wizeline.maven.learningjava.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.wizeline.maven.learningjava.model.ApiPublica;
import com.wizeline.maven.learningjava.model.BeneficiariosDTO;

@Service
public class BeneficiariosService {
	
	 @Autowired
	    private MongoTemplate mongoTemplate;
	    private static final String ID ="_id";

	    public ApiPublica consultaApiPublica() {
	        RestTemplate restTemplate = new RestTemplate();
	        String url = "https://jsonplaceholder.typicode.com/todos/1";
	        return restTemplate.getForEntity(url, ApiPublica.class).getBody();
	    }

	    public Boolean agregaBeneficiarios(String beneficiarios, String edad) {

	        try {
	            BeneficiariosDTO beneficiariosNuevo = new BeneficiariosDTO(beneficiarios, edad);
	            mongoTemplate.save(beneficiariosNuevo);
	            return  true;
	        }catch (Exception e)
	        {
	            return false;
	        }

	    }

	    public List<BeneficiariosDTO> obtieneBeneficiarios() {
	        return mongoTemplate.findAll(BeneficiariosDTO.class);
	    }

	    public Boolean actualizaEdad(BeneficiariosDTO request) {
	        Query query = Query.query((CriteriaDefinition) Criteria.where(ID).is(request.getBeneficiarios()));
	        UpdateResult resultade = mongoTemplate.updateFirst(query, Update.update("edad",request.getEdad()),BeneficiariosDTO.class);
	        return resultade.wasAcknowledged();
	    }

	    public BeneficiariosDTO borraBeneficiarios(BeneficiariosDTO request) {
	        Query query = Query.query((CriteriaDefinition) Criteria.where(ID).is(request.getBeneficiarios()));

	        return mongoTemplate.findAndRemove(query,BeneficiariosDTO.class);
	    }

}
