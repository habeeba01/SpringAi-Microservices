package Soring.AI.spring.ai.Tools;

import Soring.AI.spring.ai.Models.Infos;
import Soring.AI.spring.ai.Service.InfoService;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InfoTool {
    @Autowired
    private InfoService infoService;

    @Tool
    public List<Infos> getDetails() {
        return infoService.getAll();
    }

}