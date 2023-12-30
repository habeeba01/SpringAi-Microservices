package Soring.AI.spring.ai.Service;

import java.util.List;

public record InfosDetails( String nom,String directeur,String date_ouverture,String heure_ouverture,String date_fermeture,String heure_fermeture,List<String>specialites,String description) {
}
