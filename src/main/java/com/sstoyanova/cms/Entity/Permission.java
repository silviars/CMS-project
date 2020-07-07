package com.sstoyanova.cms.Entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "permissions")
public class Permission implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name", updatable = false, unique = true)
    private String name;

    public Permission(){}

    public Permission(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
}