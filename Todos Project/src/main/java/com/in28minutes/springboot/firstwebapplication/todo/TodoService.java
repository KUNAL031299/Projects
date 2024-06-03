package com.in28minutes.springboot.firstwebapplication.todo;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Service
public class TodoService {
    private static List<Todo> todos=new ArrayList<Todo>();

    private static int todoCount = 0;

    static {
        todos.add(new Todo(++todoCount, "in28minutes", "Learn Spring Boot 1", LocalDate.now().plusYears(1), false));
        todos.add(new Todo(++todoCount, "in28minutes", "Learn to DevOps 1", LocalDate.now().plusYears(2) , false));
        todos.add(new Todo(++todoCount, "in28minutes", "Learn to Full Stack 1", LocalDate.now().plusYears(3) , false));

    }

    public List<Todo> findByUsername(String username) {
        Predicate<? super Todo> predicate = todo -> todo.getUsername().equalsIgnoreCase(username);
        return  todos.stream().filter(predicate).toList();
    }

    public void addNewTodo(String username, String description, LocalDate targetDate, boolean isDone) {
        Todo todo =new Todo(++todoCount,username,description,targetDate,isDone);
        todos.add(todo);
    }

    public void deleteById(int id) {
        Predicate<? super Todo> predicate = todo -> todo.getId() == id;
        todos.removeIf(predicate);
    }

    public Todo findById(int id) {
        Predicate<? super Todo> predicate = todo -> todo.getId() == id;
         return todos.stream().filter(predicate).findFirst().get();
    }

    public void updateTodo(Todo todo) {
        deleteById(todo.getId());
        todos.add(todo);
    }

}
