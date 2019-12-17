package userinterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MyFrame extends JFrame {
	int height = 600;

	int width = 1152;

	public MyFrame() {
		setBounds(130, 50, width, height);
		setLayout(null);
		setUndecorated(true);
		setDefaultCloseOperation(3);
		setVisible(true);
	}

}
