package zoologia;
import java.util.*;

public class Gui {
    private Map<String, List<String>> userPreferences;

    public Gui() {
        userPreferences = new HashMap<>();
    }

    public void addUserPreferences(String user, List<String> preferences) {
        userPreferences.put(user, preferences);
    }   

    public List<String> recommend(String user) {
        List<String> recommendations = new ArrayList<>();
        List<String> preferences = userPreferences.get(user);

        if (preferences == null) {
            System.out.println("El usuario no tiene preferencias.");
            return recommendations; // Devuelve una lista vacía si el usuario no tiene preferencias
        }

        for (String otherUser : userPreferences.keySet()) {
            if (!otherUser.equals(user)) {
                List<String> otherPreferences = userPreferences.get(otherUser);
                int commonCount = countCommonPreferences(preferences, otherPreferences);

                System.out.println("Comparando con " + otherUser);
                System.out.println("Preferencias del usuario actual: " + preferences);
                System.out.println("Preferencias del otro usuario: " + otherPreferences);
                System.out.println("Elementos comunes: " + commonCount);

                if (commonCount >= 1) { // Umbral basado en las preferencias del usuario actual
                    for (String item : otherPreferences) {
                        if (!preferences.contains(item) && !recommendations.contains(item)) {
                            recommendations.add(item);
                        }
                    }
                } else {
                    recommendations.add("No hay elementos en común con otros usuarios");
                }
            }
        }
        return recommendations;
    }

    private int countCommonPreferences(List<String> preferences, List<String> otherPreferences) {
        int count = 0;
        for (String preference : preferences) {
            if (otherPreferences.contains(preference)) {
                count++;
            }
        }
        return count;
    }

    public Set<String> getAllUsers() {
        return userPreferences.keySet();
    }
}
