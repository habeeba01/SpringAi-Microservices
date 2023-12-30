package Soring.AI.spring.ai.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "Infos")
public class Infos {
    @Id
    private String id;
    private String nom;
    private String directeur;
    private String date_ouverture;
    private String heure_ouverture;
    private String date_fermeture;
    private String heure_fermeture;
    private List<String> specialites;
    private String description;

}
