package service;
import model.Patient;

import java.io.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class PatientService {

    Scanner girdi = new Scanner(System.in);

    List<Patient> hastaInfo = new ArrayList<>();


    public void uyeOl() throws IOException {
        String isim, soyIsim, TcNo, sifre;
        System.out.println("Adiniz: ");
        isim = girdi.next();
        System.out.println("Soyadiniz: ");
        soyIsim = girdi.next();
        System.out.println("TC kimlik numarasi: ");
        TcNo = girdi.next();
        System.out.println("Sifre belirleyin: ");
        sifre = girdi.next();
        System.out.println("Adres bilgilerinizi giriniz: ");
        String adresFonk = adresBelirle();

        // Yeni hasta nesnesini oluştur
        Patient yeniHasta = new Patient();
        yeniHasta.setFirstName(isim);
        yeniHasta.setLastName(soyIsim);
        yeniHasta.setIdentityNumber(TcNo);
        yeniHasta.setAdres(adresFonk);
        yeniHasta.setSifre(sifre);

        // Yeni hasta nesnesini hastaInfo listesine ekle
        hastaInfo.add(yeniHasta);

        // Dosyaya kaydet
        dosyayaKaydet();

        System.out.println("Giris sayfasina yonlendiriliyorsunuz: ");
        girisYap();
    }


    public void girisYap() throws IOException {
        int giris;

        int hastaIslem;
        String TcNo, sifre;

        System.out.println("Tc no giriniz: ");
        TcNo = girdi.next();
        System.out.println("Sifre giriniz: ");
        sifre = girdi.next();

        String dosyaYolu = "hastane.txt";
        String arananIfade = sifre;
        String arananIfade2= TcNo;
        if (dosyadaArama(dosyaYolu, arananIfade,arananIfade2))
        {
            System.out.println("giris basarili  ");
            System.out.println("yapacaginiz islemi seciniz: \n" +
                    "1- randevuAl\n" +
                    "2- tedaviyiGoruntule\n" +
                    "3- ilacBilgileri\n" +
                    "4- raporTalepEt\n" +
                    "5- tahlilSonucuAl\n" +
                    "6- randevuIptal");


            hastaIslem= girdi.nextInt();



            if(hastaIslem==1)
            {
                randevuAl(TcNo);
            }
            else if(hastaIslem==2)
            {
                tedaviyiGoruntule(TcNo);

            }
            else if(hastaIslem==3)
            {
                ilacBilgileri(TcNo);
            }
            else if(hastaIslem==4)
            {
                raporTalepEt();
            }
            else if(hastaIslem==5)
            {
                tahlilSonucuGoruntule();
            }
            else if(hastaIslem==6)
            {
                randevuIptal();
            }

        }
        else
        {
            System.out.println("sifre veya tc no yanlis tekrar deneyiniz");

        }


    }




    public String adresBelirle(){
        String adres;
        String il,ilce,mahalle,sokak,binaNo,kapiNo;
        System.out.println("il:");
        il=girdi.next();

        System.out.println("ilce: ");
        ilce=girdi.next();

        System.out.println("mahalle: ");
        mahalle=girdi.next();

        System.out.println("sokak: ");
        sokak=girdi.next();

        System.out.println("bina no: ");
        binaNo=girdi.next();

        System.out.println("kapi no: ");
        kapiNo=girdi.next();
        adres="sehir: "+ il +" ilce: "+ilce+" mahalle: "+mahalle+" sokak: "+sokak+" bina no: "+binaNo+" kapi no: "+kapiNo;
        return adres;

    }
    public void randevuAl(String TcNo) {
        Scanner girdi = new Scanner(System.in);
        String dosyaYolu = "randevuSaat.txt";
        String arananIfade = "Saatler";

        try (BufferedReader br = new BufferedReader(new FileReader(dosyaYolu))) {
            String satir;
            while ((satir = br.readLine()) != null) {
                System.out.println(satir);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String doktorIsim, doktorSoyisim;


        dosyaYolu = "randevuSaat.txt";
        System.out.println("randevu alacaginiz doktorun adi: ");
        doktorIsim = girdi.nextLine();

        System.out.println("soyadi: ");
        doktorSoyisim = girdi.nextLine();

        LocalTime baslangicZamani = null;
        LocalTime bitisZamani = null;
        try (BufferedReader br = new BufferedReader(new FileReader(dosyaYolu))) {
            String satir;
            while ((satir = br.readLine()) != null) {
                if (satir.contains(doktorSoyisim) && satir.contains(doktorIsim)) {
                    // Eğer soyisim eşleşiyorsa, bir sonraki satırı yazdır
                    String sonrakiSatir = br.readLine();
                    if (sonrakiSatir != null) {
                        String[] saatParcalari = sonrakiSatir.split("[-:]");
                        int baslangicSaat = Integer.parseInt(saatParcalari[1].trim());
                        int baslangicDakika = Integer.parseInt(saatParcalari[2].trim());
                        int bitisSaat = Integer.parseInt(saatParcalari[3].trim());
                        int bitisDakida = Integer.parseInt(saatParcalari[4].trim());

                        baslangicZamani = LocalTime.of(baslangicSaat, baslangicDakika);

                        bitisZamani = LocalTime.of(bitisSaat, bitisDakida);


                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scanner girdi1 = new Scanner(System.in);

        String saatGirisi, dakikaGirisi;
        System.out.println("randevu alacaginiz saati ve dakikayı giriniz(saat:dakika): ");
        saatGirisi = girdi1.nextLine();
        LocalTime localTime = LocalTime.parse(saatGirisi);

        if (localTime.isAfter(baslangicZamani) && localTime.isBefore(bitisZamani))
        {
            randevuAl(TcNo,doktorIsim,doktorSoyisim,localTime);
        }
        else {
            System.out.println("yanlis saat araligi girdiniz doktor bu saatlerde musait deil");
        }


    }

    private void randevuAl(String TcNo, String doktorIsim, String doktorSoyisim, LocalTime localTime) {

        try (BufferedReader br = new BufferedReader(new FileReader("hastane.txt"))) {
            String satir,soyad = null,isim = null;
            while ((satir = br.readLine()) != null) {
                if(satir.contains(TcNo))
                {
                    if (soyad != null && isim != null) {
                        // Önceki iki satırı yazdırabilir veya başka bir işlem yapabilirsiniz
                        String[] isimParcalari=isim.split(": ");
                        String[] soyadParcalari=soyad.split(": ");


                        randevuAl(isimParcalari[1],soyadParcalari[1],doktorIsim,doktorSoyisim,localTime);
                    }

                }

                // Satırları kaydederken eski değerleri güncelle
                isim = soyad;
                soyad = satir;
                }


            }
            catch (IOException ex)
            {
                throw new RuntimeException(ex);
            }

    }

    private void randevuAl(String isim, String soyad, String doktorIsim, String doktorSoyisim, LocalTime localTime)
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("randevuKayit.txt",true))) {

            writer.write("\n"+isim+" "+soyad+" randevunuz "+doktorIsim+" "+doktorSoyisim+ " isimli doktoradır");

            writer.write(" saat: "+localTime);



        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void tedaviyiGoruntule(String TcNo)
    {
        try (BufferedReader br = new BufferedReader(new FileReader("tedaviler.txt"))) {
            String satir,tedavi = null,kisi = null;
            while ((satir = br.readLine()) != null) {
                if(satir.contains(TcNo))
                {
                    if (tedavi != null && kisi != null) {
                        // Önceki iki satırı yazdırabilir veya başka bir işlem yapabilirsiniz
                        System.out.println(kisi+tedavi);


                    }

                }

                // Satırları kaydederken eski değerleri güncelle
                kisi = tedavi;
                tedavi = satir;
            }


        }
        catch (IOException ex)
        {
            throw new RuntimeException(ex);
        }


    }
    public void ilacBilgileri(String TcNo)
    {
        String dosyaYolu = "ilaclar.txt";
        String arananIfade = TcNo;

        try (BufferedReader br = new BufferedReader(new FileReader(dosyaYolu))) {
            String satir;
            while ((satir = br.readLine()) != null) {
                if (satir.contains(arananIfade)) {
                    System.out.println(satir);

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void raporTalepEt()
    {

    }
    public void tahlilSonucuGoruntule()
    {

    }

    public void randevuIptal()
    {

    }

    public void dosyayaKaydet() throws IOException {
        FileWriter fw = new FileWriter("hastane.txt", true);
        BufferedWriter writer = new BufferedWriter(fw);

        for (Patient hasta : hastaInfo) {
            writer.write("ID: " + hasta.getId() + "\n");
            writer.write("Ad: " + hasta.getFirstName() + "\n");
            writer.write("Soyad: " + hasta.getLastName() + "\n");
            writer.write("TC Kimlik No: " + hasta.getIdentityNumber() + "\n");
            writer.write("Doğum Tarihi: " + hasta.getDateOfBirth() + "\n");
            writer.write("Yaş: " + hasta.getYas() + "\n");
            writer.write("Cinsiyet: " + hasta.getCinsiyet() + "\n");
            writer.write("Adres: " + hasta.getAdres() + "\n");
            writer.write("Sifre: " + hasta.getSifre() + "\n");
            writer.write("\n-------------------------\n");
        }

        writer.close();
    }


    private String[] satirDizisi; // Sınıf seviyesinde bir değişken



    public boolean dosyadaArama( String dosyaYolu, String arananIfade, String arananIfade2) throws IOException {

        try (BufferedReader br = new BufferedReader(new FileReader(dosyaYolu))) {
            String satir;
            while ((satir = br.readLine()) != null) {
                if (satir.contains(arananIfade)) {
                    return true;
                }
            }
        }
        return false;
    }



}


