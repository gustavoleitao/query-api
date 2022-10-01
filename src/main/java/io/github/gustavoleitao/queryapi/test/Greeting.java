package io.github.gustavoleitao.queryapi.test;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Builder
@Data
@AllArgsConstructor
public class Greeting {

    public Greeting() {
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
