package Soring.AI.spring.ai.Controllers;

import Soring.AI.spring.ai.Service.ChatService;
import Soring.AI.spring.ai.Service.ChatbotService;
import dev.langchain4j.service.TokenStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

/*@RestController @RequestMapping("chatbot")
public class ChatbotController {
    @Autowired
    private ChatbotService chatbotService;
    @GetMapping("/chat")
    public Flux<String> chatWithBot(
            @RequestParam String chatId,
            @RequestParam String question
    ) {
        return chatbotService.chat(chatId, question);
    }
}*/
@RestController @RequestMapping("chatbot")
public class ChatbotController {
    @Autowired
    private ChatService botService;


    @PostMapping("/chat")
    public TokenStream chat(@RequestBody String message) {
        return botService.chat("chatId", message);
    }
}
