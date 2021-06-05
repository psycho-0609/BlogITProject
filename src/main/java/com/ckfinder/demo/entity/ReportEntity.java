package com.ckfinder.demo.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "report")
@Data
public class ReportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
}
