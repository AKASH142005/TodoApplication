package SpringBootProjects.Todo.Service;

import SpringBootProjects.Todo.Models.Todo;
import SpringBootProjects.Todo.Repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    public Page <Todo> getAllTodosPages(int page , int size){
        Pageable pageable = PageRequest.of(page , size);
        return todoRepository.findAll(pageable);
    }

    public Todo updateTodo(Todo todo ,long id ){
        if (todo.getId() == null || !todoRepository.existsById(todo.getId())) {
           throw new RuntimeException("Todo not found");
        }
        return todoRepository.save(todo);
    }

    public void deleteTodos(Todo todo){
        todoRepository.delete(todo);
    }

    public void deleteTodoById(Long id){
         todoRepository.delete(getTodoById(id));
    }
}
