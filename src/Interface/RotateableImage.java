package Interface;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class RotateableImage {

	private BufferedImage img;
	private AffineTransform at;
	private float minVal, maxVal;
	private int deg, maxDeg;
	private int translateX, translateY, centerX, centerY;
	
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
	
	public void setTranslation(int translateX, int translateY) {
		this.translateX = translateX;
		this.translateY = translateY;
		at = AffineTransform.getTranslateInstance(translateX, translateY);
	}
	
	public void setCenter(int centerX, int centerY) {
		this.centerX = centerX;
		this.centerY = centerY;
	}
	
	public void rotate(double deg) {
		if(this.deg + deg <= maxDeg && this.deg + deg >= 0) {
			at.rotate(Math.toRadians(deg), centerX, centerY);
		}
	}
	
	public void setVal(float newVal) {
		if(newVal > maxVal) rotate(maxDeg);
		else if(newVal < minVal) rotate(0);
		else rotate(((newVal) * maxDeg)/ (maxVal + minVal));
	}

	public BufferedImage getImg() {
		return img;
	}

	public AffineTransform getAt() {
		return at;
	}
	
	
	
}
