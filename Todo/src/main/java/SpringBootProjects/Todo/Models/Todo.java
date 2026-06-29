package SpringBootProjects.Todo.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;


@Entity
@Data
public class Todo {
    @Id
    @GeneratedValue
    private Long id ;
    String title ;
    String description ;
    boolean isCompleted ;
}
