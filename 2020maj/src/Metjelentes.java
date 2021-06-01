import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;
import java.util.stream.Collectors;

public final class Metjelentes {

    private static final List<Tavirat> TAVIRATOK = new ArrayList<>();

    public static void main(String[] args) {
        feladat1();
        feladat2();
        feladat3();
        feladat4();
        feladat5_6();
    }

    //Adatok eltárolása
    private static void feladat1() {
        try (BufferedReader reader = new BufferedReader(new FileReader("tavirathu13.txt"))) {
            String line;
            while ((line = reader.readLine()) != null)
                TAVIRATOK.add(new Tavirat(line.split(" ")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Utolsó mérési adat kiíratása
    private static void feladat2() {
        Scanner scanner = new Scanner(System.in);
        System.out.printf("2. feladat\nAdja meg egy település kódját! Település: ");
        String input = scanner.next("^[a-zA-Z]{2}$").toUpperCase(Locale.ROOT);
        scanner.close();

        Optional<Tavirat> t = TAVIRATOK
                .stream()
                .filter(tav -> tav.getKod().equals(input))
                .reduce((t1, t2) -> {
                    if (t2.getIdopont().compareTo(t1.getIdopont()) >= 0) return t2;
                    return t1;
                });

        t.ifPresent(tavirat -> System.out.printf("Az utolsó mérési adat a megadott településről %s-kor érkezett.%n%n",
                tavirat.getIdopont()));
    }

    //Legalacsonyabb, legmagasabb hőmérséklet
    private static void feladat3() {
        Tavirat min, max;
        TAVIRATOK.sort(Comparator.comparing(Tavirat::getHomerseklet));
        min = TAVIRATOK.get(0);
        max = TAVIRATOK.get(TAVIRATOK.size() - 1);

        System.out.printf("3. feladat\n" +
                        "A legalacsonyabb hőmérséklet: %s %s %s fok.\nA legmagasabb hőmérséklet: %s %s %s fok.%n%n",
                min.getKod(), min.getIdopont(), min.getHomerseklet(),
                max.getKod(), max.getIdopont(), max.getHomerseklet()
        );
    }

    //Szélcsendes időpontok meghatározása
    private static void feladat4() {
        List<Tavirat> t =
                TAVIRATOK
                        .stream()
                        .filter(tavirat -> tavirat.getSzelIrany().equals("000") && tavirat.getSzelErosseg() == 0)
                        .collect(Collectors.toList());

        System.out.println("4.feladat");
        if (t.size() == 0) System.out.println("Nem volt szélcsend a mérések idején.\n");
        else {
            for (Tavirat t1 : t) {
                System.out.println(t1.getKod() + " " + t1.getIdopont());
            }
            System.out.println();
        }
    }

    //Középhőmérsékletek, hőingadozások meghatározása +
    private static void feladat5_6() {
        System.out.println("5.feladat");

        //Települések kódjai
        List<String> kodok = TAVIRATOK
                .stream()
                .map(Tavirat::getKod)
                .distinct()
                .collect(Collectors.toList());

        //A megfigyelt időpontok
        List<Integer> idopontok = List.of(1, 7, 13, 19);

        for (String kod : kodok) {
            //A településhez tartozó bejegyzések
            List<Tavirat> bejegyzesek = TAVIRATOK
                    .stream()
                    .filter(tavirat -> tavirat.getKod().equals(kod))
                    .collect(Collectors.toList());

            //Volt-e olyan időpont, ahol nem történt mérés?
            boolean voltHianyzoBejegyzes = false;
            for (Integer i : idopontok) {
                if (bejegyzesek.stream().noneMatch(tavirat -> i == tavirat.getOra())) voltHianyzoBejegyzes = true;
            }

            //A megfigyelt időpontokhoz tartozó hőmérsékletek
            int[] homersekletek = bejegyzesek
                    .stream()
                    .filter(tavirat -> idopontok.contains(tavirat.getOra()))
                    .mapToInt(Tavirat::getHomerseklet)
                    .toArray();

            System.out.printf(kod + " %s; Hőmérséklet-ingadozás: %s\n",
                    voltHianyzoBejegyzes ?
                            "NA" : "Középhőmérséklet: " +
                            Math.round(Arrays.stream(homersekletek).sum() / (double) homersekletek.length),
                    Math.abs(bejegyzesek.get(0).getHomerseklet() - bejegyzesek.get(bejegyzesek.size() - 1).getHomerseklet())
            );

            //Szélerősségek kiíratása fájlba
            try (PrintStream ps = new PrintStream(kod + ".txt")) {
                ps.println(kod);
                bejegyzesek.sort(Comparator.comparing(Tavirat::getIdopont));

                for (Tavirat t : bejegyzesek) {
                    ps.print(t.getIdopont() + " ");
                    for (int i = 1; i <= t.getSzelErosseg(); i++) ps.print("#");
                    ps.print("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("\n6. feladat\nA fájlok elkészültek.");
    }
}
