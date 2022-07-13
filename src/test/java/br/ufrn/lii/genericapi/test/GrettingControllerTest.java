package br.ufrn.lii.genericapi.test;

import br.ufrn.lii.genericapi.QuerySpecification;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GrettingControllerTest {

    @Autowired
    private GreetingRepository repository;

    @Test
    public void contextLoads() throws Exception {
        assertThat(repository).isNotNull();
    }

    public List<Greeting> mockData(){
        List<Greeting> mock = new ArrayList<>();
        mock.add(getMockGreeting("Simple text", 9, 0.33, Greeting.State.ON));
        mock.add(getMockGreeting("Text simple", 10, 0.66, Greeting.State.OFF));
        mock.add(getMockGreeting("Some different", 11, 0.99, Greeting.State.ON));
        return mock;
    }

    @Test
    public void noFilter(){
        List<Greeting> result = repository.findAll(new QuerySpecification<>(ParamterBuild.instance().build()));
        Assert.assertEquals(mockData().size(), result.size());
    }

    @Test
    public void likeFilter(){
        var paramters = ParamterBuild.instance()
                .like("content", "Simple%")
                .build();
        List<Greeting> result = repository.findAll(new QuerySpecification<>(paramters));
        Assert.assertTrue(result.stream().findAny().get().getContent().startsWith("Simple"));
    }

    @Test
    public void eqStringFilter(){
        var paramters = ParamterBuild.instance()
                .eq("content", "Simple text")
                .build();
        List<Greeting> result = repository.findAll(new QuerySpecification<>(paramters));
        Assert.assertEquals(1, result.size());
        Assert.assertTrue(result.stream().findAny().get().getContent().equals("Simple text"));
    }

    @Test
    public void eqIntFilter(){
        var paramters = ParamterBuild.instance()
                .eq("iValue", "10")
                .build();
        List<Greeting> result = repository.findAll(new QuerySpecification<>(paramters));
        Assert.assertEquals(1, result.size());
        Assert.assertTrue(result.stream().findAny().get().getIValue() == 10);
    }

    @Test
    public void gtIntFilter(){
        var paramters = ParamterBuild.instance()
                .gt("iValue", "10")
                .build();
        List<Greeting> result = repository.findAll(new QuerySpecification<>(paramters));
        Assert.assertEquals(1, result.size());
        Assert.assertTrue(result.stream().findAny().get().getIValue() > 10);
    }

    @Test
    public void ltIntFilter(){
        var paramters = ParamterBuild.instance()
                .lt("iValue", "10")
                .build();
        List<Greeting> result = repository.findAll(new QuerySpecification<>(paramters));
        Assert.assertEquals(1, result.size());
        Assert.assertTrue(result.stream().findAny().orElseThrow(() -> new RuntimeException("Registro não encontrado")).getIValue() < 10);
    }

    @Test
    public void geIntFilter(){
        var paramters = ParamterBuild.instance()
                .ge("iValue", "11")
                .build();
        List<Greeting> result = repository.findAll(new QuerySpecification<>(paramters));
        Assert.assertEquals(1, result.size());
        Assert.assertTrue(result.stream().findAny().orElseThrow(() -> new RuntimeException("Registro não encontrado")).getIValue() >= 11);
    }

    @Test
    public void leIntFilter(){
        var paramters = ParamterBuild.instance()
                .le("iValue", "9")
                .build();
        List<Greeting> result = repository.findAll(new QuerySpecification<>(paramters));
        Assert.assertEquals(1, result.size());
        Assert.assertTrue(result.stream().findAny().orElseThrow(() -> new RuntimeException("Registro não encontrado")).getIValue() <= 9);
    }

    @Test
    public void gtDoubleFilter(){
        var paramters = ParamterBuild.instance()
                .gt("dValue", "0.66")
                .build();
        List<Greeting> result = repository.findAll(new QuerySpecification<>(paramters));
        Assert.assertEquals(1, result.size());
        Assert.assertTrue(result.stream().findAny().orElseThrow(() -> new RuntimeException("Registro não encontrado")).getDValue() > 0.66);
    }

    @Test
    public void eqEnumFilter(){
        var paramters = ParamterBuild.instance()
                .eq("state", "OFF")
                .build();
        List<Greeting> result = repository.findAll(new QuerySpecification<>(paramters));
        Assert.assertEquals(1, result.size());
        Assert.assertTrue(result.stream().findAny().orElseThrow(() -> new RuntimeException("Registro não encontrado"))
                .getState().equals(Greeting.State.OFF));
    }

    @BeforeAll
    public void setupTest(){
        repository.saveAll(mockData());
    }

    private Greeting getMockGreeting(String content, int iValue, double dValue, Greeting.State state) {
        var greeting = new Greeting();
        greeting.setContent(content);
        greeting.setIValue(iValue);
        greeting.setDValue(dValue);
        greeting.setState(state);
        return greeting;
    }

}