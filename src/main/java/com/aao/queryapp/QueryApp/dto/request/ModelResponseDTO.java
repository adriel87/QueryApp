package com.aao.queryapp.QueryApp.dto.request;

import java.util.*;

import com.aao.queryapp.QueryApp.Entities.ColumnName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModelResponseDTO {
    private String id;
    private String name;
    private String modelJoinColumn;
    private Set<ColumnName> columns;
    private String alias;
    private String modelsID;
    private Set<Long> childrensID;
    
}
