package userinterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class UserInterface extends JFrame {

	private JPanel sidePanel = new JPanel();
	private JPanel mainPanel = new JPanel();
	private JPanel welcomeInterfacePanel = new JPanel();
	private JPanel labelPanel = new JPanel();
	private JPanel dateLabelPanel = new JPanel();// 主要是为了给标签添加背景色

	private JTextField inputInterfaceWord = new JTextField("word", 25);
	private JTextField inputInterfaceMean = new JTextField("翻译", 25);
	private JTextField reviewInterfaceWord = new JTextField("", 25);
	private JTextField reviewInterfaceMean = new JTextField("", 25);
	private JTextField searchField = new JTextField("", 20);

	private JButton save = createButton("SAVE", 612, 175, 85, 50, buttonBackgroundColor, Color.WHITE);
	private JButton finish = createButton("FINISH", 294, 500, 85, 50, buttonBackgroundColor, Color.WHITE);
	private JButton input = createButton("INPUT", 300, 350, 120, 50, buttonBackgroundColor, Color.WHITE);
	private JButton review = createButton("REVIEW", 700, 350, 120, 50, buttonBackgroundColor, Color.WHITE);
	private JButton labelListSwitch = createButton("!", 0, 450, 180, 30, secondBackgroundColor,
			new Color(100, 100, 100, 255));
	private JButton mean = createButton("MEAN", 389, 250, 85, 50, buttonBackgroundColor, Color.WHITE);
	private JButton add = createButton("ADD", 179, 550, 85, 50, buttonBackgroundColor, Color.WHITE);
	private JButton modify = createButton("MODIFY", 600, 550, 85, 50, buttonBackgroundColor, Color.WHITE);
	private JButton set = createButton("SET", 400, 550, 85, 50, buttonBackgroundColor, Color.WHITE);
	private JButton close = createButton("X", 820, 0, 44, 44, new Color(255, 127, 39, 150), Color.WHITE);
	private JButton pointButton = new JButton("");
	private JButton labelbutton[] = new JButton[100];

	private JLabel welcomeInterfaceNotice = new JLabel();
	private JLabel reviewWord = new JLabel();
	private JLabel reviewMean = new JLabel();
	private JLabel dateLabel = new JLabel();

	static Color mainBackgroundColor = new Color(237, 231, 177, 255);
	static Color secondBackgroundColor = new Color(249, 245, 230, 255);
	static Color labelButtonColor = new Color(147, 138, 57, 255);
	static Color buttonBackgroundColor = new Color(50, 165, 177, 255);

	static Font labelButtonFont = new Font("宋体", Font.CENTER_BASELINE, 20);
	private Font textFieldFont = new Font("宋体", Font.CENTER_BASELINE, 30);

	private String[] allWords = new String[1000];
	private String[] allNames = new String[1000];
	static String singleWords[] = new String[100];
	static String[] modifiedWords = new String[100];
	static String[] modifiedNames = new String[100];
	private static UserInterface userinterface = null;

	private int index = 0;
	private int flag = -1;// index和flag是录入单词时所使用的标记
	private int beginPoint = 0;
	private int endPoint = 0;// beginPoint和endPoint时修改单词时用的标记
	private int[] reviewWordsIndex = { 1, 2, 4, 7, 15 };
	private int reviewWordIndex = 0;// 下面这两个是根据记忆曲线复习单词所用的标记
	private int modifiedWordsIndex = 0;

	public static void main(String[] args) throws IOException {

		UserInterface u = getInstance();
		u.start();
		

	}

	public static UserInterface getInstance() throws IOException {
		if (userinterface == null)
			userinterface = new UserInterface();
		return userinterface;
	}

	private UserInterface() throws IOException {

		MyIO myio = MyIO.getMyIOInstance();
		allWords = myio.getWords();
		allNames = myio.getNames();

		mainPanel.add(close);
		mainPanel.setBounds(288, 0, 864, 600);
		mainPanel.setLayout(null);
		mainPanel.setBackground(mainBackgroundColor);
		sidePanel.setBackground(secondBackgroundColor);
		sidePanel.setBounds(0, 0, 288, 600);
		welcomeInterfacePanel.setBounds(0, 0, 1152, 600);
		welcomeInterfacePanel.setLayout(null);
		welcomeInterfacePanel.setBackground(mainBackgroundColor);
		dateLabelPanel.setBackground(secondBackgroundColor);
		dateLabelPanel.setBounds(0, 450, 180, 30);
		dateLabelPanel.add(dateLabel);
		labelPanel.setBackground(secondBackgroundColor);
		labelPanel.setBounds(0, 0, 288, 600);

		searchField.setFont(textFieldFont);
		searchField.setBounds(70, 20, 150, 30);
		inputInterfaceWord.setBounds(162, 100, 350, 50);
		inputInterfaceWord.setFont(textFieldFont);
		inputInterfaceMean.setBounds(162, 250, 350, 50);
		inputInterfaceMean.setFont(textFieldFont);
		reviewInterfaceWord.setBounds(257, 100, 350, 50);
		reviewInterfaceWord.setFont(new Font("宋体", Font.CENTER_BASELINE, 50));
		reviewInterfaceMean.setBounds(257, 250, 350, 50);
		reviewInterfaceMean.setFont(new Font("宋体", Font.CENTER_BASELINE, 50));
		dateLabel.setBounds(0, 450, 180, 30);
		dateLabel.setFont(labelButtonFont);
		dateLabel.setForeground(new Color(155, 155, 155, 255));
		dateLabel.setText(allNames[0].substring(11, 19));

		close.addActionListener(new CloseAction());
		input.addActionListener(new InputAction());
		review.addActionListener(new ReviewAction());
		save.addActionListener(new SaveAction());
		finish.addActionListener(new FinishAction());
		mean.addActionListener(new MeanAction());
		modify.addActionListener(new ModifyAction());
		pointButton.addKeyListener(new PointButtonKeyListener());
		set.addActionListener(new SetAction());
		add.addActionListener(new AddAction());

		inputInterfaceWord.addKeyListener(new InputInterfaceKeyListener());
		inputInterfaceMean.addKeyListener(new InputInterfaceKeyListener());

		if (allWords[0] == "0")
			welcomeInterfaceNotice.setText("YOU HAVE'T INPUT WORDS TODAY,START INPUT NOW ?");
		else
			welcomeInterfaceNotice.setText("YOU HAVE INPUT WORDS ALREDY, REVIEW NOW ?");
		welcomeInterfaceNotice.setFont(new Font("宋体", Font.CENTER_BASELINE, 35));
		welcomeInterfaceNotice.setBounds(100, 100, 952, 80);
		welcomeInterfaceNotice.setHorizontalAlignment(SwingConstants.CENTER);
		reviewWord.setBounds(0, 100, 864, 50);
		reviewWord.setFont(new Font("宋体", Font.CENTER_BASELINE, 50));
		reviewWord.setForeground(labelButtonColor);
		reviewWord.setHorizontalAlignment(SwingConstants.CENTER);
		reviewMean.setBounds(0, 250, 864, 50);
		reviewMean.setFont(new Font("宋体", Font.CENTER_BASELINE, 40));
		reviewMean.setForeground(new Color(100, 100, 100, 255));
		reviewMean.setHorizontalAlignment(SwingConstants.CENTER);

	}

	public void start() {
		welcomeInterfacePanel.add(welcomeInterfaceNotice);
		welcomeInterfacePanel.add(input);
		welcomeInterfacePanel.add(review);
		setBounds(130, 50, 1152, 600);
		setLayout(null);
		setDefaultCloseOperation(3);
		setUndecorated(true);
		add(welcomeInterfacePanel);
		repaint();
		setVisible(true);

	}

	static JButton createButton(String name, int x, int y, int width, int height, Color back, Color fore) {

		JButton button = new JButton(name);
		button.setBackground(back);
		button.setForeground(fore);
		button.setFocusable(false);
		button.setBorderPainted(false);
		
	
		if (x != -1)
			button.setBounds(x, y, width, height);

		return button;
	}

	private class InputAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			labelListSwitch.setText(allNames[0].substring(11, 20));
			mainPanel.add(labelListSwitch);
			mainPanel.add(dateLabelPanel);
			mainPanel.add(save);
			mainPanel.add(finish);
			mainPanel.add(inputInterfaceWord);
			mainPanel.add(inputInterfaceMean);
			remove(welcomeInterfacePanel);
			add(mainPanel);
			add(sidePanel);
			revalidate();
			repaint();

		}
	}

	private class ReviewAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			if (allWords[reviewWordsIndex[reviewWordIndex]].indexOf(58, beginPoint) == -1) {
				reviewWordIndex++;
				beginPoint = 0;
				if (reviewWordIndex == reviewWordsIndex.length) {
					reviewWord.setText("结束了!");
				}
			}
			while (allWords[reviewWordsIndex[reviewWordIndex]].indexOf(":", beginPoint) != -1) {

				endPoint = allWords[reviewWordsIndex[reviewWordIndex]].indexOf(":", beginPoint);
				JButton b = createButton(allWords[reviewWordsIndex[reviewWordIndex]].substring(beginPoint, endPoint),
						-1, -1, -1, -1, secondBackgroundColor, labelButtonColor);
				b.setFont(labelButtonFont);
				labelPanel.add(b);
				labelPanel.revalidate();
				beginPoint = endPoint + 1;
				beginPoint = allWords[reviewWordsIndex[reviewWordIndex]].indexOf("|", beginPoint) + 1;

			}

			beginPoint = 1;
			endPoint = allWords[reviewWordsIndex[reviewWordIndex]].indexOf(":", beginPoint);
			reviewWord.setText(allWords[reviewWordsIndex[reviewWordIndex]].substring(beginPoint, endPoint));
			beginPoint = endPoint + 1;
			endPoint = allWords[reviewWordsIndex[reviewWordIndex]].indexOf("|", beginPoint);
			reviewMean.setText(allWords[reviewWordsIndex[reviewWordIndex]].substring(beginPoint, endPoint));

			remove(welcomeInterfacePanel);
			labelListSwitch.setText(allNames[reviewWordsIndex[reviewWordIndex]].substring(11, 20));
			labelListSwitch.addActionListener(new LabelListSwitchAction());
			mainPanel.add(labelListSwitch);
			mainPanel.add(reviewWord);
			mainPanel.add(mean);
			mainPanel.add(pointButton);
			sidePanel.setLayout(null);// 只有添加了这一步,它的组件labelPanel才能正常布局
			sidePanel.add(labelPanel);
			labelPanel.setVisible(false);
			sidePanel.add(searchField);
			add(sidePanel);
			add(mainPanel);
			pointButton.requestFocus();
			validate();
			repaint();
		}
	}

	private class ModifyAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			mainPanel.remove(reviewWord);
			mainPanel.remove(reviewMean);
			mainPanel.remove(modify);
			pointButton.setEnabled(false);
			mainPanel.remove(add);
			mainPanel.add(reviewInterfaceWord);
			mainPanel.add(reviewInterfaceMean);
			reviewInterfaceMean.setText(allWords[reviewWordsIndex[reviewWordIndex]].substring(beginPoint, endPoint));
			endPoint = beginPoint - 1;
			if (allWords[reviewWordsIndex[reviewWordIndex]].lastIndexOf("|", beginPoint) == -1) {
				beginPoint = 1;
			} else
				beginPoint = allWords[reviewWordsIndex[reviewWordIndex]].lastIndexOf("|", beginPoint) + 1;
			reviewInterfaceWord.setText(allWords[reviewWordsIndex[reviewWordIndex]].substring(beginPoint, endPoint));
			mainPanel.add(set);

			repaint();
			validate();
		}
	}

	private class MeanAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			mainPanel.remove(mean);
			mainPanel.add(reviewMean);
			mainPanel.add(add);
			mainPanel.add(modify);
			validate();
			repaint();
		}
	}

	private class SaveAction implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			if (inputInterfaceWord.getText().length() == 0) {
				inputInterfaceWord.setText("Please input a word");
				return;
			}
			if (flag == -1) {
				singleWords[index] = inputInterfaceWord.getText() + ":" + inputInterfaceMean.getText() + "|";
				labelbutton[index] = createButton(inputInterfaceWord.getText(), -1, -1, -1, -1, secondBackgroundColor,
						labelButtonColor);

				labelbutton[index].setFont(new Font("宋体", Font.CENTER_BASELINE, 17));
				
				labelbutton[index].addActionListener(new LabelButtonListener(index));
				sidePanel.add(labelbutton[index]);
				index++;
			} else {
				singleWords[flag] = inputInterfaceWord.getText() + ":" + inputInterfaceMean.getText() + "|";
				labelbutton[flag].setText(inputInterfaceWord.getText());
				flag = -1;
			}
			inputInterfaceWord.setText("");
			inputInterfaceMean.setText("");
			inputInterfaceWord.requestFocus();
			sidePanel.revalidate();
			sidePanel.repaint();

		}
	}

	// 把当天的日期存入modifiedNames[0]中,把singleWords中的字符串合并成一个字符串存入modifiedWords[0]中,并在末尾加上"*"
	// 然后进入复习界面,并把第一个单词呈现在屏幕上面
	private class LabelListSwitchAction implements ActionListener {
		int flag = 0;

		public void actionPerformed(ActionEvent e) {
			if (flag == 0) {
				flag = 1;
				searchField.setVisible(false);
				labelPanel.setVisible(true);
			} else {
				flag = 0;
				labelPanel.setVisible(false);
				searchField.setVisible(true);
			}
			repaint();
		}
	}

	private class FinishAction implements ActionListener {
		int fff = 0;

		public void actionPerformed(ActionEvent e) {

			int i = 0;
			modifiedNames[modifiedWordsIndex] = allNames[modifiedWordsIndex];
			modifiedWords[modifiedWordsIndex] = "";
			while (singleWords[i] != null) {
				modifiedWords[modifiedWordsIndex] = modifiedWords[0] + singleWords[i];
				i++;
			}
			modifiedWords[modifiedWordsIndex] = modifiedWords[modifiedWordsIndex] + "*";
			modifiedWordsIndex++;
			if (modifiedWords[0].length() <= 1) {
				if (fff == 0) {
					inputInterfaceWord.setText("你还没有输入任何单词");
					inputInterfaceMean.setText("想复习再按一次FINISH");
					fff = -1;
					return;
				}
				modifiedWords[modifiedWordsIndex] = null;
				modifiedWordsIndex--;
			}

			if (allWords[reviewWordsIndex[reviewWordIndex]].indexOf(58, beginPoint) == -1) {
				reviewWordIndex++;
				beginPoint = 0;
				if (reviewWordIndex == reviewWordsIndex.length) {
					reviewWord.setText("结束了!");
				}
			}
			while (allWords[reviewWordsIndex[reviewWordIndex]].indexOf(":", beginPoint) != -1) {

				endPoint = allWords[reviewWordsIndex[reviewWordIndex]].indexOf(":", beginPoint);
				JButton b = createButton(allWords[reviewWordsIndex[reviewWordIndex]].substring(beginPoint, endPoint),
						-1, -1, -1, -1, secondBackgroundColor, labelButtonColor);
				b.setFont(labelButtonFont);
				labelPanel.add(b);
				labelPanel.revalidate();
				beginPoint = endPoint + 1;
				beginPoint = allWords[reviewWordsIndex[reviewWordIndex]].indexOf("|", beginPoint) + 1;
			}

			beginPoint = 0;
			endPoint = allWords[reviewWordsIndex[reviewWordIndex]].indexOf(":", beginPoint);
			reviewWord.setText(allWords[reviewWordsIndex[reviewWordIndex]].substring(beginPoint, endPoint));
			beginPoint = endPoint + 1;
			endPoint = allWords[reviewWordsIndex[reviewWordIndex]].indexOf("|", beginPoint);
			reviewMean.setText(allWords[reviewWordsIndex[reviewWordIndex]].substring(beginPoint, endPoint));

			mainPanel.removeAll();
			sidePanel.removeAll();
			mainPanel.add(close);
			sidePanel.setLayout(null);
			sidePanel.add(labelPanel);
			labelPanel.setVisible(false);
			sidePanel.add(searchField);
			labelListSwitch.setText(allNames[reviewWordsIndex[reviewWordIndex]].substring(11, 20));
			labelListSwitch.addActionListener(new LabelListSwitchAction());
			mainPanel.add(labelListSwitch);
			mainPanel.add(reviewWord);
			mainPanel.add(mean);
			mainPanel.add(pointButton);
			pointButton.requestFocus();
			repaint();

		}
	}

	private class CloseAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				MyIO.finishWork(modifiedWords, modifiedNames);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.exit(0);
		}
	}

	private class AddAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			allWords[reviewWordsIndex[reviewWordIndex]] = allWords[reviewWordsIndex[reviewWordIndex]].substring(0,
					endPoint + 1) + allWords[reviewWordsIndex[reviewWordIndex]].substring(endPoint);
			endPoint += 1;
			beginPoint = endPoint;
			mainPanel.remove(reviewWord);
			mainPanel.remove(reviewMean);
			mainPanel.remove(modify);
			pointButton.setEnabled(false);
			mainPanel.remove(add);
			mainPanel.add(reviewInterfaceWord);
			mainPanel.add(reviewInterfaceMean);

		}
	}

	private class SetAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			endPoint = allWords[reviewWordsIndex[reviewWordIndex]].indexOf("|", endPoint);
			String forepart = allWords[reviewWordsIndex[reviewWordIndex]].substring(0, beginPoint);
			String backpart = allWords[reviewWordsIndex[reviewWordIndex]].substring(endPoint);
			allWords[reviewWordsIndex[reviewWordIndex]] = forepart + reviewInterfaceWord.getText() + ":"
					+ reviewInterfaceMean.getText() + backpart;
			System.out.println(allWords[reviewWordsIndex[reviewWordIndex]]);
			modifiedWords[modifiedWordsIndex] = allWords[reviewWordsIndex[reviewWordIndex]];
			System.out.println(modifiedWords[modifiedWordsIndex] + modifiedWordsIndex);
			modifiedNames[modifiedWordsIndex] = allNames[reviewWordsIndex[reviewWordIndex]];
			modifiedWordsIndex++;

			endPoint = allWords[reviewWordsIndex[reviewWordIndex]].indexOf(":", beginPoint);
			reviewWord.setText(allWords[reviewWordsIndex[reviewWordIndex]].substring(beginPoint, endPoint));
			beginPoint = endPoint + 1;
			endPoint = allWords[reviewWordsIndex[reviewWordIndex]].indexOf("|", beginPoint);
			reviewMean.setText(allWords[reviewWordsIndex[reviewWordIndex]].substring(beginPoint, endPoint));

			mainPanel.remove(reviewInterfaceWord);
			mainPanel.remove(reviewInterfaceMean);
			mainPanel.add(reviewWord);
			mainPanel.add(mean);
			pointButton.setEnabled(true);
			pointButton.requestFocus();
			mainPanel.remove(set);
			repaint();
		}
	}

	private class LabelButtonListener implements ActionListener {

		private int i;

		public LabelButtonListener(int i) {
			this.i = i;
		}

		public void actionPerformed(ActionEvent e) {
			inputInterfaceWord.setText("");
			inputInterfaceMean.setText("");
			flag = i;
			int stringIndex = singleWords[i].indexOf(58);
			inputInterfaceWord.setText(singleWords[i].substring(0, stringIndex));
			inputInterfaceMean.setText(singleWords[i].substring(stringIndex + 1, singleWords[i].length() - 1));
		}

	}

	private class PointButtonKeyListener extends KeyAdapter {

		public void keyPressed(KeyEvent e) {
			//System.out.print("!!");
			if ((e.getKeyCode() == KeyEvent.VK_DOWN) || (e.getKeyCode() == KeyEvent.VK_RIGHT)
					|| (e.getKeyCode() == KeyEvent.VK_SPACE)) {
				if (allWords[reviewWordsIndex[reviewWordIndex]].indexOf(":", beginPoint) == -1) {
					reviewWordIndex++;
					if (reviewWordIndex == reviewWordsIndex.length) {
						reviewWord.setText("结束了!");
						mainPanel.remove(mean);
						mainPanel.remove(pointButton);
						mainPanel.remove(reviewMean);
						repaint();
						return;
					}
					beginPoint = 0;
					endPoint = 0;
					labelPanel.removeAll();
					while (allWords[reviewWordsIndex[reviewWordIndex]].indexOf(":", beginPoint) != -1) {

						endPoint = allWords[reviewWordsIndex[reviewWordIndex]].indexOf(":", beginPoint);
						JButton b = createButton(
								allWords[reviewWordsIndex[reviewWordIndex]].substring(beginPoint, endPoint), -1, -1, -1,
								-1, secondBackgroundColor, labelButtonColor);
						b.setFont(labelButtonFont);
						labelPanel.add(b);
						labelPanel.revalidate();
						beginPoint = endPoint + 1;
						beginPoint = allWords[reviewWordsIndex[reviewWordIndex]].indexOf("|", beginPoint) + 1;
					}
					beginPoint = 0;
					endPoint = 0;
					labelListSwitch.setText(allNames[reviewWordsIndex[reviewWordIndex]].substring(11, 20));
				} else
					beginPoint = endPoint + 1;

				endPoint = allWords[reviewWordsIndex[reviewWordIndex]].indexOf(":", beginPoint);
				reviewWord.setText(allWords[reviewWordsIndex[reviewWordIndex]].substring(beginPoint, endPoint));
				beginPoint = endPoint + 1;
				endPoint = allWords[reviewWordsIndex[reviewWordIndex]].indexOf("|", beginPoint);
			//	System.out.println(
					//	"往后" + beginPoint + ";" + endPoint + ";" + allWords[reviewWordsIndex[reviewWordIndex]]);

				reviewMean.setText(allWords[reviewWordsIndex[reviewWordIndex]].substring(beginPoint, endPoint));

				mainPanel.add(mean);
				mainPanel.remove(reviewMean);
				mainPanel.remove(add);
				mainPanel.remove(modify);
				repaint();

			}

			else if ((e.getKeyCode() == KeyEvent.VK_LEFT) || (e.getKeyCode() == KeyEvent.VK_UP)) {
				if ((allWords[reviewWordsIndex[reviewWordIndex]].lastIndexOf(":", beginPoint - 2) == -1)
						|| (allWords[reviewWordsIndex[reviewWordIndex]].lastIndexOf("|", endPoint - 1) == -1)) {
					reviewWordIndex--;
					if (reviewWordIndex == -1) {
						reviewWord.setText("这是第一个了!");
						reviewWordIndex = 0;
						repaint();
						return;
					}
					beginPoint = 0;
					endPoint = 0;
					labelPanel.removeAll();
					while (allWords[reviewWordsIndex[reviewWordIndex]].indexOf(":", beginPoint) != -1) {

						endPoint = allWords[reviewWordsIndex[reviewWordIndex]].indexOf(":", beginPoint);
						JButton b = createButton(
								allWords[reviewWordsIndex[reviewWordIndex]].substring(beginPoint, endPoint), -1, -1, -1,
								-1, secondBackgroundColor, labelButtonColor);
						b.setFont(labelButtonFont);
						labelPanel.add(b);
						labelPanel.revalidate();
						beginPoint = endPoint + 1;
						beginPoint = allWords[reviewWordsIndex[reviewWordIndex]].indexOf("|", beginPoint) + 1;
					}
					beginPoint = 0;
					endPoint = 0;
					labelListSwitch.setText(allNames[reviewWordsIndex[reviewWordIndex]].substring(11, 20));
					endPoint = allWords[reviewWordsIndex[reviewWordIndex]].indexOf("*");
					beginPoint = endPoint;
				}

				beginPoint = allWords[reviewWordsIndex[reviewWordIndex]].lastIndexOf(":", beginPoint - 2) + 1;
				endPoint = allWords[reviewWordsIndex[reviewWordIndex]].lastIndexOf("|", endPoint - 1);
			//	System.out.println(
						//"往前" + beginPoint + ";" + endPoint + ";" + allWords[reviewWordsIndex[reviewWordIndex]]);

				reviewMean.setText(allWords[reviewWordsIndex[reviewWordIndex]].substring(beginPoint, endPoint));
				beginPoint = allWords[reviewWordsIndex[reviewWordIndex]].lastIndexOf("|", beginPoint) + 1;
				endPoint = allWords[reviewWordsIndex[reviewWordIndex]].lastIndexOf(":", endPoint);
				reviewWord.setText(allWords[reviewWordsIndex[reviewWordIndex]].substring(beginPoint, endPoint));
				beginPoint = endPoint + 1;
				endPoint = allWords[reviewWordsIndex[reviewWordIndex]].indexOf("|", endPoint);

				mainPanel.add(mean);
				mainPanel.remove(reviewMean);
				mainPanel.remove(add);
				mainPanel.remove(modify);
				repaint();

			}

		}

	}

	private class InputInterfaceKeyListener extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == inputInterfaceWord) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					inputInterfaceMean.requestFocus();
			} else if (e.getSource() == inputInterfaceMean) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (inputInterfaceWord.getText().length() == 0) {
						inputInterfaceWord.setText("Please input a word");
						return;
					}
					if (flag == -1) {
						singleWords[index] = inputInterfaceWord.getText() + ":" + inputInterfaceMean.getText() + "|";
						labelbutton[index] = createButton(inputInterfaceWord.getText(), -1, -1, -1, -1,
								secondBackgroundColor, labelButtonColor);
						labelbutton[index].setFont(new Font("宋体", Font.CENTER_BASELINE, 17));
						labelbutton[index].addActionListener(new LabelButtonListener(index));
						sidePanel.add(labelbutton[index]);
						index++;
					} else {
						singleWords[flag] = inputInterfaceWord.getText() + ":" + inputInterfaceMean.getText() + "|";
						labelbutton[flag].setText(inputInterfaceWord.getText());
						flag = -1;
					}
					inputInterfaceWord.setText("");
					inputInterfaceMean.setText("");
					inputInterfaceWord.requestFocus();
					sidePanel.revalidate();
					sidePanel.repaint();

				}

			}
		}
	}
}
