package Data;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Klasa umo¿liwiaj¹ca obs³ugê strumieni obiektowych, zapis i odczyt obiektów z plików.
 * 
 * @author Adam Kalisz
 * @author Kamil Rojszczak
 */
public class ObjectFileManager implements FileManagerInterface {
	
	/**
	 * Zapisuje obiekt do pliku podanego w œcie¿ce za pomoc¹ strumieni obiektowych.
	 * @param obj obiekt do zapisu
	 * @param path œcie¿ka zapisu pliku wraz z nazw¹ i rozszerzeniem
	 * @throws IOException wyj¹tek nieprawid³owej operacji wejœcia wyjœcia
	 */
	@Override
	public void saveToFile(Object obj, String path) throws IOException {
		ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(path));
		os.writeObject(obj);
		os.close();
	}
	
	/**
	 * Odczytuje obiekt z pliku podanego w œcie¿ce za pomoc¹ strumieni obiektowych.
	 * @param path œcie¿ka do pliku wraz z nazw¹ i rozszerzeniem
	 * @return odczytany obiekt
	 * @throws IOException wyj¹tek nieprawid³owej operacji wejœcia wyjœcia
	 * @throws ClassNotFoundException wyj¹tek niepoprawnej klasy
	 */
	@Override
	public Object readFromFile(String path) throws IOException, ClassNotFoundException {
		ObjectInputStream is = new ObjectInputStream(new FileInputStream(path));
		Object obj = is.readObject();
		is.close();
		return obj;
	}
	
}
