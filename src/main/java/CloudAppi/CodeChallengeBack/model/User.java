package CloudAppi.CodeChallengeBack.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotBlank;
import java.sql.Date;

public class User {


    private int id;

    @NotBlank
    @NonNull
    private String name;

    @NotBlank
    @NonNull
    private String email;

    @NotBlank
    @NonNull
    private Date birthdate;

    @NotBlank
    @NonNull
    private Address address;


    public User(@JsonProperty("name") String name,
                @JsonProperty("email")String email,
                @JsonProperty("birthDate")Date birthdate,
                @JsonProperty("addres")Address address){
        this.name = name;
        this.email = email;
        this.birthdate = birthdate;
        this.address = address;
    }

    public User(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }


}
