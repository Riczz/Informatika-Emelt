import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public final class Tavirat {


    private final char[] kod;
    private final int szelErosseg, homerseklet;
    private final String szelIrany;
    private final LocalTime idopont;

    private Tavirat(String kod, String ido, String iranyErosseg, String homerseklet) {
        this.kod = kod.toCharArray();
        this.idopont = LocalTime.parse(ido, DateTimeFormatter.ofPattern("HHmm", Locale.ROOT));
        this.szelIrany = iranyErosseg.substring(0, 3);
        this.szelErosseg = Integer.parseInt(iranyErosseg.substring(3, 5));
        this.homerseklet = Math.max(Integer.parseInt(homerseklet), 0);
    }

    public Tavirat(String[] params) {
        this(params[0], params[1], params[2], params[3]);
    }

    public String getKod() {
        return String.valueOf(kod);
    }

    public String getSzelIrany() {
        return szelIrany;
    }

    public int getSzelErosseg() {
        return szelErosseg;
    }

    public int getHomerseklet() {
        return homerseklet;
    }

    public int getOra() {
        return idopont.getHour();
    }

    public String getIdopont() {
        return idopont.format(DateTimeFormatter.ofPattern("HH:mm"));
    }
}
