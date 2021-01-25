import javafx.beans.property.*;

public class Racket {

    private IntegerProperty id;
    private StringProperty model;
    private StringProperty manufacturer;
    private IntegerProperty mass;
    private IntegerProperty headSize;
    private StringProperty dominantColor;
    private DoubleProperty priceUSD;

    public Racket() {

        id = new SimpleIntegerProperty();
        model = new SimpleStringProperty();
        manufacturer = new SimpleStringProperty();
        mass = new SimpleIntegerProperty();
        headSize = new SimpleIntegerProperty();
        dominantColor = new SimpleStringProperty();
        priceUSD = new SimpleDoubleProperty();

    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getModel() {
        return model.get();
    }

    public StringProperty modelProperty() {
        return model;
    }

    public void setModel(String model) {
        this.model.set(model);
    }

    public String getManufacturer() {
        return manufacturer.get();
    }

    public StringProperty manufacturerProperty() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer.set(manufacturer);
    }

    public int getMass() {
        return mass.get();
    }

    public IntegerProperty massProperty() {
        return mass;
    }

    public void setMass(int mass) {
        this.mass.set(mass);
    }

    public int getHeadSize() {
        return headSize.get();
    }

    public IntegerProperty headSizeProperty() {
        return headSize;
    }

    public void setHeadSize(int headSize) {
        this.headSize.set(headSize);
    }

    public String getDominantColor() {
        return dominantColor.get();
    }

    public StringProperty dominantColorProperty() {
        return dominantColor;
    }

    public void setDominantColor(String dominantColor) {
        this.dominantColor.set(dominantColor);
    }

    public double getPriceUSD() {
        return priceUSD.get();
    }

    public DoubleProperty priceUSDProperty() {
        return priceUSD;
    }

    public void setPriceUSD(double priceUSD) {
        this.priceUSD.set(priceUSD);
    }
}
