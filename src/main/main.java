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
//		GridBagLayout gridBagLayout = new GridBagLayout();
//		GridBagConstraints gridBagConstraints = new GridBagConstraints();
//		gridBagConstraints.fill = GridBagConstraints.BOTH;
//		frame.setLayout(gridBagLayout);
//		JPanel common = frame.getUI();
		JPanel console = frame.getConsole();
//		gridBagConstraints.weightx = 500;
//		gridBagConstraints.weighty = 400;
//		gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
//		gridBagLayout.setConstraints(common, gridBagConstraints);
		
		frame.setTitle("编译原理：王子健 + 宫磊");
		frame.setBounds(500, 800, 600, 800);
		
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE ) ;
		frame.setVisible(true);
		
		
//		String filename = "/Users/wangzj/eclipse-workspace/workplace2/编译原理/file.txt";
//		scanner sc = new scanner();
//		try {
//			ArrayList<Node> TokenList = sc.getTokenList(filename);
//			Iterator<Node> iterator = TokenList.iterator();
//			while (iterator.hasNext()) {
//				Node node = iterator.next();
//				System.out.print(node.getData());
//				System.out.print(node.getType());
//				System.out.println(node.getLine());
//				
//				
//			}
//			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			System.out.println("error");
//		}
	}

}
