package thb.siprojektanamneseservice.transfert.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import thb.siprojektanamneseservice.model.Role;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserReadTO {
    private UUID id;
    private String userName;
    private boolean isActive;
    private Set<Role> roles = new HashSet<>();

    public UserReadTO id(UUID id) {
        this.id = id;
        return this;
    }

    public UserReadTO roles(Set<Role> roles) {
        this.roles = roles;
        return this;
    }

}
