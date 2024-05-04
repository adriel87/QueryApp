package com.aao.queryapp.QueryApp.Entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Models {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 50, nullable = false, unique = true)
    private String name;

    private String modelJoinColum;
    
    @OneToMany(mappedBy = "models", fetch = FetchType.EAGER)
    private Set<ColumnName> columns;
    
    @Column(length = 15, unique = true)
    private String alias;

    @ManyToOne
    @JoinColumn(name = "models_id")
    private Models models;

    @OneToMany(mappedBy = "models",fetch = FetchType.LAZY)
    private Set<Models> children;


    @Column(nullable = false)
    private String targetJoinColumn;
}
