package service;
import java.time.LocalTime;
import model.Doctor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


import model.Patient;

public class DoctorService {

        List<Doctor> doctorInfo = new ArrayList<>();
        Patient hasta = new Patient();

        class CalculateSalary {
            public void receiveSalary(String IBAN) {
                System.out.println("Maas alindi");
            }

            public void receiveSalary(String creditCardNumber, String bankName) {
                System.out.println("Maas alindi");
            }

            public void receiveSalary() {

            }
        }

        public void uyeOl() throws IOException {
            Scanner girdi = new Scanner(System.in);
            String isim, soyIsim, TcNo, sifre;
            System.out.println("Adiniz: ");
            isim = girdi.next();
            System.out.println("Soyadiniz: ");
            soyIsim = girdi.next();
            System.out.println("TC kimlik numarasi: ");
            TcNo = girdi.next();
            System.out.println("Sifre belirleyin: ");
            sifre = girdi.next();

            Doctor yeniDoktor = new Doctor();
            yeniDoktor.setFirstName(isim);
            yeniDoktor.setLastName(soyIsim);
            yeniDoktor.setIdentityNumber(TcNo);
            yeniDoktor.setSifre(sifre);

            doctorInfo.add(yeniDoktor);
            dosyayaKaydet();

            System.out.println("Giris sayfasina yonlendiriliyorsunuz: ");
            girisYap();


        }

        public void dosyayaKaydet() throws IOException {
            FileWriter fw = new FileWriter("doktor.txt", true);
            BufferedWriter writer = new BufferedWriter(fw);

            for (Doctor doctor : doctorInfo) {

                writer.write("Ad: " + doctor.getFirstName() + "\n");
                writer.write("Soyad: " + doctor.getLastName() + "\n");
                writer.write("TC Kimlik No: " + doctor.getIdentityNumber() + "\n");

                writer.write("Sifre: " + doctor.getSifre() + "\n");
                writer.write("\n-------------------------\n");
            }

            writer.close();
        }

        public void girisYap() throws IOException {
            Scanner girdi = new Scanner(System.in);
            String sifre, TcNo;
            System.out.println("TC kimlik numarnizi giriniz: ");

            TcNo = girdi.next();

            System.out.println("sifrenizi giriniz: ");

            sifre = girdi.next();

            String dosyaYolu = "doktor.txt";
            String arananIfade = sifre;
            String arananIfade2= TcNo;
            if (dosyadaArama(dosyaYolu, arananIfade,arananIfade2)) {
                System.out.println("giris basarili  ");

                System.out.println("yapacaginiz islemi seciniz: \n" +
                        "0- profil goruntule\n" +
                        "1- hasta bilgileri goruntule\n" +
                        "2- randevu saatleri belirle\n" +
                        "3- ilac yaz\n" +
                        "4- tedavi gir\n" +
                        "5- sonuc gir\n" +
                        "6- randevuları görüntüle");

                int doctorIslem;
                doctorIslem = girdi.nextInt();

                if (doctorIslem == 0) {
                    profilGoruntule(sifre);
                }
                else if (doctorIslem==1) {
                    hastaBilgileriniGoruntule();

                }
                else if (doctorIslem==2) {
                    randevuSaatleriBelirle(TcNo);

                }
                else if (doctorIslem==3) {
                    ilacYaz();

                }
                else if (doctorIslem==4)
                {
                    tedaviGir();
                }
                else if (doctorIslem==5) {
                    sonucGir(TcNo);

                }
                else if (doctorIslem==6)
                {
                    randevulariGoruntule(TcNo);
                }

            }
            else {
                System.out.println("sifre veya tc no yanlis tekrar deneyiniz");

            }


        }

        private void randevulariGoruntule(String TcNo) throws IOException {

            String[] doktorBilgileri = doktorBilgisi(TcNo);

            String doktorBilgisi = String.join(" ", doktorBilgileri);
            System.out.println(doktorBilgisi + " randevunuzun ollduğu hastaların randevuları");
            try(BufferedReader br =new BufferedReader(new FileReader("randevuKayit.txt")))
            {
                String satir;
                while((satir= br.readLine())!=null)
                {
                    if (satir.contains(doktorBilgileri[0]) && satir.contains(doktorBilgileri[1])) {
                        System.out.println(satir);
                    }
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }

        }

    private String[] doktorBilgisi(String TcNo) {
            try (BufferedReader br = new BufferedReader(new FileReader("doktor.txt"))) {
            String satir,isim = null,soyad = null;
            while ((satir = br.readLine()) != null) {
                if(satir.contains(TcNo))
                {
                    if (soyad != null && isim != null) {
                        // Önceki iki satırı yazdırabilir veya başka bir işlem yapabilirsiniz
                        String[] isimParcalari=isim.split(": ");
                        String[] soyadParcalari=soyad.split(": ");
                        String[] isimVeSoyad= new String[2];
                        isimVeSoyad[0] = isimParcalari[1];
                        isimVeSoyad[1] = soyadParcalari[1];
                        return isimVeSoyad;


                    }

                }
                isim = soyad;
                soyad = satir;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void sonucGir(String tcNo)
        {
            Scanner girdi =new Scanner(System.in);
            String hastaTc;
            System.out.println("hangi hastanin sonucunu giriceksiniz TC yaziniz: ");
            hastaTc=girdi.next();
            try (BufferedReader br = new BufferedReader(new FileReader("hastane.txt"))) {
                String satir,isim = null,soyad = null;

                // Dosyadan satır okuma
                while ((satir = br.readLine()) != null) {
                    if(satir.contains(hastaTc))
                    {
                        if (soyad != null && isim != null)
                        {
                            String[] isimParcalari=isim.split(": ");
                            String[] soyadParcalari=soyad.split(": ");
                            String[] isimVeSoyad= new String[2];
                            isimVeSoyad[0] = isimParcalari[1];
                            isimVeSoyad[1] = soyadParcalari[1];
                            sonucGir(isimVeSoyad[0],isimVeSoyad[1]);
                        }


                    }
                    isim = soyad;
                    soyad = satir;

                }
            } catch (IOException e) {
                e.printStackTrace();
            }



        }

        public void sonucGir(String isim,String soyad)
        {
            Scanner girdi = new Scanner(System.in);
            String sonuc;
            System.out.println(isim+" "+soyad +" isimli hastanın sonucunu giriniz:");
            sonuc=girdi.nextLine();
            try(BufferedWriter writer= new BufferedWriter(new FileWriter("sonuclar.txt",true)))
            {
                writer.write(isim+" "+soyad);
                writer.newLine();
                writer.write(sonuc);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        private void tedaviGir()
        {
            Scanner girdi = new Scanner(System.in);
            String hastaTc,tedavi,hastaIsim;
            System.out.println("tedavisini gireceginiz hastanin tc kimlik numarasını giriniz: ");
            hastaTc=girdi.next();
            hastaIsim =hastayiBelirle(hastaTc);
            System.out.println("hastanın tedavisini giriniz: ");
            girdi.nextLine();
            tedavi=girdi.nextLine();
            tedaviGir(hastaIsim,tedavi,hastaTc);

        }

    private String hastayiBelirle(String hastaTc) {

        try (BufferedReader br = new BufferedReader(new FileReader("hastane.txt"))) {
            String satir,isim = null,soyad = null;

            // Dosyadan satır okuma
            while ((satir = br.readLine()) != null) {
                if(satir.contains(hastaTc))
                {
                    if (soyad != null && isim != null)
                    {
                        String[] isimParcalari=isim.split(": ");
                        String[] soyadParcalari=soyad.split(": ");
                        String[] isimVeSoyad= new String[2];
                        isimVeSoyad[0] = isimParcalari[1];
                        isimVeSoyad[1] = soyadParcalari[1];
                        return (isimVeSoyad[0]+" "+ isimVeSoyad[1]);
                    }


                }
                isim = soyad;
                soyad = satir;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
            return null;
    }
    public void tedaviGir(String isim,String tedavi,String hastaTc)
    {
        try(BufferedWriter writer =new BufferedWriter(new FileWriter("tedaviler.txt",true)))
        {


            writer.write(isim+" isimli hastanin tedavisi: ");
            writer.newLine();
            writer.write(tedavi);
            writer.newLine();
            writer.write(hastaTc);

        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void hastaBilgileriniGoruntule()
        {
            try (BufferedReader br = new BufferedReader(new FileReader("hastane.txt"))) {
                String satir;

                // Dosyadan satır okuma
                while ((satir = br.readLine()) != null) {
                    System.out.println(satir);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        private void randevuSaatleriBelirle(String TcNo) throws IOException {
            System.out.println("Randevu veriş saatlerinizi giriniz: ");
            int baslangic_saat, baslangic_dakika, bitis_saat, bitis_dakika;
            Scanner girdi = new Scanner(System.in);
            System.out.println("Başlangıç saatleri: ");
            baslangic_saat = girdi.nextInt();
            baslangic_dakika = girdi.nextInt();
            LocalTime baslangic = LocalTime.of(baslangic_saat, baslangic_dakika);
            System.out.println("Bitiş saatleri:");
            bitis_saat = girdi.nextInt();
            bitis_dakika = girdi.nextInt();
            LocalTime bitis = LocalTime.of(bitis_saat, bitis_dakika);

            String dosyaYolu = "doktor.txt";
            String randevuDosyaYolu = "randevuSaat.txt";

            try (BufferedReader br = new BufferedReader(new FileReader(dosyaYolu))) {
                String soyad = null;
                String ad = null;
                String satir;

                while ((satir = br.readLine()) != null) {

                    if (satir.contains(TcNo)) {

                        if (soyad != null && ad != null) {
                            // Randevu dosyasına yazma işlemi
                            FileWriter fw = new FileWriter(randevuDosyaYolu, true);
                            BufferedWriter writer = new BufferedWriter(fw);

                            writer.write("Ad: " + ad );
                            writer.write(" Soyad: " + soyad + "\n");
                            writer.write("Saatler: " + baslangic_saat + ":" + baslangic_dakika + " - " + bitis_saat + ":" + bitis_dakika + "\n");

                            writer.write("\n-------------------------\n");
                            writer.close();
                        } else {
                            System.out.println("Önceki satır bilgileri eksik.");
                        }
                        break;
                    }

                    // Satırları kaydederken eski değerleri güncelle
                    ad = soyad;
                    soyad = satir;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void ilacYaz() throws IOException {
            Scanner girdi=new Scanner(System.in);
            String hastaTc,ilac;
            System.out.println("ilac yazacagın hastanin tc kimlik numarasi: ");
            hastaTc=girdi.next();
            System.out.println("yazacaginiz ilaci giriniz: ");
            ilac=girdi.next();

            FileWriter fw = new FileWriter("ilaclar.txt", true);
            BufferedWriter writer = new BufferedWriter(fw);
            writer.write(hastaTc + " : ");
            writer.write(ilac + "\n");
            writer.close();


        }


        public boolean dosyadaArama(String dosyaYolu, String arananIfade, String arananIfade2) throws IOException {
            try (BufferedReader br = new BufferedReader(new FileReader(dosyaYolu))) {
                List<String> satirlar = new ArrayList<>();
                String satir;

                while ((satir = br.readLine()) != null) {
                    satirlar.add(satir);
                }

                for (int i = 0; i < satirlar.size(); i++) {
                    String currentLine = satirlar.get(i);

                    if (currentLine.contains(arananIfade) && i > 0) {
                        // Eğer arananIfade bulunduysa ve bir önceki satır varsa
                        String oncekiSatir = satirlar.get(i - 1);

                        if (oncekiSatir.contains(arananIfade2)) {
                            // Eğer arananIfade2 de bulunduysa, eşleşme var
                            return true;
                        }
                    }
                }
            }
            return false;
        }



        public void profilGoruntule(String sifre) throws IOException {
            try (BufferedReader br = new BufferedReader(new FileReader("doktor.txt"))) {
                String satir;
                int sayac = 0;

                List<String> satirlar = new ArrayList<>();
                while ((satir = br.readLine()) != null) {
                    satirlar.add(satir);
                    sayac++;

                    if (satir.contains(sifre)) {
                        // Bulunan satırdan önceki 4 satırı yazdır
                        for (int i = Math.min(sayac - 1, 3); i >= 0; i--) {
                            System.out.println(satirlar.get(sayac - i - 1));
                        }
                        return; // Sadece önceki 4 satırı yazdırdık, bu yüzden geri dönebiliriz.
                    }
                }
                System.out.println("Doktor bulunamadı.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }




