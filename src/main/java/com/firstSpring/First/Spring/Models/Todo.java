package com.firstSpring.First.Spring.Models;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
public class Todo {

    @Id
    @GeneratedValue
    Long id;

    @NotBlank(message = "Todo cannot be empty")
    String todo;

    String status = "open";

    @DateTimeFormat(pattern = "dd-mm-yyyy")
    Date created_date;

    @DateTimeFormat(pattern = "dd-mm-yyyy")
    String todo_date;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"password"})
    private User user;

    
}
