package com.aao.queryapp.QueryApp.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddColumnRequestDTO {
    private String name;
    private String type;
    private int modelId;
}
