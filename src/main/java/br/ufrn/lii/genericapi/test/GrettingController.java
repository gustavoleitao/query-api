package br.ufrn.lii.genericapi.test;

import br.ufrn.lii.genericapi.QuerySpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.ls.LSOutput;

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
        System.out.println("Saving...");
        return repository.save(data);
    }

}
