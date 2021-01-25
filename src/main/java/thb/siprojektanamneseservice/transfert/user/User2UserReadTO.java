package thb.siprojektanamneseservice.transfert.user;

import thb.siprojektanamneseservice.model.Person;

import java.util.List;
import java.util.stream.Collectors;

public class User2UserReadTO {

    public static final UserReadTO apply(Person in) {
        UserReadTO out = new UserReadTO();
        out.setId(in.getId());
        out.setUserName(in.getUserName());
        out.roles(in.getRoles());
        return out;
    }

    public static final List<UserReadTO> apply(List<Person> in) {
        return in.stream().map(user -> User2UserReadTO.apply(user)).collect(Collectors.toList());
    }
}
