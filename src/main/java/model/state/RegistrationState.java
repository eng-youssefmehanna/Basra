package main.java.model.state;

public enum RegistrationState {
    IDLE,
    LOADING,
    EMPTYFIELDS,
    PASSWORDMISMATCH,
    PASSWORDREQS,
    USERNAMETAKEN,
    SUCCESS,
    NETWORKERROR
}
