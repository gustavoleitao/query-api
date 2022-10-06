package io.github.gustavoleitao.queryapi.test;

import io.github.gustavoleitao.queryapi.DateUtil;
import io.github.gustavoleitao.queryapi.QuerySpecification;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GrettingRepositoryTest {

    @Autowired
    private GreetingRepository repository;

    public GrettingRepositoryTest() {
    }

    @Test
    public void contextLoads() {
        assertThat(repository).isNotNull();
    }

    public List<Greeting> mockData() {

        var data1 = Greeting
                .greetingBuilder().content("Simple text").iValue(9).dValue(0.33).state(Greeting.State.ON)
                .noIdea("noIdea")
                .bValue(false).bNonPrimitiveValue(false).dateValue(DateUtil.parseDate("2022-07-11T00:00:00Z")).build();

        var data2 = Greeting
                .greetingBuilder().content("Text simple").iValue(10).dValue(0.66).state(Greeting.State.OFF)
                .bValue(true).bNonPrimitiveValue(true).dateValue(DateUtil.parseDate("2022-07-12T00:00:00Z"))
                .noIdea("noIdea")
                .other(SomeOther.builder().text("random").build())
                .build();

        var data3 = Greeting
                .greetingBuilder().content("Some different").iValue(11).dValue(0.99).state(Greeting.State.ON)
                .bValue(false).bNonPrimitiveValue(false).dateValue(DateUtil.parseDate("2022-07-13T00:00:00Z"))
                .noIdea("lii")
                .build();

        return Arrays.asList(data1, data2, data3);
    }

    @Test
    public void noFilter() {
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
                .isBValue());
    }

    @Test
    public void eqNonPrimitiveBooleanFilter(){
        var paramters = ParamterBuild.instance()
                .eq("bNonPrimitiveValue", "true")
                .build();
        List<Greeting> result = repository.findAll(new QuerySpecification<>(paramters));
        Assert.assertEquals(1, result.size());
        Assert.assertTrue(result.stream().findAny().orElseThrow(() -> new RuntimeException("Registro não encontrado"))
                .getBNonPrimitiveValue().booleanValue());
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

    @Test
    public void eqOne2OneFilter(){
        var paramters = ParamterBuild.instance()
                .eq("other.text", "random")
                .build();
        List<Greeting> result = repository.findAll(new QuerySpecification<>(paramters));
        Assert.assertEquals(1, result.size());
        Assert.assertTrue(result.stream().findAny().orElseThrow(() -> new RuntimeException("Registro não encontrado"))
                .getOther().getText().equals("random"));
    }

    @Test
    public void likeOne2OneFilter(){
        var paramters = ParamterBuild.instance()
                .like("other.text", "rand%")
                .build();
        List<Greeting> result = repository.findAll(new QuerySpecification<>(paramters));
        Assert.assertEquals(1, result.size());
        Assert.assertTrue(result.stream().findAny().orElseThrow(() -> new RuntimeException("Registro não encontrado"))
                .getOther().getText().equals("random"));
    }

    @Test
    public void filterInBaseClass(){
        var paramters = ParamterBuild.instance()
                .eq("noIdea", "lii")
                .build();
        List<Greeting> result = repository.findAll(new QuerySpecification<>(paramters));
        Assert.assertEquals(1, result.size());
        Assert.assertTrue(result.stream().findAny().orElseThrow(() -> new RuntimeException("Registro não encontrado"))
                .getNoIdea().equals("lii"));
    }

    @BeforeAll
    public void setupTest() throws ParseException {
        repository.saveAll(mockData());
    }

}