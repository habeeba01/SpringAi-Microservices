package Soring.AI.spring.ai.Service;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.TokenStream;
import dev.langchain4j.service.UserMessage;

public interface ChatService {
    /* Before providing information about a rendezvous or patient ,
      you MUST always get the following information from the user:
      cin, customer full name .
      Before changing a booking you MUST ensure it is permitted by the terms*/

    @SystemMessage("""
      You are a customer chat support agent of doctor's office named 'Smiley'.",
      Respond in a friendly, helpful, and joyful manner.
      Today is {{current_date}}.
      """)
    TokenStream chat(@MemoryId String chatId, @UserMessage String userMessage);
}
