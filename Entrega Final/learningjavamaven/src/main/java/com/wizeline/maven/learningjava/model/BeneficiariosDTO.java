package com.wizeline.maven.learningjava.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Beneficiarios")
public class BeneficiariosDTO {
	
	@Id
    private String beneficiarios;
	
    private String edad;

    public BeneficiariosDTO(String beneficiarios, String edad) {
        this.beneficiarios = beneficiarios;
        this.edad = edad;
    }

	public String getBeneficiarios() {
		return beneficiarios;
	}

	public void setBeneficiarios(String beneficiarios) {
		this.beneficiarios = beneficiarios;
	}

	public String getEdad() {
		return edad;
	}

	public void setEdad(String edad) {
		this.edad = edad;
	}
}
