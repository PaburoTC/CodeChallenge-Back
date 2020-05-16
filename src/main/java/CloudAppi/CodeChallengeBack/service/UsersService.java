package CloudAppi.CodeChallengeBack.service;

import CloudAppi.CodeChallengeBack.dao.IUsersDao;
import CloudAppi.CodeChallengeBack.dao.UsersDataAccessService;
import CloudAppi.CodeChallengeBack.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
