package Data;
import java.io.IOException;

/**
 * Interfejs menad¿era zapisu i odczytu obiektów z ró¿nego rodzaju plików.
 */
public interface FileManagerInterface {

	/**
	 * Zapisuje obiekt do pliku podanego w œcie¿ce.
	 * @param object obiekt do zapisu
	 * @param path œcie¿ka zapisu pliku wraz z nazw¹ i rozszerzeniem
	 * @throws IOException wyj¹tek nieprawid³owej operacji wejœcia wyjœcia
	 */
	abstract void saveToFile(Object object, String path) throws IOException;
	
	/**
	 * Odczytuje obiekt z pliku podanego w œcie¿ce i zwraca go.
	 * @param path œcie¿ka do pliku wraz z nazw¹ i rozszerzeniem
	 * @return odczytany obiekt
	 * @throws IOException wyj¹tek nieprawid³owej operacji wejœcia wyjœcia
	 * @throws ClassNotFoundException wyj¹tek niepoprawnej klasy
	 */ 
	abstract Object readFromFile(String path) throws IOException, ClassNotFoundException;
	
}
