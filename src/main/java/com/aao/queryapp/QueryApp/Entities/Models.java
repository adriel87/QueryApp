package com.aao.queryapp.QueryApp.Entities;

import java.util.Collection;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Models {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 50, nullable = false, unique = true)
    private String name;

    private String modelJoinColum;

    @Column(length = 15, nullable = true, unique = true)
    private String alias;

    @ManyToOne
    private Models joinModel;

    @OneToMany(mappedBy = "joinModel")
    // @JoinColumn(nullable = true, name = "join_model_id")
    private Collection<Models> children;

    @Column(nullable = false)
    private String targetJoinColumn;
}
