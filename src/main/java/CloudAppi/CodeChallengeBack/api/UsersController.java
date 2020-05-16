package CloudAppi.CodeChallengeBack.api;

import CloudAppi.CodeChallengeBack.model.User;
import CloudAppi.CodeChallengeBack.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("users")
@RestController
public class UsersController {

    private final UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService){
        this.usersService = usersService;
    }

    @GetMapping(path ="getusers")
    public List<User> getUsers(){
        return usersService.getUsers();
    }

}