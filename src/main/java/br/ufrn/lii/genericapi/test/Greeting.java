package br.ufrn.lii.genericapi.test;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Greeting {

    public enum State {
        ON,OFF
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String content;

    private int iValue;

    private double dValue;

    private State state;

    public Greeting() {
    }

    public double getDValue() {
        return dValue;
    }

    public void setDValue(double dvalue) {
        this.dValue = dvalue;
    }

    public Long getId() {
        return id;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setIValue(int quantity) {
        this.iValue = quantity;
    }

    public int getIValue() {
        return iValue;
    }

    @Override
    public String toString() {
        return "Greeting{" +
                "id=" + id +
                ", content='" + content + '\'' +
                '}';
    }

}
