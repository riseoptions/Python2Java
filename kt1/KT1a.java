import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

class Liige implements Comparable<Liige> {
    protected String nimi;
    protected int vanus;
    protected int kestus;
    protected String teenused;

    public Liige(String nimi, int vanus, int kestus, String teenused) {
        this.nimi = nimi;
        this.vanus = vanus;
        this.kestus = kestus;
        this.teenused = teenused;
    }

    public String toString() {
        return nimi + " (" + vanus + " a) - " + kestus + " kuud, Teenused: " + teenused;
    }

    public int compareTo(Liige other) {
        return Integer.compare(this.kestus, other.kestus);
    }
}

class Tavaliige extends Liige {
    public Tavaliige(String nimi, int vanus, int kestus, String teenused) {
        super(nimi, vanus, kestus, teenused);
    }

    public String toString() {
        return "Tavaliige: " + super.toString();
    }
}

class Preemiumliige extends Liige {
    public Preemiumliige(String nimi, int vanus, int kestus, String teenused) {
        super(nimi, vanus, kestus, teenused);
    }

    public String toString() {
        return "Preemiumliige: " + super.toString();
    }
}

class Spordiklubi {
    private String nimi;
    private List<Liige> liikmed;

    public Spordiklubi(String nimi) {
        this.nimi = nimi;
        this.liikmed = new ArrayList<>();
    }

    public void lisaLiige(Liige liige) {
        liikmed.add(liige);
    }

    public List<Liige> pikaajalisedLiikmed() {
        List<Liige> pikaajalised = new ArrayList<>();
        for (Liige liige : liikmed) {
            if (liige.kestus >= 12) {
                pikaajalised.add(liige);
            }
        }
        return pikaajalised;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Spordiklubi: " + nimi + "\n");
        for (Liige liige : liikmed) {
            sb.append(liige).append("\n");
        }
        return sb.toString();
    }
}

public class Peaklass {
    public static void main(String[] args) {
        String url = "https://kodu.ut.ee/~marinai/liikmed.txt";
        Spordiklubi klubi = new Spordiklubi("Spordiklubi Peakas");

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length < 5) continue;

                String liikmeTüüp = parts[0];
                String nimi = parts[1];
                int vanus = Integer.parseInt(parts[2]);
                int kestus = Integer.parseInt(parts[3]);
                String teenused = parts[4];

                if (liikmeTüüp.equals("Tavaliige")) {
                    klubi.lisaLiige(new Tavaliige(nimi, vanus, kestus, teenused));
                } else if (liikmeTüüp.equals("Preemiumliige")) {
                    klubi.lisaLiige(new Preemiumliige(nimi, vanus, kestus, teenused));
                }
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("Viga andmete lugemisel: " + e.getMessage());
        }

        // Kuvame kõik liikmed
        System.out.println(klubi);

        // Kuvame pikaajalised liikmed
        System.out.println("Pikaajalised liikmed:");
        for (Liige liige : klubi.pikaajalisedLiikmed()) {
            System.out.println(liige);
        }
    }
}
