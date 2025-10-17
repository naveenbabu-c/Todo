package com.firstSpring.First.Spring.Controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.firstSpring.First.Spring.Models.Todo;
import com.firstSpring.First.Spring.Service.TodoService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/todo")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;


    

    @PostMapping("/create")
    public ResponseEntity<?> createTodo(@RequestHeader("Authorization")String authHeader, @RequestBody Todo newTodo){

        String response = todoService.createTodo(authHeader,newTodo.getTodo());
        if(response.equals("Todo created successfully")){
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }   
        
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getTodos(@RequestHeader("Authorization")String authHeader) {
        return new ResponseEntity<>(todoService.getTodosByUserEmail(authHeader), HttpStatus.OK);
    }
    

}
