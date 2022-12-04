package tehtavia;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Perinto {

	private static int potti;
	private static int kuollutID;
	private static List<Perija> perijat = new ArrayList<>();
	
	public static void main(String[] args) {
		while (true) {
		    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		    
			String perinto = "";
			
		    try {
				perinto = br.readLine();
			} catch (IOException ioe) {
				System.out.println(ioe);
			}
		    if (perinto == null || perinto.equals("")) break;
		    
		    String[] numerot = perinto.split(" ");
		    kuollutID = Integer.parseInt(numerot[0]);
		    potti = Integer.parseInt(numerot[1]);
			
		    while (true) {
				try {
					String perija = br.readLine();
					if (perija.equals("0")) break;
					String[] osat = perija.split(" ");
					Perija uusi = new Perija(Integer.parseInt(osat[0]), osat[1], Integer.parseInt(osat[2]), Integer.parseInt(osat[3]));
					perijat.add(uusi);
				} catch (IOException ioe) {
					System.out.println(ioe);
				}
			}
			
			laskePerinto();
		}
	}
	
	public static void laskePerinto() {
		if (perijat.size() == 0) {
			System.out.println("Valtio saa " + potti);
			System.out.println("Jakamatta jää 0");
			return;
		}
		Perija kuollut = new Perija(0, "", 0, 0);
		for (Perija p: perijat) { if (Math.abs(p.id) == kuollutID) kuollut = p;}
		kuollut.tulot = potti;
		kuollut.tulot = kuollut.laskePerinto(perijat, 0);
		boolean oliko = false;
		for (Perija p: perijat) {
			if (p.tulot != 0 && Math.abs(p.id) != kuollutID) {
				System.out.println(p.nimi + " saa " + p.tulot);
				oliko = true;
			}
		}
		if (!oliko) {
			System.out.println("Valtio saa " + potti);
			System.out.println("Jakamatta jää 0");
			return;
		}
		System.out.println("Jakamatta jää " + kuollut.tulot);
	}
}

class Perija {
	int id;
	String nimi;
	int vanhempi1;
	int vanhempi2;
	int tulot = 0;
	
	Perija(int id, String nimi, int vanhempi1, int vanhempi2) {
		this.id = id;
		this.nimi = nimi;
		this.vanhempi1 = vanhempi1;
		this.vanhempi2 = vanhempi2;
	}
	
	public void kerroPerima() {
		System.out.println(this.nimi + " saa " + tulot);
	}
	
	public int laskePerinto(List<Perija> kaikki, int iter) {
		List<Perija> perijat = new ArrayList<>();
		for (Perija p: kaikki) {
			if (p.vanhempi1 == Math.abs(this.id) || p.vanhempi2 == Math.abs(this.id))
				perijat.add(p);
			if (iter == 0 && (this.vanhempi1 == Math.abs(p.id) || this.vanhempi2 == Math.abs(p.id)))
				perijat.add(p);
		}
		
		int ekstra = 0;
		int vahempi = 0;
		for (Perija p: perijat) {
			int apu = 0;
			if (p.id < 0) apu = p.laskePerinto(kaikki, iter + 1);
			if (apu < 0) vahempi++;
			else ekstra += apu;
		}
		
		for (Perija p: perijat) {
			if (p.id >= 0) p.tulot = this.tulot / (perijat.size() - vahempi);
		}
		
		int apu = tulot;
		tulot = 0;
		if (perijat.size() == 0) return -1;
		ekstra += apu%(perijat.size() - vahempi);

		return ekstra;
	}
}
