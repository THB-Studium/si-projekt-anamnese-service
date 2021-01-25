package thb.siprojektanamneseservice.rest;

public class ApiConstants {

    // API
    public static final String API = "api";

    // Cross-Origin:
    public static final String CROSS_ORIGIN_PATH = "*";

    // Sessions:
    public static final String SESSIONS_COLLECTION = "/sessions";
    public static final String SESSIONS_ITEM = SESSIONS_COLLECTION + "/{sessionId}";
    public static final String SESSIONS_ITEM_DETAILS = SESSIONS_ITEM + "/person_details";

    public static final String AUTH_COLLECTION_PATH = "/oauth/token?grant_type=password&username=%s&password=%s";
    public static final String REVOKE_TOKEN = "/tokens/revoke";

    // Person
    public static final String PERSON_ROOT = API + "/persons";
    public static final String PERSON_ITEM = PERSON_ROOT + "/{personId}";

    public static final String PERSON_DIAGNOSIS_ROOT = PERSON_ITEM + "/diagnosis";
    public static final String PERSON_DISEASE_ROOT = PERSON_ITEM + "/diseases";
    public static final String PERSON_MEDICATION_IN_TAKE_ROOT = PERSON_ITEM + "/medication_in_takes";

    // MedicationInTake
    public static final String MEDICATION_IN_TAKE_ROOT = API + "/medication_in_takes";
    public static final String MEDICATION_IN_TAKE_ITEM = MEDICATION_IN_TAKE_ROOT + "/{medicationInTakeId}";

    // PreExistingIllnesses
    public static final String PRE_EXISTING_ILLNESS_ROOT = API + "/preExistingIllnesses";
    public static final String PRE_EXISTING_ILLNESS_ITEM = PRE_EXISTING_ILLNESS_ROOT + "/{preExistingIllnessId}";

    // AllergyType
    public static final String ALLERGY_TYPE_ROOT = API + "/allergies";
    public static final String ALLERGY_TYPE_ITEM = ALLERGY_TYPE_ROOT + "/{allergyTypeId}";

    // Diagnosis
    public static final String DIAGNOSIS_ROOT = API + "/diagnoses";
    public static final String DIAGNOSIS_ITEM = DIAGNOSIS_ROOT + "/{diagnosisId}";

    // Address
    public static final String ADDRESS_ROOT = API + "/addresses";
    public static final String ADDRESS_ITEM = ADDRESS_ROOT + "/{addressId}";

    // Vegetative anamnesis
    public static final String VEGETATIVEANAMNESIS_ROOT = API + "/vegetativeAnamnesis";
    public static final String VEGETATIVEANAMNESIS_ITEM = VEGETATIVEANAMNESIS_ROOT + "/{vegetativeAnamnesisId}";

    // FamilyAnamnesis
    public static final String FAMILYANAMNESIS_ROOT = API + "/familyAnamnesis";
    public static final String FAMILYANAMNESIS_ITEM = FAMILYANAMNESIS_ROOT + "/{familyAnamnesisId}";

    // Disease
    public static final String DISEASE_ROOT = API + "/diseases";
    public static final String DISEASE_ITEM = DISEASE_ROOT + "/{diseaseId}";

    // Security
    public static final String SECURITY_ROOT = API + "/securities";
    public static final String SECURITY_ITEM = SECURITY_ROOT + "/{securityId}";
}
