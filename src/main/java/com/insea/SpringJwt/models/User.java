package com.insea.SpringJwt.models;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.*;

@Entity
public class User  {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private  int id;
    @Column(unique=true)
    @NotBlank(message = "Username is empty")
    @NotNull(message = "Username is Null")
    private String username;
    @Column(unique=true)
    @NotBlank(message = "Email is empty")
    @Email(message = "email not valid")
    private String email;
    @NotBlank(message = "Password is empty")
    //@Size(min = 8,max = 30,message = "password must be between 8 and 30 character")
    private String password;
    @NotBlank(message = "first name is empty")
    private String firstName;
    @NotBlank(message = "Last Name is empty")
    private String lastName;

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;


    public User() {
        roles = new ArrayList<>();
    }
/*
    public User(int id,String username, String email, String password, String firstName, String lastName) {
        this.id=id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roles = roles;
    }*/


    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
