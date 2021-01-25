package thb.siprojektanamneseservice.model.constants;


public enum AllergyValues {
    ANTIBIOTICS("antibiotics"),
    FRUCTOSE("fructose"),
    FOODS("foods"),
    POLLEN("pollen"),
    RADIO_OPAQUE_SUBSTANCE("radio_opaque_substance"),
    ANIMAL_HAIR("animal_hair"),
    LOCAL_ANAESTHETICS("local_anaesthetics"),
    LACTOSE("lactose"),
    HOUSE_DUST("House_dust"),
    PRESERVATIVES("preservatives"),
    VITAMIN_B("vitamin_b");

    private final String allergyValue;

    AllergyValues(String allergyValue) {
        this.allergyValue = allergyValue;
    }
}
