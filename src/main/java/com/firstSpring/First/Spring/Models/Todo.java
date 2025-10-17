package com.firstSpring.First.Spring.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Entity
@Data
public class Todo {

    @Id
    @GeneratedValue
    Long id;

    @NotBlank(message = "Todo cannot be empty")
    String todo;

    @Pattern(regexp = "^(open|inprogress|completed)$", message = "Invalid Status")
    String status = "open";

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnoreProperties({ "password" })
    private User user;

}
