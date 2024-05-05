package com.aao.queryapp.QueryApp.Entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Entity
@Getter
@Setter
public class Filter {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String operator;

    @OneToMany(mappedBy = "filter")
    private Collection<ColumnType> columnTypes;

}
