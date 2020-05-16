package CloudAppi.CodeChallengeBack.dao;

import CloudAppi.CodeChallengeBack.model.User;

import java.util.List;
import java.util.Optional;

public interface IUsersDao {

    List<User> getUsers();

    void createUser(User user);

    Optional<User> getUserById(int id);

    void updateUserById(int id, User user);

    void deleteUserById(int id);


}
