package com.aao.queryapp.QueryApp.dto.request;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModelRequestDTO {
    private String name;
    private String modelNameJoinColum;
    private String alias;
    private String targetJoinColumn;
    private Optional<Integer> optionalModelId;
}
