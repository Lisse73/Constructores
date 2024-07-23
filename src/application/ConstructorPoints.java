package application; 
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ConstructorPoints {
    private final SimpleStringProperty constructorName;
    private final SimpleIntegerProperty totalPoints;

    public ConstructorPoints(String constructorName, int totalPoints) {
        this.constructorName = new SimpleStringProperty(constructorName);
        this.totalPoints = new SimpleIntegerProperty(totalPoints);
    }

    public String getConstructorName() {
        return constructorName.get();
    }

    public void setConstructorName(String constructorName) {
        this.constructorName.set(constructorName);
    }

    public int getTotalPoints() {
        return totalPoints.get();
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints.set(totalPoints);
    }
}
