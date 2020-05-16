package CloudAppi.CodeChallengeBack.dao;

import CloudAppi.CodeChallengeBack.model.Address;
import CloudAppi.CodeChallengeBack.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository("postgres")
public class UsersDataAccessService implements IUsersDao{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UsersDataAccessService (JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> getUsers() {
        final String sql_user = "SELECT * FROM users";
        return jdbcTemplate.query(sql_user,((resultSet, i) -> {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String email = resultSet.getString("email");
            Date birthdate = resultSet.getDate("birthdate");
            int address_id = resultSet.getInt("address_id");
            Optional<Address> address = getAddresById(address_id);
            User user = new User(name,email,birthdate,address.orElse(null));
            user.setId(id);
            return user;
        }));
    }

    @Override
    public void createUser(User user) {

    }

    @Override
    public Optional<User> getUserById(int id) {
        final String sql = "SELECT * FROM users WHERE id = ?";
        User user = jdbcTemplate.queryForObject(sql, new Object[]{id}, ((resultSet, i) -> {
            String name = resultSet.getString("name");
            String email = resultSet.getString("email");
            Date birthdate = resultSet.getDate("birthdate");
            int address_id = resultSet.getInt("address_id");
            Optional<Address> address = getAddresById(address_id);
            User u = new User(name,email,birthdate,address.orElse(null));
            u.setId(id);
            return u;
        }));
        return Optional.ofNullable(user);
    }

    @Override
    public void updateUserById(int id) {

    }

    @Override
    public void deleteUserById(int id) {

    }

    private Optional<Address> getAddresById(int id){
        final String sql_address = "SELECT * FROM address WHERE id = ?";
        Address address = jdbcTemplate.queryForObject(sql_address, new Object[]{id}, ((resultSet, i) -> {
            String street = resultSet.getString("street");
            String state = resultSet.getString("state");
            String city = resultSet.getString("city");
            String country = resultSet.getString("country");
            String zip = resultSet.getString("zip");

            Address addrss = new Address(street,state,city,country,zip);
            addrss.setId(id);
            return  addrss;
        }));

        return Optional.ofNullable(address);
    }
}
