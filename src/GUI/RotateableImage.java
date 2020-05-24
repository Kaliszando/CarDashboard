package GUI;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Klasa odpowiada za transformacje obrazu wskaŸników tablicy rozdzielczej.
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
	 * Konstruktor pobiera obraz z podanej œcie¿ki, wartoœci do jakich zostanie przeskalowany oraz k¹t o jaki mo¿e siê obróciæ.
	 * 
	 * @param path œcie¿ka do pliku z obraze
	 * @param minVal minimalna wartoœæ
	 * @param maxVal maksymana wartoœæ
	 * @param maxDeg maksymany k¹t o jaki obraz mo¿e siê obróciæ
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
	 * Przekszta³cenie afiniczne.
	 * @param translateX odleg³oœæ o jak¹ obraz zostaje przesuniêty wzd³u¿ osi X
	 * @param translateY odleg³oœæ o jak¹ obraz zostaje przesuniêty wzd³u¿ osi Y
	 */
	public void setTranslation(int translateX, int translateY) {
		this.translateX = translateX;
		this.translateY = translateY;
		at = AffineTransform.getTranslateInstance(translateX, translateY);
	}
	
	/**
	 * Ustawienie œrodka wokó³ którego obraz bêdzie siê obraæ
	 * @param centerX wartoœæ x jako int
	 * @param centerY wartoœæ y jako int
	 */
	public void setCenter(int centerX, int centerY) {
		this.centerX = centerX;
		this.centerY = centerY;
	}
	
	/**
	 * Obraca obraz o podany k¹t.
	 * @param deg k¹t o jaki obraz ma siê obróciæ
	 */
	public void rotate(double deg) {
		if(this.deg + deg <= maxDeg && this.deg + deg >= 0) {
			at.rotate(Math.toRadians(deg), centerX, centerY);
		}
	}
	
	/**
	 * Ustawia wartoœæ z zakresu podanego przy wywo³aniu kosntruktora.
	 * Je¿eli docelowe wartoœci przekraczaj¹ mo¿liwy zakres obraz sie nie obróci.
	 * @param newVal nowa wartoœæ jak¹ ma wskazywaæ wskazówka
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
	 * Zwraca obiekt at umo¿liwaj¹cy transformacjê obrazu.
	 * @return obiekt klasy AffineTransform umo¿liwiaj¹cy transformacjê
	 */
	public AffineTransform getAt() {
		return at;
	}
	
	
	
}
