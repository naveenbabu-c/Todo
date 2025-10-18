package com.firstSpring.First.Spring.Controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.firstSpring.First.Spring.Models.Todo;
import com.firstSpring.First.Spring.Service.TodoService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;



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
    public ResponseEntity<?> getAllTodos(@RequestHeader("Authorization")String authHeader) {
        return new ResponseEntity<>(todoService.getTodosByUserEmail(authHeader), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getTodo(@RequestHeader("Authorization")String authHeader,@PathVariable int id) {
        return new ResponseEntity<>(todoService.getTodo(authHeader,id), HttpStatus.OK);
    }

    @PutMapping("/get/{id}/update")
    public ResponseEntity<?> changeStatus(@RequestHeader("Authorization")String authHeader,@PathVariable int id, @RequestBody String entity) {
        
        return new ResponseEntity<>(todoService.changeStatus(authHeader,id,entity), HttpStatus.OK);
    }
    
}
