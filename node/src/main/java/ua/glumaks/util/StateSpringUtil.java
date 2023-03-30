package ua.glumaks.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import ua.glumaks.domain.UserState;
import ua.glumaks.service.command.*;
import ua.glumaks.service.state.BasicState;
import ua.glumaks.service.state.State;
import ua.glumaks.service.state.WaitingForEmailState;

@Slf4j
@Component
public class StateSpringUtil {

    private static ApplicationContext context;


    @Autowired
    public void setContext(ApplicationContext context) {
        StateSpringUtil.context = context;
    }

    public static State forType(UserState type) {
        return switch (type) {
            case BASIC_STATE -> context.getBean(BasicState.class);
            case WAIT_FOR_EMAIL_STATE -> context.getBean(WaitingForEmailState.class);
            default -> {
                log.error("Unknown state type: " + type);
                throw new EnumConstantNotPresentException(UserState.class, type.name());
            }
        };
    }

}
