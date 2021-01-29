import javafx.beans.property.*;

import java.sql.Date;

public class Package {

    private IntegerProperty id;
    private StringProperty rozmiar;
    private StringProperty stan_paczki;
    private StringProperty data_nadania;
    private StringProperty data_odbioru;
    private StringProperty ulica;
    private IntegerProperty nr;
    private StringProperty miasto;
    private StringProperty kraj;
    private StringProperty nadawca;
    private StringProperty odbiorca;


    public Package() {
        id=new SimpleIntegerProperty();
        rozmiar=new SimpleStringProperty();
        stan_paczki=new SimpleStringProperty();
        data_nadania=new SimpleStringProperty();
        data_odbioru=new SimpleStringProperty();
        ulica=new SimpleStringProperty();
        nr=new SimpleIntegerProperty();
        miasto=new SimpleStringProperty();
        kraj=new SimpleStringProperty();
        nadawca=new SimpleStringProperty();
        odbiorca=new SimpleStringProperty();

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

    public String getRozmiar() {
        return rozmiar.get();
    }


    public StringProperty rozmiarProperty() {
        return rozmiar;
    }

    public void setRozmiar(String rozmiar) {
        this.rozmiar.set(rozmiar);
    }

    public String getStan_paczki() {
        return stan_paczki.get();
    }

    public StringProperty stan_paczkiProperty() {
        return stan_paczki;
    }

    public void setStan_paczki(String stan_paczki) {
        this.stan_paczki.set(stan_paczki);
    }

    public String getData_nadania() {
        return data_nadania.get();
    }

    public StringProperty data_nadaniaProperty() {
        return data_nadania;
    }

    public void setData_nadania(String data_nadania) {
        this.data_nadania.set(data_nadania);
    }

    public String getData_odbioru() {
        return data_odbioru.get();
    }

    public StringProperty data_odbioruProperty() {
        return data_odbioru;
    }

    public void setData_odbioru(String data_odbioru) {
        this.data_odbioru.set(data_odbioru);
    }

    public String getUlica() {
        return ulica.get();
    }

    public StringProperty ulicaProperty() {
        return ulica;
    }

    public void setUlica(String ulica) {
        this.ulica.set(ulica);
    }

    public int getNr() {
        return nr.get();
    }

    public IntegerProperty nrProperty() {
        return nr;
    }

    public void setNr(int nr) {
        this.nr.set(nr);
    }

    public String getMiasto() {
        return miasto.get();
    }

    public StringProperty miastoProperty() {
        return miasto;
    }

    public void setMiasto(String miasto) {
        this.miasto.set(miasto);
    }

    public String getKraj() {
        return kraj.get();
    }

    public StringProperty krajProperty() {
        return kraj;
    }

    public void setKraj(String kraj) {
        this.kraj.set(kraj);
    }
    public String getNadawca() {
        return kraj.get();
    }

    public StringProperty nadawcaProperty() {
        return nadawca;
    }

    public void setNadawca(String nadawca) {
        this.nadawca.set(nadawca);
    }
    public String getOdbiorca() {
        return odbiorca.get();
    }

    public StringProperty odbiorcaProperty() {
        return odbiorca;
    }

    public void setOdbiorca(String odbiorca) {
        this.odbiorca.set(odbiorca);
    }
}
