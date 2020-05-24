package Data;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.thoughtworks.xstream.XStream;

/**
 * Klasa umo�liwiaj�ca obs�ug� zapisu i odczytu obiekt�w z plik�w XML.
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
	 * Zapisuje obiekt do pliku podanego w �cie�ce za pomoc� biblioteki XStream.
	 * @param obj obiekt do zapisu
	 * @param path �cie�ka zapisu pliku wraz z nazw� i rozszerzeniem
	 * @throws IOException wyj�tek nieprawid�owej operacji wej�cia wyj�cia
	 */
	@Override
	public void saveToFile(Object obj, String path) throws IOException {
		FileWriter ostream = new FileWriter(new File(path));
		ostream.write(this.declaration + xs.toXML(obj));
		ostream.close();
	}
	
	/**
	 * Odczytuje obiekt z pliku XML podanego w �cie�ce.
	 * @param path �cie�ka do pliku wraz z nazw� i rozszerzeniem
	 * @return odczytany obiekt
	 * @throws IOException wyj�tek nieprawid�owej operacji wej�cia wyj�cia
	 * @throws ClassNotFoundException wyj�tek niepoprawnej klasy
	 */
	@Override
	public Object readFromFile(String path) throws IOException, ClassNotFoundException {
		Class<?>[] classes = new Class[] { Data.Travel.class };
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
	 * Liczy ilo�� linii w podanym pliku XML
	 * @param path �cie�ka do pliku wraz z nazw� i rozszerzeniem
	 * @return ilo�� linii
	 * @throws IOException wyj�tek nieprawid�owej operacji wej�cia wyj�cia
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
	 * @param newName alias na kt�ry chcemy zmieni� nazw� klasy
	 * @param clas klasa dla kt�rej zmiana ma by� wprowadzona
	 */
	public void addClassAlias(String newName, Class<?> clas) {
		xs.alias(newName, clas);
	}
	
	/**
	 * Ustawia alias dla pola danej klasy.
	 * @param newName alias na kt�ry chcemy zmieni� pole danej klasy
	 * @param clas klasa dla kt�rej zmiana ma by� wprowadzona
	 * @param fieldName nazwa pola klasy dla kt�rego ustawiamy alias
	 */
	public void addFieldAlias(String newName, Class<?> clas, String fieldName) {
		xs.aliasField(newName, clas, fieldName);
	}

	/**
	 * Zwraca ustawion� dekralacj�
	 * @return deklaracja jako String
	 */
	public String getDeclaration() {
		return declaration;
	}

	/**
	 * Zmienia domy�ln� deklaracj�
	 * @param declaration nowa deklaracja
	 */
	public void setDeclaration(String declaration) {
		this.declaration = declaration;
	}
	
}
