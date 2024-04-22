package com.aao.queryapp.QueryApp.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// @Entity(name = "basic") we could change the entity name, by default is the name of the class
@Entity
// @Table(name = "basicoso", schema = "testdos") 
@Getter
@Setter
@NoArgsConstructor
public class Basic {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private String description;

    private int age;
}
