package metier.modele;

/**
 * Class representing astrological predictions for a person.
 * This class is not persisted in the database and is intended for one-time display.
 */
public class Prediction {
    
    // Prediction results
    private final String predictionAmour;
    private final String predictionSante;
    private final String predictionTravail;
    
    /**
     * Creates a new Prediction with the given values.
     * 
     * @param predictionAmour Love prediction text
     * @param predictionSante Health prediction text
     * @param predictionTravail Work prediction text
     */
    public Prediction(String predictionAmour, String predictionSante, String predictionTravail) {
        this.predictionAmour = predictionAmour;
        this.predictionSante = predictionSante;
        this.predictionTravail = predictionTravail;
    }
    
    /**
     * Factory method to create a Prediction from API results
     * 
     * @param predictions List of predictions from API (amour, sante, travail)
     * @return A new Prediction object
     */
    public static Prediction fromApiResults(java.util.List<String> predictions) {
        return new Prediction( predictions.get(0), predictions.get(1), predictions.get(2)
        );
    }

    public String getPredictionAmour() {
        return predictionAmour;
    }

    public String getPredictionSante() {
        return predictionSante;
    }

    public String getPredictionTravail() {
        return predictionTravail;
    }
    
    /**
     * Returns a formatted string representation of this prediction.
     * 
     * @return String representation of the prediction
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Prédictions :\n");
        sb.append("[ Amour ").append(predictionAmour).append("\n");
        sb.append("[ Santé ").append(predictionSante).append("\n");
        sb.append("[ Travail ").append(predictionTravail);
        return sb.toString();
    }
    
}