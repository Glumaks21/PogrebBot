package ua.glumaks.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.glumaks.service.Dispatcher;
import ua.glumaks.service.handler.Handler;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class DispatcherImpl implements Dispatcher {

    private final Set<Handler> handlers;


    @Override
    public boolean dispatch(Update update) {
        for (Handler handler : handlers) {
            if (handler.isApplicable(update)) {
                handler.handle(update);
                return true;
            }
        }

        return false;
    }

}
