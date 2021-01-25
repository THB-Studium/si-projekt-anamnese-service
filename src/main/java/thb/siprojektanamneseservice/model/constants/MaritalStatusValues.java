package thb.siprojektanamneseservice.model.constants;

public enum MaritalStatusValues {
    SINGLE("single"),
    CIVIL_UNION("civil_union"),
    MARRIED("married"),
    DIVORCED("divorced"),
    WIDOWED("widowed");

    private final String maritalStatusValue;

    MaritalStatusValues(String maritalStatusValue) {
        this.maritalStatusValue = maritalStatusValue;
    }
}
