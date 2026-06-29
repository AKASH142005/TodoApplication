package SpringBootProjects.Todo;

import SpringBootProjects.Todo.Models.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todo/api")
public class TodoController {
    @Autowired
    private TodoService todoService;
    @PostMapping("/create")
    public ResponseEntity<Todo> createUser(@RequestBody Todo todo){
        return new ResponseEntity<>(todoService.createTodo(todo), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Todo> getUser(@PathVariable Long id){
        try {
            Todo getTodo = todoService.getTodoById(id) ;
            return new ResponseEntity<>(getTodo , HttpStatus.OK);
        } catch (RuntimeException error){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<Todo>> getAllTodos(){
        return new ResponseEntity<>(todoService.allTodos() ,HttpStatus.OK);
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
