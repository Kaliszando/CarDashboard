package Data;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.thoughtworks.xstream.XStream;

/**
 * Klasa umo¿liwiaj¹ca obs³ugê zapisu i odczytu obiektów z plików XML.
 * 
 * @author Adam Kalisz
 * @author Kamil Rojszczak
 */
public class XMLFileManager implements FileManagerInterface {

	private XStream xs;
	private String declaration = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
	
	/**
	 * Konstruktor, tworzy obiekt klasy XStream.
	 */
	public XMLFileManager() {
		this.xs = new XStream();
	}
	
	/**
	 * Zapisuje obiekt do pliku podanego w œcie¿ce za pomoc¹ biblioteki XStream.
	 * @param obj obiekt do zapisu
	 * @param path œcie¿ka zapisu pliku wraz z nazw¹ i rozszerzeniem
	 * @throws IOException wyj¹tek nieprawid³owej operacji wejœcia wyjœcia
	 */
	@Override
	public void saveToFile(Object obj, String path) throws IOException {
		FileWriter ostream = new FileWriter(new File(path));
		ostream.write(this.declaration + xs.toXML(obj));
		ostream.close();
	}
	
	/**
	 * Odczytuje obiekt z pliku XML podanego w œcie¿ce.
	 * @param path œcie¿ka do pliku wraz z nazw¹ i rozszerzeniem
	 * @return odczytany obiekt
	 * @throws IOException wyj¹tek nieprawid³owej operacji wejœcia wyjœcia
	 * @throws ClassNotFoundException wyj¹tek niepoprawnej klasy
	 */
	@Override
	public Object readFromFile(String path) throws IOException, ClassNotFoundException {
		Class<?>[] classes = new Class[] { Data.Travel.class, Data.CarSettings.class };
		XStream.setupDefaultSecurity(xs);
		xs.allowTypes(classes);
		
		BufferedReader istream = new BufferedReader(new FileReader(path));
		String xmlR = new String();
		
		for(int i = 0; i < this.countLines(path); i++) 
			xmlR += istream.readLine() + "\n";
		istream.close();
		return xs.fromXML(xmlR);
	}
	
	/**
	 * Liczy iloœæ linii w podanym pliku XML
	 * @param path œcie¿ka do pliku wraz z nazw¹ i rozszerzeniem
	 * @return iloœæ linii
	 * @throws IOException wyj¹tek nieprawid³owej operacji wejœcia wyjœcia
	 */
	public int countLines(String path) throws IOException {
		int counter = 0;
		BufferedReader istream = new BufferedReader(new FileReader(path));
		while (istream.readLine() != null)
			counter++;
		istream.close();
		return counter;
	}
	
	/**
	 * Ustawia alias dla danej klasy. 
	 * @param newName alias na który chcemy zmieniæ nazwê klasy
	 * @param clas klasa dla której zmiana ma byæ wprowadzona
	 */
	public void addClassAlias(String newName, Class<?> clas) {
		xs.alias(newName, clas);
	}
	
	/**
	 * Ustawia alias dla pola danej klasy.
	 * @param newName alias na który chcemy zmieniæ pole danej klasy
	 * @param clas klasa dla której zmiana ma byæ wprowadzona
	 * @param fieldName nazwa pola klasy dla którego ustawiamy alias
	 */
	public void addFieldAlias(String newName, Class<?> clas, String fieldName) {
		xs.aliasField(newName, clas, fieldName);
	}

	/**
	 * Zwraca ustawion¹ dekralacjê
	 * @return deklaracja jako String
	 */
	public String getDeclaration() {
		return declaration;
	}

	/**
	 * Zmienia domyœln¹ deklaracjê
	 * @param declaration nowa deklaracja
	 */
	public void setDeclaration(String declaration) {
		this.declaration = declaration;
	}
	
}
