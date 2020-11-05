package com.tech.paper.domain;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import java.io.Serializable;

@Deprecated
public class ArxivType implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    private Integer id;

    //physics,math,q-bio,cs,q-fin,stat,eess,econ
    @Column(nullable = false)
    private String typeShort;

    //Physics,Mathematics...
    @Column(nullable = false)
    private String typeName;


}
