package Data;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectFileManager implements FileManagerInterface {
	
	@Override
	public void saveToFile(Object obj, String path) throws IOException {
		ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(path));
		os.writeObject(obj);
		os.close();
	}
	
	@Override
	public Object readFromFile(String path) throws IOException, ClassNotFoundException {
		ObjectInputStream is = new ObjectInputStream(new FileInputStream(path));
		Object obj = is.readObject();
		is.close();
		return obj;
	}
	
}
