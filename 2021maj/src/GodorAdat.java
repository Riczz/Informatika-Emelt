import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class GodorAdat {

    private static byte[] melyseg;      //Mélységeket tároló tömb
    private final List<Godor> godrok;   //Gödrök száma
    private int count;                  //Mélység Adatok száma

    public GodorAdat(int maxSize) {
        melyseg = new byte[maxSize];
        godrok = new ArrayList<>();
        count = 0;
    }

    public static byte[] getGodorMelysegek(Godor g) {
        byte[] godorMelyseg = new byte[g.getEnd()-g.getStart()+1];
        System.arraycopy(melyseg,g.getStart()-1,godorMelyseg,0,godorMelyseg.length);
        return godorMelyseg;
    }

    public Godor getGodor(int tavolsag) {
        return godrok
                .stream()
                .filter(g -> g.getStart() <= tavolsag && g.getEnd() >= tavolsag)
                .findFirst()
                .orElse(null);
    }

    public void addGodor(Godor g) {
        godrok.add(g);
    }

    public byte getMelyseg(int index) {
        if (index < 0 || index >= count) throw new IndexOutOfBoundsException();
        return melyseg[index];
    }

    public void addMelyseg(byte val) {
        melyseg[count++] = val;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return Arrays.toString(melyseg);
    }
}
