package com.inn.cafe.com.POJO;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
@NamedQuery(name="User.findByEmail", query="SELECT u FROM User u WHERE u.email =:email")
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="user")

public class User implements Serializable{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(nullable = false,name="name")
    private String name;

    @Column(nullable = false,name="contact_number")
    private String contactNumber;

    @Column(nullable = false,name="email")
    private String email;

    @Column(nullable = false,name="password")
    private String password;

    @Column(name="status")
    private String status;

    @Column(name="role")
    private String role;

}
