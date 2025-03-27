import java.io.*;
import java.nio.file.*;
import java.util.*;

abstract class Liige implements Comparable<Liige> {
    protected String nimi;
    protected int vanus;
    protected int kestus;
    
    public Liige(String nimi, int vanus, int kestus) {
        this.nimi = nimi;
        this.vanus = vanus;
        this.kestus = kestus;
    }

    public abstract String teenused();
    
    @Override
    public int compareTo(Liige other) {
        return Integer.compare(this.kestus, other.kestus);
    }
}

class Tavaliige extends Liige {
    private String teenus;
    
    public Tavaliige(String nimi, int vanus, int kestus, String teenus) {
        super(nimi, vanus, kestus);
        this.teenus = teenus;
    }
    
    @Override
    public String teenused() {
        return "Tavaliige teenus: " + teenus;
    }
}

class Preemiumliige extends Liige {
    private List<String> teenused;
    
    public Preemiumliige(String nimi, int vanus, int kestus, List<String> teenused) {
        super(nimi, vanus, kestus);
        this.teenused = teenused;
    }
    
    @Override
    public String teenused() {
        return "Preemiumliige teenusteks on: " + String.join(", ", teenused);
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
    
    public void kuvaLiikmed() {
        for (Liige liige : liikmed) {
            System.out.println(liige.nimi + " - " + liige.teenused());
        }
    }
}

public class Peaklass {
    public static List<Liige> loeLiikmed(String failiNimi) throws IOException {
        List<Liige> liikmed = new ArrayList<>();
        List<String> read = Files.readAllLines(Paths.get(failiNimi));
        for (String rida : read) {
            String[] osad = rida.split(";");
            String liikmeTüüp = osad[0];
            String nimi = osad[1];
            int vanus = Integer.parseInt(osad[2]);
            int kestus = Integer.parseInt(osad[3]);
            if (liikmeTüüp.equals("Tavaliige")) {
                liikmed.add(new Tavaliige(nimi, vanus, kestus, osad[4]));
            } else if (liikmeTüüp.equals("Preemiumliige")) {
                List<String> teenused = Arrays.asList(osad[4].split(","));
                liikmed.add(new Preemiumliige(nimi, vanus, kestus, teenused));
            }
        }
        return liikmed;
    }
    
    public static void main(String[] args) {
        try {
            List<Liige> liikmed = loeLiikmed("liikmed.txt");
            Spordiklubi klubi = new Spordiklubi("Spordiklubi X");
            for (Liige liige : liikmed) {
                klubi.lisaLiige(liige);
            }
            System.out.println("Kõik liikmed:");
            klubi.kuvaLiikmed();
            System.out.println("\nPikaajalised liikmed:");
            for (Liige liige : klubi.pikaajalisedLiikmed()) {
                System.out.println(liige.nimi);
            }
        } catch (IOException e) {
            System.err.println("Viga faili lugemisel: " + e.getMessage());
        }
    }
}