package main;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JPanel;

import common.Node;
import lex.scanner;
import UI.UI;

public class main {
	public static void main(String args[]) {
		UI frame = new UI();

		JPanel console = frame.getConsole();
		
		frame.setTitle("编译原理：王子健 + 宫磊");
		frame.setBounds(500, 800, 600, 800);
		
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE ) ;
		frame.setVisible(true);
		
		

	}

}
