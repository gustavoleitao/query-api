package br.ufrn.lii.genericapi.test;

import br.ufrn.lii.genericapi.DateUtil;
import br.ufrn.lii.genericapi.QuerySpecification;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
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


    public List<Greeting> mockData() throws ParseException {
        List<Greeting> mock = new ArrayList<>();
        mock.add(getMockGreeting("Simple text", 9, 0.33, Greeting.State.ON, false, DateUtil.parseDate("2022-07-11T00:00:00Z")));
        mock.add(getMockGreeting("Text simple", 10, 0.66, Greeting.State.OFF, true, DateUtil.parseDate("2022-07-12T00:00:00Z")));
        mock.add(getMockGreeting("Some different", 11, 0.99, Greeting.State.ON, false, DateUtil.parseDate("2022-07-13T00:00:00Z")));
        return mock;
    }

    @Test
    public void noFilter() throws ParseException {
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

    @Test
    public void eqBooleanFilter(){
        var paramters = ParamterBuild.instance()
                .eq("bValue", "true")
                .build();
        List<Greeting> result = repository.findAll(new QuerySpecification<>(paramters));
        Assert.assertEquals(1, result.size());
        Assert.assertTrue(result.stream().findAny().orElseThrow(() -> new RuntimeException("Registro não encontrado"))
                .isbValue());
    }

    @Test
    public void eqNonPrimitiveBooleanFilter(){
        var paramters = ParamterBuild.instance()
                .eq("bNonPrimitiveValue", "true")
                .build();
        List<Greeting> result = repository.findAll(new QuerySpecification<>(paramters));
        Assert.assertEquals(1, result.size());
        Assert.assertTrue(result.stream().findAny().orElseThrow(() -> new RuntimeException("Registro não encontrado"))
                .getbNonPrimitiveValue().booleanValue());
    }

    @Test
    public void gtDateFilter() throws ParseException {
        var paramters = ParamterBuild.instance()
                .gt("dateValue", "2022-07-12T00:00:00Z")
                .build();
        List<Greeting> result = repository.findAll(new QuerySpecification<>(paramters));
        Assert.assertEquals(1, result.size());
        Assert.assertTrue(result.stream().findAny().get().getDateValue().after(DateUtil.parseDate("2022-07-12T00:00:00Z")));
    }

    @BeforeAll
    public void setupTest() throws ParseException {
        repository.saveAll(mockData());
    }

    private Greeting getMockGreeting(String content, int iValue, double dValue, Greeting.State state, boolean bValue, Date dateValue) {
        var greeting = new Greeting();
        greeting.setContent(content);
        greeting.setIValue(iValue);
        greeting.setDValue(dValue);
        greeting.setState(state);
        greeting.setbValue(bValue);
        greeting.setbNonPrimitiveValue(Boolean.valueOf(bValue));
        greeting.setDateValue(dateValue);
        return greeting;
    }

}