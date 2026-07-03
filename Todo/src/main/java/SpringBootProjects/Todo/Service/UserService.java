package SpringBootProjects.Todo.Service;



import SpringBootProjects.Todo.Models.User;
import SpringBootProjects.Todo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository ;

    public void createUser(User user){
        userRepository.save(user);
    }

    public User getUserById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Found"));
    }
}
