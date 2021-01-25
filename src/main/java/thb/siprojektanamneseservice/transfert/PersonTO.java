package thb.siprojektanamneseservice.transfert;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonTO {

    @NotNull(message = "Firstname cannot be null")
    private String firstName;

    @NotNull(message = "lastName cannot be null")
    private String lastName;

    private String profession;

    private String streetAndNumber;
    private String postalCode;
    private String country;
    private String city;

    private String phoneNumber;
    @Email(message = "email should be a valid email")
    private String email;
    private String gender;
    private String maritalStatus;
    private boolean children;

    private int height;
    private float weight;
    private String type;
    private String userName;
    private String password;
    private boolean recorded;

    @NotEmpty(message = "The secret question must not be empty")
    @NotNull(message = "The secret question cannot be null")
    private String secretQuestion;

    @NotEmpty(message = "The answer must not be empty")
    @NotNull(message = "The answer cannot be null")
    private String answer;

//    @NotEmpty(message = "The allergy name must not be null")
    private List<String> allergyNames = new ArrayList<>();

    private List<UUID> medicationIds = new ArrayList<>();
}
