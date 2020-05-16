package CloudAppi.CodeChallengeBack.service;

import CloudAppi.CodeChallengeBack.dao.IUsersDao;
import CloudAppi.CodeChallengeBack.dao.UsersDataAccessService;
import CloudAppi.CodeChallengeBack.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService {

    private final IUsersDao usersDao;

    @Autowired
    public UsersService(@Qualifier("postgres") IUsersDao usersDao){
        this.usersDao = usersDao;
    }

    public List<User> getUsers(){
        return usersDao.getUsers();
    }


    public void createUser(User user){
        usersDao.createUser(user);
    }

    public Optional<User> getUserById(int id){
        return usersDao.getUserById(id);
    }

    public void updateUserById(int id, User user){
        usersDao.updateUserById(id, user);
    }

    public void deleteUserById(int id){
        usersDao.deleteUserById(id);
    }
}
