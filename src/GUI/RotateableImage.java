package GUI;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Klasa odpowiada za transformacje obrazu wska�nik�w tablicy rozdzielczej.
 * 
 * @version 1.0
 * @author Adam Kalisz
 * @author Kamil Rojszczak
 * 
 */
public class RotateableImage {

	private BufferedImage img;
	private AffineTransform at;
	private float minVal, maxVal;
	private int deg, maxDeg;
	private int translateX, translateY, centerX, centerY;
	
	/**
	 * Konstruktor pobiera obraz z podanej �cie�ki, warto�ci do jakich zostanie przeskalowany oraz k�t o jaki mo�e si� obr�ci�.
	 * 
	 * @param path �cie�ka do pliku z obraze
	 * @param minVal minimalna warto��
	 * @param maxVal maksymana warto��
	 * @param maxDeg maksymany k�t o jaki obraz mo�e si� obr�ci�
	 */
	public RotateableImage(String path, int minVal, int maxVal, int maxDeg) {
		try {
			img = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.minVal = minVal;
		this.maxVal = maxVal;
		this.translateX = 0;
		this.translateY = 0;
		this.centerX = 0;
		this.centerY = 0;
		this.maxDeg = maxDeg;
		setTranslation(this.translateX, this.translateY);
	}
	
	/**
	 * Przekszta�cenie afiniczne.
	 * @param translateX odleg�o�� o jak� obraz zostaje przesuni�ty wzd�u� osi X
	 * @param translateY odleg�o�� o jak� obraz zostaje przesuni�ty wzd�u� osi Y
	 */
	public void setTranslation(int translateX, int translateY) {
		this.translateX = translateX;
		this.translateY = translateY;
		at = AffineTransform.getTranslateInstance(translateX, translateY);
	}
	
	/**
	 * Ustawienie �rodka wok� kt�rego obraz b�dzie si� obra�
	 * @param centerX warto�� x jako int
	 * @param centerY warto�� y jako int
	 */
	public void setCenter(int centerX, int centerY) {
		this.centerX = centerX;
		this.centerY = centerY;
	}
	
	/**
	 * Obraca obraz o podany k�t.
	 * @param deg k�t o jaki obraz ma si� obr�ci�
	 */
	public void rotate(double deg) {
		if(this.deg + deg <= maxDeg && this.deg + deg >= 0) {
			at.rotate(Math.toRadians(deg), centerX, centerY);
		}
	}
	
	/**
	 * Ustawia warto�� z zakresu podanego przy wywo�aniu kosntruktora.
	 * Je�eli docelowe warto�ci przekraczaj� mo�liwy zakres obraz sie nie obr�ci.
	 * @param newVal nowa warto�� jak� ma wskazywa� wskaz�wka
	 */
	public void setVal(float newVal) {
		if(newVal > maxVal) rotate(maxDeg);
		else if(newVal < minVal) rotate(0);
		else rotate(((newVal) * maxDeg)/ (maxVal + minVal));
	}

	/**
	 * Zwraca obiekt obrazu.
	 * @return obiekt obrazu
	 */
	public BufferedImage getImg() {
		return img;
	}

	/**
	 * Zwraca obiekt at umo�liwaj�cy transformacj� obrazu.
	 * @return obiekt klasy AffineTransform umo�liwiaj�cy transformacj�
	 */
	public AffineTransform getAt() {
		return at;
	}
	
	
	
}
