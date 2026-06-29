package SpringBootProjects.Todo;


import org.springframework.data.jpa.repository.JpaRepository;
import SpringBootProjects.Todo.Models.Todo;
public interface TodoRepository extends JpaRepository<Todo,Long> {
}
