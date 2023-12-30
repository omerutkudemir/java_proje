import model.Doctor;
import model.Patient;
import service.DoctorService;
import service.PatientService;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        PatientService hasta = new PatientService();
        DoctorService doktor = new DoctorService();

        int giris;
        int hastaneIslem;

        Scanner girdi = new Scanner(System.in);

        System.out.println("****Hastane sistemine hosgeldiniz****");
        System.out.println("Doktor girişi için 1, hasta girişi için 2'ye basınız");

        giris = girdi.nextInt();

        if (giris == 1) {
            System.out.println("Sisteme kaydolmak için 1, giriş yapmak için 2:");
            hastaneIslem = girdi.nextInt();
            if (hastaneIslem == 1) {
                doktor.uyeOl();
            } else if (hastaneIslem == 2) {
                doktor.girisYap();
            }
        }

        if (giris == 2) {
            System.out.println("Sisteme kaydolmak için 1, giriş yapmak için 2:");
            hastaneIslem = girdi.nextInt();
            if (hastaneIslem == 1) {
                hasta.uyeOl();
            } else if (hastaneIslem == 2) {
                hasta.girisYap();
            }
        }
    }
}
