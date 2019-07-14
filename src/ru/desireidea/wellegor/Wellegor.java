package ru.desireidea.wellegor;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.RenderedImage;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import ru.desireidea.dejavu.Command;
import ru.desireidea.dejavu.FileToString;
import ru.desireidea.dejavu.ImagePanel;

public class Wellegor extends JApplet implements ActionListener,
		DocumentListener, WindowListener, Command, KeyEventDispatcher {

	private boolean appletMode = true, checkResult = true, textEdit = true,
			delaySet = false, absDelaySet = false, saveSc = false;
	private ImageIcon success, failure;
	private JTextField field1, field2;
	private JButton multiply;
	private JFrame frame;
	private int arg1, arg2;
	private long product = -6l, absDelay = 10000l;
	private ImagePanel img1, img2, area1, area2;
	private int delay = 0;
	private String scDir, scName, scPath;
	private MenuItem scDirMenu, scNameMenu, aboutMenu, helpMenu,
			whatIsThisMenu;
	private Image successImg, failureImg;

	public Wellegor() {
	}

	public Wellegor(String scPath) {
		this.scPath = scPath;
	}

	public Wellegor(boolean state) {
		checkResult = state;
	}

	public Wellegor(int delay) {
		this.delay = delay;
	}

	public Wellegor(long absDelay) {
		this.absDelay = absDelay;
	}

	public Wellegor(boolean state, int delay) {
		checkResult = state;
		this.delay = delay;
	}

	public Wellegor(boolean state, long absDelay) {
		checkResult = state;
		this.absDelay = absDelay;
	}

	public Wellegor(boolean state, String scPath) {
		checkResult = state;
		this.scPath = scPath;
	}

	public Wellegor(int delay, String scPath) {
		this.delay = delay;
		this.scPath = scPath;
	}

	public Wellegor(long absDelay, String scPath) {
		this.absDelay = absDelay;
		this.scPath = scPath;
	}

	public Wellegor(boolean state, int delay, String scPath) {
		checkResult = state;
		this.delay = delay;
		this.scPath = scPath;
	}

	public Wellegor(boolean state, long absDelay, String scPath) {
		checkResult = state;
		this.absDelay = absDelay;
		this.scPath = scPath;
	}

	public static void main(String[] args) {
		Wellegor $this = new Wellegor();
		switch (args.length) {
		case 4:
			$this.scPath = args[3];
		case 3:
			try {
				$this.absDelay = Long.parseLong(args[2]);
				$this.absDelaySet = true;
			} catch (NumberFormatException e) {
			}
		case 2:
			try {
				$this.delay = Integer.parseInt(args[1]);
				$this.delaySet = true;
			} catch (NumberFormatException e) {
			}
		case 1:
			$this.checkResult = "true".equals(args[0])
					|| "false".equals(args[0]) ? Boolean.parseBoolean(args[0])
					: true;
		}
		$this.appletMode = false;
		$this.init();
	}

	public void init() {
		success = new ImageIcon(getClass().getResource(
				"/ru/desireidea/wellegor/resources/Success.png"));
		successImg = success.getImage();
		failure = new ImageIcon(getClass().getResource(
				"/ru/desireidea/wellegor/resources/Failure.png"));
		failureImg = failure.getImage();
		setSize(795, 975);
		setBackground(new Color(238, 238, 238));
		setLayout(null);
		JLabel info = new JLabel(
				"Множители должны быть положительными числами до 1000000000");
		info.setBounds(192, 5, 410, 15);
		info.setFont(new Font("Helvetica", Font.BOLD, 12));
		field1 = new JTextField();
		field1.setBounds(268, 42, 70, 20);
		field1.getDocument().addDocumentListener(this);
		field2 = new JTextField();
		field2.setBounds(457, 42, 70, 20);
		field2.getDocument().addDocumentListener(this);
		multiply = new JButton("X");
		multiply.setBounds(370, 30, 55, 55);
		multiply.setFont(new Font("Courier", Font.BOLD, 32));
		multiply.addActionListener(this);
		area1 = new ImagePanel(failureImg);
		area1.setBounds(207, 30, 48, 48);
		area2 = new ImagePanel(failureImg);
		area2.setBounds(540, 30, 48, 48);
		img1 = new ImagePanel();
		img1.setBounds(20, 105, 750, 750);
		img1.setBackground(Color.WHITE);
		img2 = new ImagePanel();
		img2.setBounds(20, 875, 750, 100);
		add(info);
		add(area1);
		add(field1);
		add(multiply);
		add(field2);
		add(area2);
		add(img1);
		add(img2);
		if (appletMode) {
			checkResult = "true".equals(getParameter("checkresult"))
					| "false".equals(getParameter("checkresult")) ? Boolean
					.parseBoolean(getParameter("checkresult")) : true;
			try {
				delay = Integer.parseInt(getParameter("delay"));
				delaySet = true;
			} catch (NumberFormatException e) {
			}
			try {
				absDelay = Long.parseLong(getParameter("absDelay"));
				absDelaySet = true;
			} catch (NumberFormatException e) {
			}
			scPath = getParameter("screenshotpath");
		}
		frame = new JFrame("Поразрядное сеточное умножение - "
				+ (checkResult ? "[Режим контроля результата]"
						: "[Контроль результата отключен]"));
		frame.setResizable(false);
		frame.setSize(795, 975);
		frame.addWindowListener(this);
		frame.setIconImage(new ImageIcon(getClass().getResource(
				"/ru/desireidea/wellegor/resources/Icon.png")).getImage());
		frame.add(this);
		KeyboardFocusManager.getCurrentKeyboardFocusManager()
				.addKeyEventDispatcher(this);
		MenuBar bar = new MenuBar();
		if (!appletMode) {
			Menu scMenu = new Menu("Настройки");
			scDirMenu = new MenuItem("Папка для сохранения снимков экрана");
			scDirMenu.addActionListener(this);
			scMenu.add(scDirMenu);
			scNameMenu = new MenuItem("Имя файлов со снимками экрана");
			scNameMenu.addActionListener(this);
			scMenu.add(scNameMenu);
			bar.add(scMenu);
		}
		Menu help = new Menu("Помощь");
		whatIsThisMenu = new MenuItem("Что это такое?");
		whatIsThisMenu.addActionListener(this);
		help.add(whatIsThisMenu);
		helpMenu = new MenuItem("Как пользоваться программой?");
		helpMenu.addActionListener(this);
		help.add(helpMenu);
		aboutMenu = new MenuItem("О программе");
		aboutMenu.addActionListener(this);
		help.addSeparator();
		help.add(aboutMenu);
		bar.setHelpMenu(help);
		frame.setMenuBar(bar);
		frame.setVisible(true);
		img1.createImage();
		img2.createImage();
		if (delaySet && !absDelaySet)
			absDelay = -1;
	}

	public static String error(int id) {
		String message = null;
		switch (id) {
		case -1: {
			message = "Произведение посчитано некорректно.";
			break;
		}
		case -2: {
			message = "Недопустимое значение множителя(ей).";
			break;
		}
		case -3: {
			message = "Во время рассчётов произошла ошибка: IndexOutOfBoundsException (выход за границы диапазона).";
			break;
		}
		case -4: {
			message = "Во время рассчётов произошла ошибка: NumberFormatException (невозможно конвертировать текст в число).";
			break;
		}
		case -5: {
			message = "Во время рассчётов произошла неизвестная ошибка.";
			break;
		}
		case -6: {
			message = "По неизвестным причинам программе не удалось выполнить рассчёты.";
			break;
		}
		}
		message += " Обязательно сообщите об этом разработчику программы, чтобы он исправил эту ошибку.";
		return message;
	}

	public BGMData multiply() {
		return BitwiseGridMultiplicator
				.specialMultiply(arg1, arg2, checkResult);
	}

	public long visualise() {
		BGMData data = multiply();
		if (product != data.value) {
			product = data.value;
			if (product >= 0) {
				img1.createImage();
				img2.createImage();
				WellegorVisualiser.visualise(img1.getImage().getGraphics(),
						img2.getImage().getGraphics(), data, delay, absDelay,
						750, this);
			} else
				showMessage("Ошибка!", error(new Long(data.value).intValue()),
						true);
		}
		return data.value;
	}

	public boolean checkArgs(String arg1, String arg2) {
		boolean state = false;
		try {
			Integer iArg1 = Integer.parseInt(arg1);
			if (iArg1 <= BitwiseGridMultiplicator.MAX_FACTOR_VALUE
					&& iArg1 >= 0) {
				area1.setImage(successImg);
				state = true;
			} else {
				area1.setImage(failureImg);
			}
		} catch (NumberFormatException e) {
			area1.setImage(failureImg);
		}
		try {
			Integer iArg2 = Integer.parseInt(arg2);
			if (iArg2 <= BitwiseGridMultiplicator.MAX_FACTOR_VALUE
					&& iArg2 >= 0) {
				area2.setImage(successImg);
			} else {
				state = false;
				area2.setImage(failureImg);
			}
		} catch (NumberFormatException e) {
			state = false;
			area2.setImage(failureImg);
		}
		return state;
	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == multiply) {
			if (checkArgs(field1.getText(), field2.getText())) {
				arg1 = new Integer(field1.getText());
				arg2 = new Integer(field2.getText());
				visualise();
			}
		} else if (evt.getSource() == scDirMenu) {
			JFileChooser chooser = new JFileChooser(
					scDir == null ? System.getProperty("user.home")
							+ "\\Desktop" : scDir);
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			chooser.setDragEnabled(true);
			if (chooser.showDialog(this, "Принять") == JFileChooser.APPROVE_OPTION)
				scDir = chooser.getSelectedFile().toString();
		} else if (evt.getSource() == scNameMenu) {
			String temp = (String) JOptionPane
					.showInputDialog(
							null,
							"Вместо \"<PRODUCT>\" будет подставлено текущее произведение на момент сохранения снимка:",
							"Введите имя файла для сохранения снимков экрана",
							JOptionPane.QUESTION_MESSAGE, null, null,
							scName == null ? "WellegorScreenshot [<PRODUCT>]"
									: scName);
			scName = temp == null ? scName : temp;
		} else if (evt.getSource() == whatIsThisMenu) {
			try {
				showMessage(
						"Что это такое?",
						FileToString
								.readFile(getClass()
										.getResourceAsStream(
												"/ru/desireidea/wellegor/resources/WhatIsThis.txt")),
						false);
			} catch (Exception fail) {
				showMessage("Ошибка!6", fail, true);
			}
		} else if (evt.getSource() == helpMenu) {
			try {
				showMessage(
						"Как пользоваться программой?",
						FileToString.readFile(getClass().getResourceAsStream(
								"/ru/desireidea/wellegor/resources/Help.txt")),
						false);
			} catch (Exception fail) {
				showMessage("Ошибка!", fail, true);
			}
		} else if (evt.getSource() == aboutMenu) {
			try {
				showMessage(
						"О программе",
						FileToString.readFile(getClass().getResourceAsStream(
								"/ru/desireidea/wellegor/resources/About.txt")),
						false);
			} catch (Exception fail) {
				showMessage("Ошибка!", fail, true);
			}
		} else if (evt.getActionCommand().startsWith("field")) {
			JTextField field = evt.getActionCommand().endsWith("1") ? field1
					: field2;
			textEdit = false;
			field.setText(field.getText().substring(
					0,
					field.getText().length() <= 9 ? field.getText().length()
							: 9));
			checkArgs(field1.getText(), field2.getText());
		}
	}

	public File saveScreenshot() {
		File file = null;
		try {
			if (scName == null)
				if (scPath == null)
					scName = "WellegorScreenshot ["
							+ (product < 0 ? 0 : product) + "]";
				else
					scName = scPath.substring(scPath.lastIndexOf("\\"));
			if (scDir == null)
				if (scPath == null)
					scDir = System.getProperty("user.home") + "\\Desktop";
				else
					scDir = scPath.substring(0, scPath.lastIndexOf("\\"));
			file = new File(scDir
					+ "\\"
					+ scName.replace("<PRODUCT>", (product < 0 ? 0 : product)
							+ "") + ".gif");
			for (int i = 1; file.exists(); i++)
				file = new File(scDir
						+ "\\"
						+ scName.replace("<PRODUCT>", (product < 0 ? 0
								: product) + "") + "_" + i + ".gif");
			file.mkdirs();
			javax.imageio.ImageIO.write((RenderedImage) img1.getImage(), "gif",
					file);
			showMessage("Снимок экрана успешно сохранён!", "Путь к файлу: "
					+ file, false);
		} catch (Exception fail) {
			showMessage(
					"Снимок экрана не сохранён!",
					"Произошла ошибка: "
							+ fail
							+ "\nПопробуйте указать допустимый путь к сохраняемым снимкам в настройках. Если это не помогло, обратитесь к разработчику программы.",
					true);
		}
		return file;
	}

	public String getAppletInfo() {
		return "";
	}

	public String[][] getParameterInfo() {
		return new String[0][0];
	}

	public void windowActivated(WindowEvent evt) {

	}

	public void windowClosed(WindowEvent evt) {

	}

	public void windowClosing(WindowEvent evt) {
		if (!appletMode)
			System.exit(0);
	}

	public void windowDeactivated(WindowEvent evt) {

	}

	public void windowDeiconified(WindowEvent evt) {

	}

	public void windowIconified(WindowEvent evt) {

	}

	public void windowOpened(WindowEvent evt) {
	}

	public void changedUpdate(DocumentEvent evt) {
		checkArgs(field1.getText(), field2.getText());
	}

	public void insertUpdate(DocumentEvent evt) {
		if (evt.getDocument().getLength() + evt.getLength() > 10 && textEdit) {
			Timer timer = new Timer(0, this);
			timer.setActionCommand(evt.getDocument() == field1.getDocument() ? "field1"
					: "field2");
			timer.setRepeats(false);
			timer.start();
		} else
			checkArgs(field1.getText(), field2.getText());
		textEdit = true;
	}

	public void removeUpdate(DocumentEvent evt) {
		checkArgs(field1.getText(), field2.getText());
	}

	public boolean checkResult() {
		return checkResult;
	}

	public void checkResult(boolean checkResult) {
		this.checkResult = checkResult;
	}

	public long getArg1() {
		return arg1;
	}

	public void setArg1(int arg1) {
		this.arg1 = arg1;
	}

	public long getArg2() {
		return arg2;
	}

	public void setArg2(int arg2) {
		this.arg2 = arg2;
	}

	public long getProduct() {
		return product;
	}

	public void setProduct(long product) {
		this.product = product;
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delayelay) {
		this.delay = delayelay;
	}

	public long getAbsDelay() {
		return absDelay;
	}

	public void setAbsDelay(int absDelay) {
		this.absDelay = absDelay;
	}

	public boolean isApplet() {
		return appletMode;
	}

	public boolean runCmd(Object[] args) {
		img1.repaint();
		img2.repaint();
		return true;
	}

	public boolean dispatchKeyEvent(KeyEvent evt) {
		if (!appletMode)
			if (evt.getKeyCode() == KeyEvent.VK_F2
					|| (evt.getKeyCode() == KeyEvent.VK_S && evt
							.isControlDown())) {
				saveSc = !saveSc;
				if (saveSc)
					saveScreenshot();
			} else if (evt.getKeyCode() == KeyEvent.VK_PRINTSCREEN)
				saveScreenshot();
		return false;
	}

	public void showMessage(String header, Object message, boolean error) {
		JOptionPane.showConfirmDialog(this, message, header,
				JOptionPane.DEFAULT_OPTION, error ? JOptionPane.ERROR_MESSAGE
						: JOptionPane.INFORMATION_MESSAGE, error ? failure
						: success);
	}

}
