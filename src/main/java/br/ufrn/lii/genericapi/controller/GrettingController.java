package br.ufrn.lii.genericapi.controller;

import br.ufrn.lii.genericapi.model.Greeting;
import br.ufrn.lii.genericapi.repository.GreetingRepository;
import br.ufrn.lii.genericapi.repository.GenericSpecification;
import br.ufrn.lii.genericapi.repository.QuerySpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class GrettingController {

    @Autowired
    private GreetingRepository repository;

    @GetMapping("/greeting")
    public Page<Greeting> greeting(@RequestParam  Map<String,String> paramters, Pageable pageable){
        return repository.findAll(new QuerySpecification(paramters), pageable);
    }

    @PostMapping("/greeting")
    public Greeting save(@RequestBody Greeting data){
        return repository.save(data);
    }

}
