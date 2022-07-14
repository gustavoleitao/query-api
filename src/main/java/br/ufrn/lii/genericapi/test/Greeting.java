package br.ufrn.lii.genericapi.test;

import javax.persistence.*;
import java.util.Date;

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

    private boolean bValue;

    private Boolean bNonPrimitiveValue;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateValue;

    public Greeting() {
    }

    public boolean isbValue() {
        return bValue;
    }

    public void setbValue(boolean bValue) {
        this.bValue = bValue;
    }

    public Boolean getbNonPrimitiveValue() {
        return bNonPrimitiveValue;
    }

    public void setbNonPrimitiveValue(Boolean bNonPrimitiveValue) {
        this.bNonPrimitiveValue = bNonPrimitiveValue;
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

    public Date getDateValue() {
        return dateValue;
    }

    public void setDateValue(Date dateValue) {
        this.dateValue = dateValue;
    }

    @Override
    public String toString() {
        return "Greeting{" +
                "id=" + id +
                ", content='" + content + '\'' +
                '}';
    }

}
