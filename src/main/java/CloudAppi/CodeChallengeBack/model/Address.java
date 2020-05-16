package CloudAppi.CodeChallengeBack.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.lang.NonNull;
import javax.validation.constraints.NotBlank;

public class Address {

    private int id;

    @NotBlank
    @NonNull
    private String street;

    @NotBlank
    @NonNull
    private String state;

    @NotBlank
    @NonNull
    private String city;

    @NotBlank
    @NonNull
    private String country;

    @NotBlank
    @NonNull
    private String zip;

    public Address(@JsonProperty("street") String street,
                   @JsonProperty("state") String state,
                   @JsonProperty("city") String city,
                   @JsonProperty("country") String country,
                   @JsonProperty("zip") String zip) {
        this.street = street;
        this.state = state;
        this.city = city;
        this.country = country;
        this.zip = zip;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}
