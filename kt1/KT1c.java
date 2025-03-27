import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

// Põhiklass Liige, mis esindab spordiklubi liiget
class Liige implements Comparable<Liige> {
    protected String nimi; // Liikme nimi
    protected int vanus; // Liikme vanus
    protected int kestus; // Kui kaua (kuudes) on liige klubisse kuulunud
    protected String teenused; // Teenused, mida liige kasutab

    // Konstruktor, et luua uus liige
    public Liige(String nimi, int vanus, int kestus, String teenused) {
        this.nimi = nimi;
        this.vanus = vanus;
        this.kestus = kestus;
        this.teenused = teenused;
    }

    // Liikme info tekstina
    public String toString() {
        return nimi + " (" + vanus + " a) - " + kestus + " kuud, Teenused: " + teenused;
    }

    // Meetod liikmete järjestamiseks kestuse (kuud) järgi
    public int compareTo(Liige other) {
        return Integer.compare(this.kestus, other.kestus);
    }
}

// Tavaliikme klass, mis pärib klassi Liige
class Tavaliige extends Liige {
    public Tavaliige(String nimi, int vanus, int kestus, String teenused) {
        super(nimi, vanus, kestus, teenused);
    }

    // Tavaliikme info tekstina
    public String toString() {
        return "Tavaliige: " + super.toString();
    }
}

// Preemiumliikme klass, mis pärib klassi Liige
class Preemiumliige extends Liige {
    public Preemiumliige(String nimi, int vanus, int kestus, String teenused) {
        super(nimi, vanus, kestus, teenused);
    }

    // Preemiumliikme info tekstina
    public String toString() {
        return "Preemiumliige: " + super.toString();
    }
}

// Spordiklubi klass, mis sisaldab liikmeid ja klubi nime
class Spordiklubi {
    private String nimi; // Spordiklubi nimi
    private List<Liige> liikmed; // Klubi liikmete nimekiri

    // Konstruktor, mis määrab spordiklubi nime ja loob tühja liikmete nimekirja
    public Spordiklubi(String nimi) {
        this.nimi = nimi;
        this.liikmed = new ArrayList<>();
    }

    // Meetod uue liikme lisamiseks klubisse
    public void lisaLiige(Liige liige) {
        liikmed.add(liige);
    }

    // Meetod pikaajaliste liikmete (vähemalt 12 kuud liikmeks olnud) leidmiseks
    public List<Liige> pikaajalisedLiikmed() {
        List<Liige> pikaajalised = new ArrayList<>();
        for (Liige liige : liikmed) {
            if (liige.kestus >= 12) {
                pikaajalised.add(liige);
            }
        }
        return pikaajalised;
    }

    // Spordiklubi info tekstina
    public String toString() {
        StringBuilder sb = new StringBuilder("Spordiklubi: " + nimi + "\n");
        for (Liige liige : liikmed) {
            sb.append(liige).append("\n");
        }
        return sb.toString();
    }
}

// Peaklass, mis käivitab programmi
public class Peaklass {
    public static void main(String[] args) {
        String url = "https://kodu.ut.ee/~marinai/liikmed.txt"; // Faili asukoht internetis
        Spordiklubi klubi = new Spordiklubi("Spordiklubi Peakas"); // Loome spordiklubi objekti

        try {
            // Loeme andmed failist URL-i kaudu
            BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
            String line;
            while ((line = reader.readLine()) != null) { // Loeme iga rea eraldi
                String[] parts = line.split(";"); // Jagame rea andmed semikooloni alusel
                if (parts.length < 5) continue; // Kontrollime, et kõik vajalikud andmed on olemas

                String liikmeTüüp = parts[0]; // Esimene väärtus - kas Tavaliige või Preemiumliige
                String nimi = parts[1]; // Teine väärtus - liikme nimi
                int vanus = Integer.parseInt(parts[2]); // Kolmas väärtus - liikme vanus
                int kestus = Integer.parseInt(parts[3]); // Neljas väärtus - liikmeks olemise kestus kuudes
                String teenused = parts[4]; // Viies väärtus - teenused, mida liige kasutab

                // Loome vastava tüübi objekti ja lisame selle klubisse
                if (liikmeTüüp.equals("Tavaliige")) {
                    klubi.lisaLiige(new Tavaliige(nimi, vanus, kestus, teenused));
                } else if (liikmeTüüp.equals("Preemiumliige")) {
                    klubi.lisaLiige(new Preemiumliige(nimi, vanus, kestus, teenused));
                }
            }
            reader.close(); // Sulgeme faili lugemise
        } catch (Exception e) {
            System.out.println("Viga andmete lugemisel: " + e.getMessage()); // Vea korral kuvame teate
        }

        // Kuvame kõik spordiklubi liikmed
        System.out.println(klubi);

        // Kuvame pikaajalised liikmed
        System.out.println("Pikaajalised liikmed:");
        for (Liige liige : klubi.pikaajalisedLiikmed()) {
            System.out.println(liige);
        }
    }
}
