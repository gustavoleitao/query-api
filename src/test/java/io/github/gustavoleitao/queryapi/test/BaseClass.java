package io.github.gustavoleitao.queryapi.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class BaseClass implements Serializable {

    protected String noIdea;

}
