import java.util.TreeMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.Comparator;

public class Zad7 {
	public static void main(String[] args) {
		Osoba firstPerson = new Osoba("Jan", "Nowak", "Piotrkowska 43", "+42", "123456789");
		Osoba secondPerson = new Osoba("Adam", "Kowalski", "Tulipanowa 21", "+42", "987654321");
		Firma firstCompany = new Firma("TEAM-BUD", "Wojska Polskiego 232", "+44", "527936458");
		Firma secondCompany = new Firma("BUD-MAR", "Lewacka 12", "+42", "157965745");

		SortedMap<NrTelefoniczny, Wpis> dictionary = new TreeMap<NrTelefoniczny, Wpis>(new Comparator<NrTelefoniczny>() {
			public int compare(NrTelefoniczny n1, NrTelefoniczny n2) {
				return n1.compareTo(n2);
			}
		});
		
		dictionary.put(firstPerson.zwrocNumer(), firstPerson);
		dictionary.put(secondPerson.zwrocNumer(), secondPerson);
		dictionary.put(firstCompany.zwrocNumer(), firstCompany);
		dictionary.put(secondCompany.zwrocNumer(), secondCompany);

		for(Map.Entry<NrTelefoniczny, Wpis> entry : dictionary.entrySet()) {
			System.out.println(entry.getKey() + " => " + entry.getValue().opis());
		}
	}
}

class NrTelefoniczny implements Comparable<NrTelefoniczny> {
	private String nrKierunkowy;
	private String nrTelefonu;

	NrTelefoniczny(String areaCode, String phoneNumber) {
		nrKierunkowy = areaCode;
		nrTelefonu = phoneNumber;
	}

	@Override
	public String toString() {
		return nrKierunkowy+" "+nrTelefonu;
	}

	@Override
	public int compareTo(NrTelefoniczny o) {
		return this.toString().compareTo(o.toString());
	}
}

abstract class Wpis {
	public abstract String opis();
	public abstract NrTelefoniczny zwrocNumer();
}

class Osoba extends Wpis {
	private String imie;
	private String nazwisko;
	private String adres;
	private NrTelefoniczny numer;

	Osoba(String name, String lastName, String address, String areaCode, String phoneNumber) {
		imie = name;
		nazwisko = lastName;
		adres = address;
		numer = new NrTelefoniczny(areaCode, phoneNumber);
	}

	@Override
	public String opis() {
		return imie+" "+nazwisko+", "+adres+", "+numer.toString();
	}

	@Override
	public NrTelefoniczny zwrocNumer() {
		return numer;
	}
}

class Firma extends Wpis {
	private String nazwa;
	private String adres;
	private NrTelefoniczny numer;

	Firma(String companyName, String address, String areaCode, String phoneNumber) {
		nazwa = companyName;
		adres = address;
		numer = new NrTelefoniczny(areaCode, phoneNumber);
	}

	@Override
	public String opis() {
		return nazwa+", "+adres+", "+numer.toString();
	}

	@Override
	public NrTelefoniczny zwrocNumer() {
		return numer;
	}
}