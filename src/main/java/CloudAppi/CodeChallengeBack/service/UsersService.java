package CloudAppi.CodeChallengeBack.service;

import CloudAppi.CodeChallengeBack.dao.IUsersDao;
import CloudAppi.CodeChallengeBack.model.Address;
import CloudAppi.CodeChallengeBack.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@Service
public class UsersService {

    private final IUsersDao usersDao;
    private final Encrypt encrypt;
    private final Decrypt decrypt;

    @Autowired
    public UsersService(@Qualifier("postgres") IUsersDao usersDao) throws NoSuchPaddingException, UnsupportedEncodingException, NoSuchAlgorithmException {
        this.usersDao = usersDao;
        this.encrypt = new Encrypt();
        this.decrypt = new Decrypt();
    }

    public List<User> getUsers() throws InvalidKeyException, BadPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException {
        List<User> users = usersDao.getUsers();
        for(User user : users) decryptUser(user);
        return users;
    }

    public void createUser(User user) throws NoSuchPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        encryptUser(user);
        usersDao.createUser(user);
    }

    public Optional<User> getUserById(int id) throws InvalidKeyException, BadPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException {
        User user = usersDao.getUserById(id).orElse(null);
        if (user != null) decryptUser(user);
        return Optional.ofNullable(user);
    }

    public void updateUserById(int id, User user) throws NoSuchPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        encryptUser(user);
        usersDao.updateUserById(id, user);
    }

    public void deleteUserById(int id){
        usersDao.deleteUserById(id);
    }

    private void encryptUser(User user) throws NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        user.setName(encrypt.encrypt(user.getName()));
        user.setEmail(encrypt.encrypt(user.getEmail()));

        Address address = user.getAddress();
        address.setStreet(encrypt.encrypt(address.getStreet()));
        address.setState(encrypt.encrypt(address.getState()));
        address.setCity(encrypt.encrypt(address.getCity()));
        address.setCountry(encrypt.encrypt(address.getCountry()));
        address.setZip(encrypt.encrypt(address.getZip()));
    }

    private void decryptUser(User user) throws IllegalBlockSizeException, InvalidKeyException, InvalidAlgorithmParameterException, BadPaddingException {
        user.setName(decrypt.decrypt(user.getName()));
        user.setEmail(decrypt.decrypt(user.getEmail()));

        Address address = user.getAddress();
        address.setStreet(decrypt.decrypt(address.getStreet()));
        address.setState(decrypt.decrypt(address.getState()));
        address.setCity(decrypt.decrypt(address.getCity()));
        address.setCountry(decrypt.decrypt(address.getCountry()));
        address.setZip(decrypt.decrypt(address.getZip()));
    }
}
