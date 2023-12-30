package Soring.AI.spring.ai.Service;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@AnonymousAllowed
public class ChatbotService {
    @Autowired
    private ChatService agent;

    public Flux<String> chat(String chatId, String question) {
        Sinks.Many<String> sink = Sinks.many().unicast().onBackpressureBuffer();

        agent.chat(chatId, question)
                .onNext(sink::tryEmitNext)
                .onComplete(aiMessageResponse -> sink.tryEmitComplete())
                .onError(sink::tryEmitError)
                .start();

        return sink.asFlux();
    }
}
