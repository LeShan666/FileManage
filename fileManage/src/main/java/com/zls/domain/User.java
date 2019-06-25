package com.zls.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "user_name")
    private String username;
    @Column(name = "user_pwd")
    private String userpass;
    @Column(name = "user_email")
    private String useremail;

}
