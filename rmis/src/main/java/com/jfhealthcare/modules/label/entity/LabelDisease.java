package com.jfhealthcare.modules.label.entity;

import javax.persistence.*;

import lombok.Data;

@Table(name = "label_disease")
@Data
public class LabelDisease {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select uuid()")
    private String id;

    @Column(name = "UID")
    private String uid;

    @Column(name = "disease_code")
    private String diseaseCode;

    private String disease;

    @Column(name = "IDC10CODE")
    private String idc10code;
}