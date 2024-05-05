package com.aao.queryapp.QueryApp.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Entity
@Getter
@Setter
public class ColumnType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String aliases;

    private String description;

    @OneToMany(mappedBy = "columnType")
    private Collection<ColumnName> columns;

    @ManyToOne
    @JoinColumn(name = "filter_id")
    private Filter filter;
}
