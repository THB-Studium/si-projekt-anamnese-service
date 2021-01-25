package thb.siprojektanamneseservice.model.constants;

public enum VegetativeAnamnesisDecisionValues {

    BURNING("burning"),
    DIARRHEA("diarrhea"),
    DIARRHEA_WITH_BLOOD("diarrhea_With_blood"),
    NORMAL("normal"),
    FREQUENTLY("frequently"),
    TEETHING_TROUBLES("teething_troubles"),
    INCREASED("increased");

    private final String anamnesisDecisionValue;

    VegetativeAnamnesisDecisionValues(String anamnesisDecisionValue) {
        this.anamnesisDecisionValue = anamnesisDecisionValue;
    }
}
