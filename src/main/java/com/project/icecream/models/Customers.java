package com.project.icecream.models;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class Customers extends Users {
    public Customers (){}
    public Customers (Users user){
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.image = user.getImage();
        this.user_type = user.getUser_type();
        this.created_at = user.getCreated_at();
        this.updated_at = user.getUpdated_at();
    }
}