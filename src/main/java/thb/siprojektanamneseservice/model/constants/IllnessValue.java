package thb.siprojektanamneseservice.model.constants;

public enum IllnessValue {

    BLOOD_COAGULATION_DISORDER("blood coagulation disorder"),
    EAR_DISEASE("ear disease"),
    EYE_DISEASE("eye disease"),
    GASTRO_OR_INTESTINAL_DISEASE("gastro or intestinal disease"),
    HEART_DISEASE("heart_disease"),
    JOINT_DISEASE("joint disease"),
    KIDNEY_DISEASE("kidney disease"),
    LIVER_DISEASE("liver disease"),
    MENTAL_ILLNESS("mental illness"),
    PULMONARY_DISEASE("pulmonary disease"),
    SKIN_DISEASE("skin disease"),
    THYROID_DISEASE("thyroid_disease"),
    URIC_ACID_METABOLISM_DISORDER("uric acid metabolism disorder"),
    VASCULAR_DISEASES("vascular diseases");

    private final String preExistingIllnesValue;

    IllnessValue(String preExistingIllnesValue) {
        this.preExistingIllnesValue = preExistingIllnesValue;
    }
}
