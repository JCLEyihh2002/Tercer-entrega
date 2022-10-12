package com.wizeline.maven.learningjava.controller;

import feign.HeaderMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.wizeline.gradle.learningjava.model.ApiPublica;
import com.wizeline.gradle.learningjava.model.PaisesDTO;
import com.wizeline.gradle.learningjava.service.PaisService;

import java.util.List;

@RequestMapping("/paises")
@RestController
public class PaisesController {

    @Autowired
    private PaisService paisService;

    @PostMapping("/inserta")
    public Boolean insertaPais(@RequestParam String pais, @RequestParam String poblacion) {
        try {
            paisService.agregaPais(pais,poblacion);
            return  true;
        }catch (Exception e)
        {
            return false;
        }


    }
    @GetMapping("/obtiene")
    public List<PaisesDTO> obtienePaises() {
        return paisService.obtienePaises();
    }

    @PutMapping("/modifica")
    public Boolean modificaPais(@RequestBody PaisesDTO pais) {
        return paisService.actualizaPoblacion(pais);
    }

    @DeleteMapping("/borra")
    public PaisesDTO borraPais(@RequestBody PaisesDTO pais) {
        return  paisService.borraPais(pais);
    }

    @GetMapping("/api/publica")
    public ApiPublica consultaApi(){
        return paisService.consultaApiPublica();
    }

}
