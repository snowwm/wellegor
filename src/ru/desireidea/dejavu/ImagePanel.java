package ru.desireidea.dejavu;

import java.awt.*;
import java.awt.image.ImageObserver;

public class ImagePanel extends Canvas {

	private Image img;
	private ImageObserver observer;
	private boolean newImage;

	public ImagePanel() {
		super();
	}

	public ImagePanel(Image img) {
		super();
		this.img = img;
	}

	public ImagePanel(ImageObserver observer) {
		super();
		this.observer = observer;
	}

	public ImagePanel(Image img, ImageObserver observer) {
		super();
		this.img = img;
		this.observer = observer;
	}

	public void paint(Graphics g) {
		g.drawImage(img, 0, 0, getWidth() - 1, getHeight() - 1, observer);
	}

	public void update(Graphics g) {
		if (newImage) {
			super.update(g);
			newImage = false;
		} else
			paint(g);
	}

	public Image createImage() {
		img = createImage(getWidth() - 1, getHeight() - 1);
		return img;
	}

	public Image getImage() {
		return img;
	}

	public void setImage(Image img) {
		this.img = img;
		newImage = true;
		repaint();
	}

	public ImageObserver getObserver() {
		return observer;
	}

	public void setObserver(ImageObserver observer) {
		this.observer = observer;
	}

}
