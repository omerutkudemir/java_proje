package model;

public class Doctor{

    public String isim;
    public String soyIsim;
    public String tcNo;
    public String sifre;

    public void setFirstName(String isim) {
        this.isim=isim;
    }

    public void setLastName(String soyIsim) {
        this.soyIsim=soyIsim;
    }

    public void setIdentityNumber(String tcNo) {
        this.tcNo=tcNo;
    }

    public void setSifre(String sifre) {
        this.sifre=sifre;

    }

    public String getLastName() {
        return soyIsim;
    }

    public String getFirstName() {
        return isim;
    }



    public String getIdentityNumber() {
        return tcNo;
    }


    public String getSifre() {
        return sifre;
    }


}
