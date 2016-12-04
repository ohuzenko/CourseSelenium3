import java.util.Date;

/**
 * Created by sunny_IT on 12/4/2016.
 */
public class UserAccount {

    private long sfx = System.currentTimeMillis();
    private String firstName = "test" + sfx;
    private String lastName = "test" + sfx;
    private String postCode = "P0T 2S0";
    private String email = "test"+sfx+"@test.com";
    private String address1 = "SCHREIBER ON   P0T 2S0";
    private String city  = "Alberta";
    private String country  = "Canada";
    private String phone = "+15555555";
    private String password = "test";

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPostCode() {
        return postCode;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress1() {
        return address1;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }
}
