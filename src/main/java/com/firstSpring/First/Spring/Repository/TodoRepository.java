package com.firstSpring.First.Spring.Repository;

import com.firstSpring.First.Spring.Models.Todo;
import com.firstSpring.First.Spring.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

List<Todo> findByUser(User user);
}
