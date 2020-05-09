package Data;
import java.io.IOException;

public interface FileManagerInterface {

	abstract void saveToFile(Object object, String path) throws IOException;
	
	abstract Object readFromFile(String path) throws IOException, ClassNotFoundException;
	
}
