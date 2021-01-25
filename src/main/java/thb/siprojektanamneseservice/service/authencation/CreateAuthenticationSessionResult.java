package thb.siprojektanamneseservice.service.authencation;


import thb.siprojektanamneseservice.model.Person;

public class CreateAuthenticationSessionResult {
    private String sessionId;
    private Person person;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

}
