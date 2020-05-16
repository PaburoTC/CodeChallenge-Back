package CloudAppi.CodeChallengeBack.api;

import CloudAppi.CodeChallengeBack.model.User;
import CloudAppi.CodeChallengeBack.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
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
    public List<User> getUsers() throws IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException {
        return usersService.getUsers();
    }

    @PostMapping(path = "createUser")
    public void createUser(@RequestBody User user) throws NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        usersService.createUser(user);
    }

    @GetMapping(path ="getuserById/{id}")
    public User getUserById(@PathVariable("id") int id) throws IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException {
        return usersService.getUserById(id).orElse(null);
    }

    @PutMapping(path = "updateUsersById/{id}")
    public void updateUserById(@PathVariable("id") int id, @RequestBody User user) throws NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        usersService.updateUserById(id,user);
    }

    @DeleteMapping(path = "deleteUsersById/{id}")
    public void deleteUserById(@PathVariable("id") int id){
        usersService.deleteUserById(id);
    }

}
