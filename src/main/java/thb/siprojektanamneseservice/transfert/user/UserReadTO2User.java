package thb.siprojektanamneseservice.transfert.user;

import thb.siprojektanamneseservice.model.Person;

public class UserReadTO2User {

    public static final Person apply(UserReadTO in) {
        Person out = new Person();
        out.setId(in.getId());
        out.setUserName(in.getUserName());
        return out;
    }
}
