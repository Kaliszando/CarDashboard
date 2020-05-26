package Data;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Klasa umo�liwiaj�ca obs�ug� strumieni obiektowych, zapis i odczyt obiekt�w z plik�w.
 * 
 * @author Adam Kalisz
 * @author Kamil Rojszczak
 */
public class ObjectFileManager implements FileManagerInterface {
	
	/**
	 * Zapisuje obiekt do pliku podanego w �cie�ce za pomoc� strumieni obiektowych.
	 * @param obj obiekt do zapisu
	 * @param path �cie�ka zapisu pliku wraz z nazw� i rozszerzeniem
	 * @throws IOException wyj�tek nieprawid�owej operacji wej�cia wyj�cia
	 */
	@Override
	public void saveToFile(Object obj, String path) throws IOException {
		ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(path));
		os.writeObject(obj);
		os.close();
	}
	
	/**
	 * Odczytuje obiekt z pliku podanego w �cie�ce za pomoc� strumieni obiektowych.
	 * @param path �cie�ka do pliku wraz z nazw� i rozszerzeniem
	 * @return odczytany obiekt
	 * @throws IOException wyj�tek nieprawid�owej operacji wej�cia wyj�cia
	 * @throws ClassNotFoundException wyj�tek niepoprawnej klasy
	 */
	@Override
	public Object readFromFile(String path) throws IOException, ClassNotFoundException {
		ObjectInputStream is = new ObjectInputStream(new FileInputStream(path));
		Object obj = is.readObject();
		is.close();
		return obj;
	}
	
}
