package com.wizeline.maven.learningjava.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wizeline.maven.learningjava.model.ApiPublica;
import com.wizeline.maven.learningjava.model.BeneficiariosDTO;
import com.wizeline.maven.learningjava.service.BeneficiariosService;


@RestController
@RequestMapping("/beneficiarios")
@Validated
public class BeneficiariosController {
	
	@Autowired
	private BeneficiariosService beneficiariosService;

	
	@PostMapping("/beneficiarios")
	public Boolean beneficiario(@RequestParam String beneficiarios, @RequestParam String edad){

        try {
        	beneficiariosService.agregaBeneficiarios(beneficiarios, edad);
            return  true;
        }catch (Exception e)
        {
            return false;
        }

    }
	 @GetMapping("/obtiene")
	    public List<BeneficiariosDTO> obtieneBeneficiarios() {
	        return beneficiariosService.obtieneBeneficiarios();
	    }

	    @PutMapping("/modifica")
	    public Boolean modificaBeneficiarios(@RequestBody BeneficiariosDTO beneficiarios) {
	        return beneficiariosService.actualizaEdad(beneficiarios);
	    }

	    @DeleteMapping("/borra")
	    public BeneficiariosDTO borraBeneficiarios(@RequestBody BeneficiariosDTO beneficiarios) {
	        return beneficiariosService.borraBeneficiarios(beneficiarios);
	    }

	    @GetMapping("/api/publica")
	    public ApiPublica consultaApi(){
	        return beneficiariosService.consultaApiPublica();
	    }
}
