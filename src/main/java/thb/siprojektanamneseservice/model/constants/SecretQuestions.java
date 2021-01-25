package thb.siprojektanamneseservice.model.constants;


public enum SecretQuestions {
    STRASSE_DES_AUFWACHSENS("In welcher Straße sind Sie aufgewachsen?"),
    MUTTER_MAEDCHENNAME("Wie lautet der Mädchenname Ihrer Mutter?"),
    HAUSTIER_NAME("Was war der Name Ihres Haustiers aus der Kindheit?"),
    GRUNDSCHULE("Wo haben Sie die Grundschule besucht?"),
    BESTE_FREUND("Wie lautete der Name Ihres besten Freundes, als Sie aufwuchsen?");

    private final String secretQuestionValue;

    SecretQuestions(String secretQuestionValue) {
        this.secretQuestionValue = secretQuestionValue;
    }
}
