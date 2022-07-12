package br.ufrn.lii.genericapi.repository;

import br.ufrn.lii.genericapi.model.Greeting;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface GreetingRepository extends CrudRepository<Greeting,Long>, JpaSpecificationExecutor<Greeting> {

}
