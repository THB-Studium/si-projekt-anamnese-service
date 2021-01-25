package thb.siprojektanamneseservice.model.constants;


public enum PersonTypes {
    PERSONAL("personal"),
    PATIENT("patient");

    private final String personTypeValue;

    PersonTypes(String personTypeValue) {
        this.personTypeValue = personTypeValue;
    }
}
