package CloudAppi.CodeChallengeBack.dao;

import CloudAppi.CodeChallengeBack.model.Address;
import CloudAppi.CodeChallengeBack.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository("postgres")
public class UsersDataAccessService implements IUsersDao{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UsersDataAccessService (JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Me hubiera gustado haber podido implementar estas queries con prepared statements, para evitar así posibles
     * inyecciones de sql. Sin embargo no he encontrado la forma de realizarlo para los "SELECT", pero sí para el resto
     * de queries.
     * @return
     */
    @Override
    public List<User> getUsers() {
        final String sql_user = "SELECT * FROM users";
        return jdbcTemplate.query(sql_user,((resultSet, i) -> {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String email = resultSet.getString("email");
            Date birthdate = resultSet.getDate("birthdate");
            int address_id = resultSet.getInt("address_id");
            Optional<Address> address = getAddressById(address_id);
            User user = new User(name,email,birthdate,address.orElse(null));
            user.setId(id);
            return user;
        }));
    }

    @Override
    public void createUser(User user) {
        createAddress(user.getAddress());
        int address_id = getAddres(user.getAddress()).orElse(null).getId();
        final String sql = "INSERT INTO users (name,email,birthdate,address_id) VALUES (?,?,?,?)";
        jdbcTemplate.update(sql, user.getName(), user.getEmail(), user.getBirthdate(), address_id);
    }

    @Override
    public Optional<User> getUserById(int id) {
        final String sql = "SELECT * FROM users WHERE id = ?";
        User user;
        try{
            user = jdbcTemplate.queryForObject(sql, new Object[]{id}, ((resultSet, i) -> {
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                Date birthdate = resultSet.getDate("birthdate");
                int address_id = resultSet.getInt("address_id");
                Optional<Address> address = getAddressById(address_id);
                User u = new User(name,email,birthdate,address.orElse(null));
                u.setId(id);
                return u;
            }));
        } catch (EmptyResultDataAccessException e){
            user = null;
        }
        return Optional.ofNullable(user);
    }

    @Override
    public void updateUserById(int id, User user) {
        User u = getUserById(id).orElse(null);
        if(u == null) return;

        int address_index = u.getAddress().getId();
        final String sqlUser = "UPDATE users SET name = ?, email = ?, birthdate = ? WHERE id = ?";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sqlUser);
            ps.setString(1,user.getName());
            ps.setString(2,user.getEmail());
            ps.setDate(3, user.getBirthdate());
            ps.setInt(4, id);
            return ps;
        });

        final String sqlAddress = "UPDATE address SET street = ?, state = ?, city = ?, country = ?, zip = ? WHERE id = ?";
        Address address = user.getAddress();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sqlAddress);
            ps.setString(1,address.getStreet());
            ps.setString(2,address.getState());
            ps.setString(3,address.getCity());
            ps.setString(4,address.getCountry());
            ps.setString(5,address.getZip());
            ps.setInt(6, address_index);
            return ps;
        });
    }

    @Override
    public void deleteUserById(int id) {
        User u = getUserById(id).orElse(null);
        if(u == null) return;

        int address_index = u.getAddress().getId();

        final String sqlUsers = "DELETE FROM users WHERE id = ?";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sqlUsers);
            ps.setInt(1, id);
            return ps;
        });

        final String sqlAddress = "DELETE FROM address WHERE id = ?";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sqlAddress);
            ps.setInt(1, address_index);
            return ps;
        });
    }

    private Optional<Address> getAddressById(int id){
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
        List<Address> address1 = jdbcTemplate.query(sql_address, new Object[]{address.getStreet(),address.getState(),address.getCity(),address.getCountry(),address.getZip()},
                ((resultSet,i) -> {
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

        return Optional.ofNullable(address1.get(address1.size()-1));
    }

    /**
     * Mi intención era que este método devolviera el de la fila insertada ID para poder relacionarlo con el usuario correspondiente.
     * Lo he intentado mediante el uso de un KeyHolder pero no lo he logrado. Es por esto que para hallar el id hago
     * una segunda query para encontrar el último Address con las carácterísticas del que acabamos de insertar
     * @param address
     */
    private void createAddress(Address address){
        final String sql = "INSERT INTO address (street,state,city,country,zip) VALUES (?,?,?,?,?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,address.getStreet());
            ps.setString(2,address.getState());
            ps.setString(3,address.getCity());
            ps.setString(4,address.getCountry());
            ps.setString(5,address.getZip());
            return ps;
        });
    }
}
