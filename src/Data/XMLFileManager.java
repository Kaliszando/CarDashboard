package Data;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.thoughtworks.xstream.XStream;

import Controller.Travel;

public class XMLFileManager implements FileManagerInterface {

	private XStream xs;
	private String declaration = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
			+ "<?xml-stylesheet type=\"text/css\" href=\"arkusz_stylow.css\"?>\n"
			+ "<!-- przykład komentarza -->\n";
	
	public XMLFileManager() {
		this.xs = new XStream();
	}
	
	@Override
	public void saveToFile(Object obj, String path) throws IOException {
		FileWriter ostream = new FileWriter(new File(path));
		ostream.write(this.declaration + xs.toXML(obj));
		ostream.close();
	}
	
	@Override
	public Object readFromFile(String path) throws IOException, ClassNotFoundException {
		Class<?>[] classes = new Class[] { Travel.class };
		XStream.setupDefaultSecurity(xs);
		xs.allowTypes(classes);
		
		BufferedReader istream = new BufferedReader(new FileReader(path));
		String xmlR = new String();
		
		for(int i = 0; i < this.countLines(path); i++) 
			xmlR += istream.readLine() + "\n";
		istream.close();
		return xs.fromXML(xmlR);
	}
	
	public int countLines(String path) throws IOException {
		int counter = 0;
		BufferedReader istream = new BufferedReader(new FileReader(path));
		while (istream.readLine() != null)
			counter++;
		istream.close();
		return counter;
	}
	
	public void addClassAlias(String newName, Class<?> clas) {
		xs.alias(newName, clas);
	}
	
	public void addFieldAlias(String newName, Class<?> clas, String fieldName) {
		xs.aliasField(newName, clas, fieldName);
	}

	public String getDeclaration() {
		return declaration;
	}

	public void setDeclaration(String declaration) {
		this.declaration = declaration;
	}
	
}
