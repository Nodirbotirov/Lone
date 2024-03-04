package com.nod.lone.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class FileStorage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String extension;

    private Long fileSize;

    private String contentType;


}
