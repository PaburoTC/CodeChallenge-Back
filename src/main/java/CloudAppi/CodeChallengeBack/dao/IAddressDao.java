package CloudAppi.CodeChallengeBack.dao;

import CloudAppi.CodeChallengeBack.model.Address;
import CloudAppi.CodeChallengeBack.model.User;

import java.util.List;
import java.util.Optional;

public interface IAddressDao {

    void createAddres(User user);

    Optional<Address> getAddresById(int id);

    void updateUserById(int id);

    void deleteAddresById(int id);
}
