package com.in28minutes.springboot.firstwebapplication.todo;

import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@SessionAttributes("name")
public class TodoControllerJpa {
//    private TodoService todoService;

    private  TodoRepository todoRepository;

    public TodoControllerJpa( TodoRepository todoRepository) {
//        this.todoService = todoService;
        this.todoRepository = todoRepository;
    }

    @RequestMapping("/list-todos")
    public String listTodos(ModelMap model) {
        String username = getLoggedInUsername(model);

        List<Todo> todos= todoRepository.findByUsername(username);
        model.addAttribute("todos", todos);
        return "listTodos";
    }

    private static String getLoggedInUsername(ModelMap model) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        return  authentication.getName();
    }

    @RequestMapping(value="/add-todo", method = RequestMethod.GET)
    public String showNewTodoPage(ModelMap model) {

        String name = getLoggedInUsername(model);
        Todo todo = new Todo(0, name, "", LocalDate.now().plusYears(1), false);
        model.put("todo", todo);
        return "todo";
    }

    @RequestMapping(value="/add-todo", method = RequestMethod.POST)
    public String addNewTodo(ModelMap model, @Valid Todo todo, BindingResult result) {
        if (result.hasErrors()) {
            return "todo";
        }
        String name = getLoggedInUsername(model);
        todo.setUsername(name);
        todoRepository.save(todo);
//        todoService.addNewTodo(name, todo.getDescription(),todo.getTargetDate(), todo.isDone());
        return "redirect:list-todos";

    }

    @RequestMapping("/delete-todo")
    public String deleteTodo(@RequestParam int id) {
        todoRepository.deleteById(id);
        return "redirect:list-todos";
    }

    @RequestMapping(value="/update-todo", method = RequestMethod.GET)
    public String  showUpdateTodopage(@RequestParam int id, ModelMap model) {
        Todo todo = todoRepository.findById(id).get();
       model.addAttribute("todo", todo);
        return "todo";
    }

    @RequestMapping(value="/update-todo", method = RequestMethod.POST)
    public String updateTodo(ModelMap model, @Valid Todo todo, BindingResult result) {
        if (result.hasErrors()) {
            return "todo";
        }
        String name = getLoggedInUsername(model);
        todo.setUsername(name);
        todoRepository.save(todo);
        return "redirect:list-todos";

    }






}
