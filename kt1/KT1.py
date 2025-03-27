class Liige:
    def __init__(self, nimi, vanus, kestus):
        self.nimi = nimi
        self.vanus = vanus
        self.kestus = kestus
    
    def get_teenused(self):
        raise NotImplementedError("Meetod peab olema ülekirjutatud alamaklassis")
    
    def __str__(self):
        return f"{self.nimi}, {self.vanus}, {self.kestus} kuud, {self.get_teenused()}"
    
    def __lt__(self, other):
        return self.kestus < other.kestus

class Tavaliige(Liige):
    def __init__(self, nimi, vanus, kestus, teenus):
        super().__init__(nimi, vanus, kestus)
        self.teenus = teenus
    
    def get_teenused(self):
        return self.teenus
    
    def __str__(self):
        return f"Tavaliige: {super().__str__()}"

class Preemiumliige(Liige):
    def __init__(self, nimi, vanus, kestus, teenused):
        super().__init__(nimi, vanus, kestus)
        self.teenused = teenused
    
    def get_teenused(self):
        return ", ".join(self.teenused)
    
    def __str__(self):
        return f"Preemiumliige: {super().__str__()}"

class Spordiklubi:
    def __init__(self, nimi):
        self.nimi = nimi
        self.liikmed = []
    
    def lisa_liige(self, liige):
        self.liikmed.append(liige)
    
    def pikaajalised_liikmed(self):
        return sorted([liige for liige in self.liikmed if liige.kestus >= 12], key=lambda x: x.kestus, reverse=True)
    
    def __str__(self):
        return f"Spordiklubi {self.nimi} - {len(self.liikmed)} liiget"

def loe_liikmed_failist(failinimi):
    liikmed = []
    try:
        with open(failinimi, "r", encoding="utf-8") as f:
            for rida in f:
                andmed = rida.strip().split(";")
                liikme_tüüp, nimi, vanus, kestus, teenused = andmed[0], andmed[1], int(andmed[2]), int(andmed[3]), andmed[4]
                if liikme_tüüp == "Tavaliige":
                    liikmed.append(Tavaliige(nimi, vanus, kestus, teenused))
                elif liikme_tüüp == "Preemiumliige":
                    liikmed.append(Preemiumliige(nimi, vanus, kestus, teenused.split(",")))
    except Exception as e:
        print(f"Viga faili lugemisel: {e}")
    return liikmed

if __name__ == "__main__":
    spordiklubi = Spordiklubi("MyFitness")
    liikmed = loe_liikmed_failist("liikmed.txt")
    for liige in liikmed:
        spordiklubi.lisa_liige(liige)
    
    print(spordiklubi)
    print("\nKõik liikmed:")
    for liige in spordiklubi.liikmed:
        print(liige)
    
    print("\nPikaajalised liikmed:")
    for liige in spordiklubi.pikaajalised_liikmed():
        print(liige)
