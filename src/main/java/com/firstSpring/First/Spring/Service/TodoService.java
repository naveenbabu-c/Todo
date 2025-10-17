package com.firstSpring.First.Spring.Service;

import com.firstSpring.First.Spring.Configuration.JwtUtils;
import com.firstSpring.First.Spring.Models.Todo;
import com.firstSpring.First.Spring.Models.User;
import com.firstSpring.First.Spring.Repository.TodoRepository;
import com.firstSpring.First.Spring.Repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;

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

    public String createTodo(String authHeader, String todoDescription) {
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

}
