package SpringBootProjects.Todo;

import SpringBootProjects.Todo.Models.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class TodoService {
    @Autowired
    private TodoRepository todoRepository;
    public Todo createTodo(Todo todo){
        return todoRepository.save(todo);
    }

    public Todo getTodoById(Long id){
        return todoRepository.findById(id).orElseThrow(()-> new RuntimeException("Not found"));
    }

    public List<Todo> allTodos(){
        return todoRepository.findAll();
    }

    public Todo updateTodo( Todo todo){
        return todoRepository.save(todo);
    }

    public void deleteTodos(Todo todo){
        todoRepository.delete(todo);
    }

    public void deleteTodoById(Long id){
         todoRepository.delete(getTodoById(id));
    }
}
