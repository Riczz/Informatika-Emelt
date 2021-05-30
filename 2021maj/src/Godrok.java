import java.io.*;
import java.util.Scanner;

public final class Godrok {

    private static final GodorAdat GODOR_ADAT = new GodorAdat(2000);
    private static int tavolsag;

    public static void main(String[] args) {
        feladat1();
        feladat2();
        feladat3();
        feladat4_5();
        feladat6();
    }

    //Fájl tartalmának beolvasása, gödrök számának kiírása
    private static void feladat1() {
        try (LineNumberReader inputStream = new LineNumberReader(new FileReader("melyseg.txt"))) {
            String line;
            while ((line = inputStream.readLine()) != null) {
                GODOR_ADAT.addMelyseg(Byte.parseByte(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.printf("1. feladat\nA fájl adatainak száma: %d%n%n", GODOR_ADAT.getCount());
    }

    //Távolságérték beolvasása
    private static void feladat2() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("2. feladat\nAdjon meg egy távolságértéket! ");

        tavolsag = scanner.nextInt();
        scanner.close();
        System.out.printf("Ezen a helyen a felszín %d méter mélyen van.%n%n", GODOR_ADAT.getMelyseg(tavolsag - 1));
    }

    //Érintetlen területek aránya
    private static void feladat3() {
        int erintetlen = 0;
        for (int i = 0; i < GODOR_ADAT.getCount(); i++) {
            if (GODOR_ADAT.getMelyseg(i) <= 0) ++erintetlen;
        }

        System.out.printf("3. feladat\nAz érintetlen terület aránya %.2f%n%n",
                (erintetlen / (double) GODOR_ADAT.getCount()) * 100f);
    }

    //Gödrök kiíratása, gödrök száma
    private static void feladat4_5() {
        int godrokSzama = 0;
        try (PrintStream ps = new PrintStream("godrok.txt")) {

            int start = 0;
            byte melyseg;
            StringBuilder currGodor = new StringBuilder();

            for (int i = 1; i <= GODOR_ADAT.getCount(); i++) {
                if ((melyseg = GODOR_ADAT.getMelyseg(i - 1)) != 0) {
                    currGodor.append(melyseg).append(" ");
                } else {
                    //Gödör hozzáadása listához
                    if (currGodor.isEmpty()) {
                        //Kezdeti pozíció = az első nem 0 szám
                        start = i + 1;
                        continue;
                    }
                    GODOR_ADAT.addGodor(new Godor(start, i - 1));

                    //Gödör kiíratása
                    ps.println(currGodor.toString().trim());
                    currGodor.delete(0, currGodor.length());
                    ++godrokSzama;

                    //A kiíratás után csökkenteni kell az indexet, hogy
                    //az adott elem (ami már nem 0) is hozzáadódjon a
                    //következő gödör mélységeihez a következő iterációban.
                    --i;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            System.out.printf("5. feladat\nA gödrök száma: %d%n%n", godrokSzama);
        }
    }

    //Gödörhöz tartozó adatok kiíratása
    private static void feladat6() {
        System.out.println("6. feladat");
        Godor godor = GODOR_ADAT.getGodor(tavolsag);
        if (godor == null) {
            System.out.println("Az adott helyen nincs gödör.");
            return;
        }

        System.out.printf("a) A gödör kezdete: %d méter, a gödör vége: %d méter.%n", godor.getStart(), godor.getEnd());
        System.out.println("b) " + (Godor.folyamatosanMelyul(godor) ? "Folyamatosan mélyül." : "Nem mélyül folyamatosan."));
        System.out.printf("c) A legnagyobb mélyésge: %d méter.%n", godor.getMaxMelyseg());
        System.out.printf("d) A térfogata %d m^3.%n", Godor.terfogat(godor));
        System.out.printf("e) A vízmennyiség %d m^3.", Godor.vizMennyiseg(godor));
    }
}
