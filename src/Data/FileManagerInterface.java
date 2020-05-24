package Data;
import java.io.IOException;

/**
 * Interfejs menad�era zapisu i odczytu obiekt�w z r�nego rodzaju plik�w.
 */
public interface FileManagerInterface {

	/**
	 * Zapisuje obiekt do pliku podanego w �cie�ce.
	 * @param object obiekt do zapisu
	 * @param path �cie�ka zapisu pliku wraz z nazw� i rozszerzeniem
	 * @throws IOException wyj�tek nieprawid�owej operacji wej�cia wyj�cia
	 */
	abstract void saveToFile(Object object, String path) throws IOException;
	
	/**
	 * Odczytuje obiekt z pliku podanego w �cie�ce i zwraca go.
	 * @param path �cie�ka do pliku wraz z nazw� i rozszerzeniem
	 * @return odczytany obiekt
	 * @throws IOException wyj�tek nieprawid�owej operacji wej�cia wyj�cia
	 * @throws ClassNotFoundException wyj�tek niepoprawnej klasy
	 */ 
	abstract Object readFromFile(String path) throws IOException, ClassNotFoundException;
	
}
