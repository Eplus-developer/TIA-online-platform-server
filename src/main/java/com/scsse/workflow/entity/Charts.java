package com.scsse.workflow.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "charts")
public class Charts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",unique = true,nullable = false,length = 50)
    @JsonIgnore
    private Integer id;
    @Column(name = "name",nullable = false,length = 50)
    private String name;
    @Column(name = "num",nullable = false,length = 50)
    private String num;
}
