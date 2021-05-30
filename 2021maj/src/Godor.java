public class Godor {

    public static final int SZELESSEG = 10;

    private final int start;
    private final int end;
    private final int maxMelyseg;
    private final byte[] melysegek;

    public Godor(int start, int end) {
        this.start = start;
        this.end = end;
        melysegek = GodorAdat.getGodorMelysegek(this);
        maxMelyseg = max(this);
    }

    public static int vizMennyiseg(Godor g) {
        int vizmennyiseg = 0;
        for (byte b : g.melysegek) {
            if (b >= 1) {
                vizmennyiseg += 10 * (b - 1);
            }
        }
        return vizmennyiseg;
    }

    public static int terfogat(Godor g) {
        int terfogat = 0;
        for (byte b : g.melysegek) {
            terfogat += b * SZELESSEG;
        }
        return terfogat;
    }

    public static boolean folyamatosanMelyul(Godor g) {

        if (g.melysegek.length <= 2) return true;

        for (int i = 1; i <= g.melysegek.length && g.melysegek[i] != g.maxMelyseg; i++) {
            if (g.melysegek[i] > g.melysegek[i - 1]) return false;
        }

        for (int i = g.melysegek.length - 2; i >= 0 && g.melysegek[i] != g.maxMelyseg; i--) {
            if (g.melysegek[i] > g.melysegek[i + 1]) return false;
        }

        return true;
    }

    private static int max(Godor g) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < g.melysegek.length; i++) {
            if (g.melysegek[i] > max) {
                max = g.melysegek[i];
            }
        }
        return max;
    }

    public int getMaxMelyseg() {
        return maxMelyseg;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

}
