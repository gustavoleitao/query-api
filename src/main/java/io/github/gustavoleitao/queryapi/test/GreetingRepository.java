package io.github.gustavoleitao.queryapi.test;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GreetingRepository extends CrudRepository<Greeting,Long>, JpaSpecificationExecutor<Greeting> {
    
}
