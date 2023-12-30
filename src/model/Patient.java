package model;

import java.io.InputStream;

public class Patient extends model.User{


    private String KanGrubu;

    private String adres;

    private String iletisimBligileri;

    private String alerjiDurumu;


    private String hastalikGecmisi;

    private String sifre;


    public Patient(int id, String firstName, String lastName, String identityNumber, String dateOfBirth, String yas, String cinsiyet,String sifre) {
        super(id, firstName, lastName, identityNumber, dateOfBirth, yas, cinsiyet,sifre);
    }

    public Patient() {
        super();
    }

    public Patient(String isim, String soyIsim,String TcNo,String adres) {
        firstName=isim;
        lastName=soyIsim;
        identityNumber=TcNo;
        this.adres=adres;
    }
    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

}
