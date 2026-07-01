package SpringBootProjects.Todo.Models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;

import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
// @Getter @ Setter
public class Todo {
    @Id
    @GeneratedValue
    private Long id ;
    @NotBlank
    @Schema(name = "title" , example = "Complete Spring Boot")
    String title ;
    @NotBlank
    String description ;
    boolean isCompleted ;

    // @Pattern(regexp = "^[0-9]{10}$")
    // String PhoneNumber ;
    // @Email
    // String email ;
}
