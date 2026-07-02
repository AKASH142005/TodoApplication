package SpringBootProjects.Todo.Controller;

import SpringBootProjects.Todo.Models.User;
import SpringBootProjects.Todo.Repository.UserRepository;
import SpringBootProjects.Todo.Service.UserService;
import SpringBootProjects.Todo.Utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    @PostMapping("/Register")
    public ResponseEntity<String> registerUser(@RequestBody Map<String , String> body){
        String Email = body.get("Email");
        String password = body.get("password");

        if(userRepository.findByEmail(Email).isPresent()){
            return new ResponseEntity<>("User Already Exist" , HttpStatus.CONFLICT);
        }

        userService.createUser(User.builder().Email(Email).password(password).build());
        return new ResponseEntity<>("User successfully creates" , HttpStatus.CREATED) ;
    }
    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@RequestBody Map<String , String> body){
        String Email = body.get("Email");
        String password = body.get("password");

        var userOptional = userRepository.findByEmail(Email);

        if(userOptional.isEmpty()){
            return new ResponseEntity<>("User Not Register",HttpStatus.NOT_FOUND);
        }
        User user = userOptional.get();
        if(! passwordEncoder.matches(password,user.getPassword())){
            return new ResponseEntity<>("Invalid password" , HttpStatus.UNAUTHORIZED);
        }

        String token = jwtUtil.generateToken(Email);

        return ResponseEntity.ok(Map.of("token" , token));
    }


}
