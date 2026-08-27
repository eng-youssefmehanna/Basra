package main.java.viewmodel.result;

import main.java.model.Player;
import main.java.model.state.LoginState;

public record LoginResult(LoginState state, Player player) {

}


