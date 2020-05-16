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
        System.out.println(user.getAddress());
        Address address = getAddres(user.getAddress()).orElse(null);

        int address_id = (address==null) ? createAddress(user.getAddress()): address.getId();

        final String sql = "INSERT INTO users (name,email,birthdate,address_id) VALUES (?,?,?,?)";
        jdbcTemplate.update(sql, user.getName(), user.getEmail(), user.getBirthdate(), address_id);
    }

    @Override
    public Optional<User> getUserById(int id) {
        return Optional.empty();
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

    private Optional<Address> getAddres(Address address){
        final String sql_address = "SELECT * FROM address WHERE street = ? AND state = ? AND city = ? AND country = ? AND zip = ?";
        Address address1 = jdbcTemplate.queryForObject(sql_address, new Object[]{address.getStreet(),address.getState(),address.getCity(),address.getCountry(),address.getZip()},
                ((resultSet, i) -> {
                    String street = resultSet.getString("street");
                    String state = resultSet.getString("state");
                    String city = resultSet.getString("city");
                    String country = resultSet.getString("country");
                    String zip = resultSet.getString("zip");
                    int id = resultSet.getInt("id");
                    Address addrss = new Address(street,state,city,country,zip);
                    addrss.setId(id);
                    return  addrss;
        }));

        return Optional.ofNullable(address1);
    }

    private int createAddress(Address address){
        final String sql = "INSERT INTO address (street,state,city,country,zip) VALUES (?,?,?,?,?) RETURNING id";
        return jdbcTemplate.update(sql, address.getStreet(),address.getState(),address.getCity(),address.getCountry(),address.getZip());
    }
}
