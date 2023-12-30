package Soring.AI.spring.ai;

import Soring.AI.spring.ai.Service.ChatService;
import Soring.AI.spring.ai.Tools.InfoTool;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.TokenWindowChatMemory;
import dev.langchain4j.model.Tokenizer;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.embedding.AllMiniLmL6V2EmbeddingModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import dev.langchain4j.model.openai.OpenAiTokenizer;
import dev.langchain4j.retriever.EmbeddingStoreRetriever;
import dev.langchain4j.retriever.Retriever;
import dev.langchain4j.service.*;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;

import static dev.langchain4j.data.document.FileSystemDocumentLoader.loadDocument;
import static dev.langchain4j.model.openai.OpenAiModelName.GPT_4;

@SpringBootApplication @EnableDiscoveryClient
@EnableFeignClients //@Theme(value = "chatbot-personnalisé")
public class SpringAiApplication implements AppShellConfigurator {

	public static void main(String[] args) {
		SpringApplication.run(SpringAiApplication.class, args);
	}
	@Bean
	EmbeddingModel embeddingModel() {
		return new AllMiniLmL6V2EmbeddingModel();
	}

	@Bean
	EmbeddingStore<TextSegment> embeddingStore() {
		return new InMemoryEmbeddingStore<>();
	}

	@Bean
	Tokenizer tokenizer() {
		return new OpenAiTokenizer(GPT_4);
	}
	@Bean
	CommandLineRunner docsToEmbeddings(
			EmbeddingModel embeddingModel,
			EmbeddingStore<TextSegment> embeddingStore,
			Tokenizer tokenizer,
			ResourceLoader resourceLoader
	) throws IOException {
		return args -> {
			Resource resource =
					resourceLoader.getResource("classpath:/Users/habibaezzagrani/Desktop/SpringAI-MicroServices/info.json");
			var termsOfUse = loadDocument(resource.getFile().toPath());

			DocumentSplitter documentSplitter = DocumentSplitters.recursive(100, 0,
					tokenizer);

			EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
					.documentSplitter(documentSplitter)
					.embeddingModel(embeddingModel)
					.embeddingStore(embeddingStore)
					.build();
			ingestor.ingest(termsOfUse);
		};
	}

	@Bean
	StreamingChatLanguageModel chatLanguageModel(@Value("${api.key}") String apiKey) {
		return OpenAiStreamingChatModel.builder()
				.apiKey(apiKey)
				.modelName("gpt-4-1106-preview")
				.build();
	}


	@Bean
	Retriever<TextSegment> retriever(
			EmbeddingStore<TextSegment> embeddingStore,
			EmbeddingModel embeddingModel
	) {
		return EmbeddingStoreRetriever.from(
				embeddingStore,
				embeddingModel,
				1,
				0.6);
	}
	@Bean
	ChatService customerSupportAgent(
			StreamingChatLanguageModel chatLanguageModel,
			Tokenizer tokenizer,
			Retriever<TextSegment> retriever,
			InfoTool tools
	) {
		return AiServices.builder(ChatService.class)
				.streamingChatLanguageModel(chatLanguageModel)
				.chatMemoryProvider(chatId -> TokenWindowChatMemory.builder()
						.id(chatId)
						.maxTokens(500, tokenizer)
						.build())
				.retriever(retriever)
				.tools(tools)
				.build();
	}
}
