package io.github.gustavoleitao.queryapi.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class BaseClass implements Serializable {

    @Column(columnDefinition = "uuid")
    protected UUID uuidParam;
    protected String noIdea;

}
