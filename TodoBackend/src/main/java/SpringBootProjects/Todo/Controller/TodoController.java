package SpringBootProjects.Todo.Controller;

import SpringBootProjects.Todo.Models.Todo;
import SpringBootProjects.Todo.Service.TodoService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/todo")
public class TodoController {
    @Autowired
    private TodoService todoService;
    @PostMapping("/create")
    public ResponseEntity<Todo> createTodo(@Valid @RequestBody Todo todo){
        return new ResponseEntity<>(todoService.createTodo(todo), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201" , description = "Todo Retrieved successfully"),
            @ApiResponse(responseCode = "404" , description = "Todo was Not found")
    })
    public ResponseEntity<Todo> getGetTodoById(@PathVariable Long id){
        try {
            Todo getTodo = todoService.getTodoById(id) ;
            return new ResponseEntity<>(getTodo , HttpStatus.OK);
        } catch (RuntimeException error){
            log.info("Error");
            log.warn("");
            log.error("error found" , error);
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<Todo>> getAllTodos(){
        return new ResponseEntity<>(todoService.allTodos() ,HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<Page<Todo>> getAllTodosPages(@RequestParam int page , @RequestParam int size){
        return new ResponseEntity<>(todoService.getAllTodosPages(page , size) , HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Todo> updateTodoById(@RequestBody Todo todo){
        return new ResponseEntity<>(todoService.updateTodo(todo) , HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteTodoById(@PathVariable long id){
        todoService.deleteTodoById(id);
    }

    @DeleteMapping("/delete")
    public void deleteTodos(Todo todo){
        todoService.deleteTodos(todo);
    }

}
