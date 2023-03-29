package ua.glumaks.service.state;

import ua.glumaks.domain.UserState;

public abstract class AbstractState implements State {

    private final UserState type;


    public AbstractState(UserState type) {
        this.type = type;
    }

}
