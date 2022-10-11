package io.github.gustavoleitao.queryapi.test;


import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
public class Greeting extends BaseClass {

    public Greeting() {
        super(UUID.fromString("00000000-0000-0000-0000-000000000002"),"noIdea");
    }

    @Builder (builderMethodName = "greetingBuilder")
    public Greeting(String noIdea, Long id, String content, int iValue, double dValue, State state, boolean bValue, Boolean bNonPrimitiveValue, SomeOther other, Date dateValue) {
        super(UUID.fromString("00000000-0000-0000-0000-000000000002"),"noIdea");
        this.id = id;
        this.content = content;
        this.iValue = iValue;
        this.dValue = dValue;
        this.state = state;
        this.bValue = bValue;
        this.bNonPrimitiveValue = bNonPrimitiveValue;
        this.other = other;
        this.dateValue = dateValue;
    }

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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "other_id", referencedColumnName = "id")
    private SomeOther other;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateValue;


}
