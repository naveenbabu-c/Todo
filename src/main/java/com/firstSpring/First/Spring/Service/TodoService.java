package com.firstSpring.First.Spring.Service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.firstSpring.First.Spring.Configuration.JwtUtils;
import com.firstSpring.First.Spring.Models.Todo;
import com.firstSpring.First.Spring.Models.User;
import com.firstSpring.First.Spring.Repository.TodoRepository;
import com.firstSpring.First.Spring.Repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    // Response messages
    String created = "Todo created successfully";
    String emptyMessage = "Can not create empty Todo";
    String invalidUser = "User not found";

    public String createTodo(String authHeader, String todoDescription, String date) {
        String email = jwtUtils.extractEmailFromHeader(authHeader);
        User user = null;
        try {
            user = userRepository.findByEmail(email).get();
        } catch (Exception e) {
            return invalidUser;
        }
        if (userRepository.findByEmail(user.getEmail()).isEmpty()) {
            return invalidUser;
        }
        if (todoDescription.isEmpty()) {
            return emptyMessage;
        }
        Todo todo = new Todo();
        todo.setUser(user);
        todo.setTodo(todoDescription);
        todo.setCreated_date(new Date(System.currentTimeMillis()));
        todo.setTodo_date(date);
        todoRepository.save(todo);
        return created;
    }

    public List<Todo> getTodosByUserEmail(String authHeader) {
        String email = jwtUtils.extractEmailFromHeader(authHeader);
        User user = null;
        try {
            user = userRepository.findByEmail(email).get();
        } catch (Exception e) {
            return null;
        }
        List<Todo> listTodo = todoRepository.findByUser(user);
        return listTodo;
    }

    public Todo getTodo(String authHeader, int id) {
        List<Todo> listTodo = getTodosByUserEmail(authHeader);
        Todo todo = null;
        try {
            todo = listTodo.get(id);
        } catch (Exception e) {
            return null;
        }
        return todo;
    }

    public String changeStatus(String authHeader,int id, String entity) {
        if(!entity.equals("open") && !entity.equals("inprogress") && !entity.equals("completed")){
            return "Invalid Status";
        }
       Todo todo = getTodo(authHeader, id);
        todo.setStatus(entity);
        todoRepository.save(todo);
        return "Todo status updated successfully";
    }

    public String deleteTodo(String authHeader, int id) {
        Todo todo = getTodo(authHeader, id);
        todoRepository.delete(todo);
        return "Todo deleted successfully";
    }

    public String editTodo(String authHeader,int id, String todoDescription, String date){
         Todo todo = getTodo(authHeader, id);
         todo.setTodo_date(date);
         todo.setTodo(todoDescription);
         todoRepository.save(todo);
        return "Todo edited successfully";
    }

}
