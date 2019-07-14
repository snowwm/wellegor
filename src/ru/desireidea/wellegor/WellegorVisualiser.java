package ru.desireidea.wellegor;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;

import ru.desireidea.dejavu.Command;

public class WellegorVisualiser implements Runnable {

	private Graphics g;
	private Graphics g2;
	private BGMData data;
	private int delay;
	private long absDelay;
	private int width;
	private Command cmd;
	public static final int WIDTH = 750;
	public static final int HEIGHT = 750;

	public static void visualise(Graphics g, Graphics g2, BGMData data,
			int delay, long absDelay, int width, Command cmd) {
		new Thread(new WellegorVisualiser(g, g2, data, delay, absDelay, width,
				cmd)).start();
	}

	public void run() {
		final int CLIP = 20;
		final int DOTLENGTH = 10;
		final int DOTSPACE = 7;
		final int WIDTH = 710;
		g.setColor(Color.BLACK);
		g.clipRect(CLIP, CLIP, WIDTH, WIDTH);
		int cH = 0;
		int cV = 0;
		for (byte i = 0; i < data.pointsH.length; i++) {
			cH += data.pointsH[i];
			if (data.pointsH[i] == 0)
				cH++;
		}
		for (byte i = 0; i < data.pointsV.length; i++) {
			cV += data.pointsV[i];
			if (data.pointsV[i] == 0)
				cV++;
		}
		int[] coordsH = new int[cH];
		int[] coordsV = new int[cV];
		int xV = (int) Math.round(WIDTH / (cV + 3 * data.pointsV.length + 4d));
		int xH = (int) Math.round(WIDTH / (cH + 3 * data.pointsH.length + 4d));
		int yH = 4 * xH;
		int yV = 4 * xV;
		int corrV = (WIDTH - (xV * (cV - data.pointsV.length) + yV
				* (data.pointsV.length + 1))) / 2;
		int corrH = (WIDTH - (xH * (cH - data.pointsH.length) + yH
				* (data.pointsH.length + 1))) / 2;
		delay = delay >= 0 ? delay : 100;
		int delay = ((int) (absDelay < 0 ? this.delay : Math.min(500, absDelay
				/ (cH + cV))));
		int diameter = Math.max(5, Math.min(12, Math.min(xH, xV) / 2 + 2));
		Point[][] frags = new Point[data.pointsH.length * data.pointsV.length][];
		boolean[] zeroFrags = new boolean[frags.length];
		boolean color = false;
		String nextAddend = "";
		FontMetrics fM = g2.getFontMetrics(g2.getFont());
		int indentationH = 0;
		int indentationV = fM.getHeight();
		for (int y = CLIP + yV + corrV, x = CLIP, i = 0, j = 0, k = 1; i < cV; i++, k++) {
			if (data.pointsV[j] == 0) {
				for (int dot = x; dot < WIDTH + CLIP; dot += DOTSPACE
						+ DOTLENGTH) {
					g.drawLine(dot, y, dot + DOTLENGTH, y);
				}
				k -= 1;
			} else
				g.drawLine(x, y, WIDTH + CLIP, y);
			coordsV[i] = y;
			if (k == data.pointsV[j]) {
				y += yV;
				j++;
				k = 0;
			} else
				y += xV;
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
			}
			cmd.runCmd(null);
		}
		for (int y = CLIP, x = CLIP + yH + corrH, i = 0, j = 0, k = 1; i < cH; i++, k++) {
			if (data.pointsH[j] == 0) {
				for (int dot = y; dot < WIDTH + CLIP; dot += DOTSPACE
						+ DOTLENGTH) {
					g.drawLine(x, dot, x, dot + DOTLENGTH);
				}
				k -= 1;
			} else
				g.drawLine(x, y, x, WIDTH + CLIP);
			coordsH[i] = x;
			if (k == data.pointsH[j]) {
				x += yH;
				j++;
				k = 0;
			} else
				x += xH;
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
			}
			cmd.runCmd(null);
		}
		for (int f = 0, r = 0, c = 0, i = 0, j = 0, offtH = 0, offtV = 0; f < data.pointsH.length
				* data.pointsV.length; f++) {
			if (f % data.pointsH.length == 0 & f != 0) {
				offtV += data.pointsV[r] == 0 ? 1 : data.pointsV[r];
				offtH = 0;
				r += 1;
				c = 0;
			} else if (f != 0) {
				offtH += data.pointsH[c] == 0 ? 1 : data.pointsH[c];
				c += 1;
			}
			zeroFrags[f] = (data.pointsV[r] * data.pointsH[c]) == 0;
			frags[f] = new Point[(data.pointsV[r] == 0 ? 1 : data.pointsV[r])
					* (data.pointsH[c] == 0 ? 1 : data.pointsH[c])];
			for (byte n = 0; i < (data.pointsV[r] == 0 ? 1 : data.pointsV[r]); i++) {
				for (; j < (data.pointsH[c] == 0 ? 1 : data.pointsH[c]); j++, n++) {
					frags[f][n] = new Point(coordsH[offtH + j] - diameter / 2,
							coordsV[offtV + i] - diameter / 2);
				}
				j = 0;
			}
			i = 0;
		}
		for (int i = 0; i < data.lines.length; i++) {
			for (int j = 0; j < data.lines[i].length; j++) {
				for (int k = 0, f = data.lines[i][j].x + data.lines[i][j].y
						* data.pointsH.length; k < frags[f].length; k++) {
					if (zeroFrags[f])
						g.setColor(new Color(color ? 255 : 150, 150,
								color ? 150 : 255));
					else
						g.setColor(new Color(color ? 255 : 0, 0, color ? 0
								: 255));
					g.fillOval(frags[f][k].x, frags[f][k].y, diameter, diameter);
				}
			}
			g2.setColor(new Color(color ? 255 : 0, 0, color ? 0 : 255));
			nextAddend = "" + (long) (data.intSum[i] * Math.pow(10, i));
			if (indentationH
					+ fM.stringWidth(nextAddend
							+ (i == data.lines.length - 1 ? " = " : " + ")) >= width) {
				indentationH = 0;
				indentationV += fM.getHeight();
				Color currentColor = g2.getColor();
				g2.setColor(Color.BLACK);
				g2.drawString(" + ", CLIP + indentationH, indentationV);
				g2.setColor(currentColor);
				indentationH += fM
						.stringWidth(i == data.lines.length - 1 ? " = " : " + ");
			}
			g2.drawString(nextAddend, CLIP + indentationH, indentationV);
			indentationH += fM.stringWidth(nextAddend);
			g2.setColor(Color.BLACK);
			g2.drawString(i == data.lines.length - 1 ? " = " : " + ", CLIP
					+ indentationH, indentationV);
			indentationH += fM.stringWidth(i == data.lines.length - 1 ? " = "
					: " + ");
			color = !color;
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
			}
			cmd.runCmd(null);
		}
		g2.setFont(new Font(g2.getFont().getName(), Font.BOLD, g2.getFont()
				.getSize()));
		fM = g2.getFontMetrics(g2.getFont());
		if (indentationH + fM.stringWidth(data.value + "") >= width) {
			indentationH = 0;
			indentationV += fM.getHeight();
			g2.setColor(Color.BLACK);
			g2.drawString(" = ", CLIP + indentationH, indentationV);
			indentationH += fM.stringWidth(" = ");
		}
		g2.setColor(new Color(0, 165, 0));
		g2.drawString(data.value + "", CLIP + indentationH, indentationV);
		cmd.runCmd(null);
	}

	public WellegorVisualiser(Graphics g, Graphics g2, BGMData data, int delay,
			long absDelay, int width, Command cmd) {
		this.g = g.create();
		this.g2 = g2.create();
		this.data = data.clone();
		this.delay = delay;
		this.absDelay = absDelay;
		this.width = width;
		this.cmd = cmd;
	}

	public static int getWidth() {
		return WIDTH;
	}

	public static int getHeight() {
		return HEIGHT;
	}

}
