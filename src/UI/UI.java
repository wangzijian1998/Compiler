package UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

import common.Error;
import common.Node;
import common.treeNode;
import lex.scanner;
import parser.Parser;
import parser.drawTree;

public class UI extends JFrame{
	
//	JPanel jPanel;
	JPanel common;
	JPanel console;
	String filePath;
	public UI() {
		JPanel jPanel = new JPanel();
		GridBagLayout gridBagLayout = new GridBagLayout();
		console = getConsole();
		common = new JPanel();
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 500;
		gridBagConstraints.weighty = 400;
		gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
		gridBagLayout.setConstraints(common, gridBagConstraints);
		jPanel.setLayout(gridBagLayout);
		
		
		getFile(jPanel);
		add(jPanel);
		
	}
	
//	public JPanel getUI() {
//		return jPanel;
//	}
	
	public void scanner(JPanel jPanel) {
		
	}
	
	public void getFile(JPanel jPanel) {
		GridBagLayout gridBagLayout = new GridBagLayout();
		GridBagConstraints gr1 = new GridBagConstraints();
		gr1.gridwidth = GridBagConstraints.REMAINDER;
		
		
		JLabel jl1 = new JLabel("请输入SNL程序文件路径：");
		JTextField filename = new JTextField(18);
		JButton button = new JButton("确定");
		JButton scanner = new JButton("词法分析");
		JButton parser = new JButton("语法分析");
		scanner.setEnabled(false);
		parser.setEnabled(false);
		
		gridBagLayout.setConstraints(jl1, gr1);
		gridBagLayout.setConstraints(filename, gr1);
		gridBagLayout.setConstraints(button, gr1);
		common.setLayout(gridBagLayout);
		
		common.add(jl1);
		common.add(filename);
		common.add(button);
		common.add(scanner);
		common.add(parser);
		common.setBackground(Color.white);
		common.setBorder(new LineBorder(Color.black));
		jPanel.add(common);
		jPanel.add(console);
		
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				filePath = filename.getText();
				
				try {
					FileReader file = new FileReader( filePath ) ;
//					console.removeAll();
					console.updateUI();
					console.repaint();
					JLabel success_open = new JLabel("   文件打开成功！");
					JLabel success_read = new JLabel("   文件读取成功！");
					JLabel success_wait = new JLabel("   ......");
					
					console.add(success_open);
					console.add(success_read);
					console.add(success_wait);
					file.close();
					scanner.setEnabled(true);
					
//					scanner_analyse(jPanel, filePath);
					
				} catch (Exception e) {
					// TODO: handle exception
					JLabel Error_msg = new JLabel("   " + e.getMessage());
					Error_msg.setForeground(Color.red);
					console.removeAll();
					console.updateUI();
					console.repaint();
					console.add(Error_msg);
				}
			}
		});
		scanner.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				console.updateUI();
				console.repaint();
				JLabel success_scanner = new JLabel("   词法转换完成！");
				console.add(success_scanner);
				scanner_analyse(jPanel, filePath);
				parser.setEnabled(true);
			}
		});
		parser.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				console.updateUI();
				console.repaint();
				JLabel success_parser = new JLabel("   语法转换完成！");
				JLabel parser_tree = new JLabel("   语法树生成");
				console.add(success_parser);
				console.add(parser_tree);
				
			}
		});
	}
	
	public void parser_analyse(JPanel jPanel, String filePath) {
		
		try {
//			common.removeAll();
//			common.updateUI();
//			common.repaint();
//			treeNode root = new treeNode() ;	
//			Parser parser = new Parser() ;
//			root = parser.getTree( filePath ) ;
//			drawTree tree = new drawTree();
//			JLabel parser_label = tree.getTree(root);
//			JScrollPane pane = new JScrollPane(parser_label);
//			pane.repaint();
//			common.add(pane);
//			common.updateUI();
//			common.repaint();
			treeNode root = new treeNode();
			Parser parser = new Parser();
			root = parser.getTree(filePath);
			if (Error.getFlag()){
				console.updateUI();
				console.repaint();
				JLabel error = new JLabel("   语法错误  行：" + Error.getLine() + "  列：" + Error.getRow());
				error.setForeground(Color.RED);
				console.add(error);
			}else {
				drawTree tree = new drawTree();
				tree.drawtree(root);
				System.out.print(tree.getWidth());
				BufferedImage  bi = new BufferedImage(tree.getWidth(), tree.getHeight(), BufferedImage.TYPE_INT_ARGB);
				System.out.print(bi);
				Graphics2D  g2d = bi.createGraphics();
				tree.paint(g2d);
				ImageIO.write(bi, "PNG", new File("/Users/wangzj/eclipse-workspace/workplace2/编译原理/frame.png"));
			}
				
			
			
		} catch (Exception e) {
			// TODO: handle exception
			JLabel err_msg = new JLabel(e.getMessage());
			err_msg.setForeground(Color.red);
			console.updateUI();
			console.repaint();
			console.add(err_msg);
		}
		
		
		
	}
	
	public void scanner_analyse(JPanel jPanel, String filePath) {
		scanner sc = new scanner();
		try {
			ArrayList<Node> TokenList = sc.getTokenList(filePath);
			Iterator<Node> iterator = TokenList.iterator();
			common.removeAll();
			common.updateUI();
			common.repaint();
			
			JButton scanner = new JButton("词法分析");
			JButton parser = new JButton("语法分析");
			
			scanner.setBounds(50, 10, 100, 20);
			parser.setBounds(200, 10, 100, 20);
			
			
			scanner.setEnabled(false);
			
//			GridBagLayout gridBagLayout = new GridBagLayout();
//			GridBagConstraints gr1 = new GridBagConstraints();
//			
//			
//			gr1.weightx = 11;
//			gr1.weighty = 11;
//			gridBagLayout.setConstraints(scanner, gr1);
//			
//			gr1.weightx = 11;
//			gridBagLayout.setConstraints(parser, gr1);
//			
//			gr1.gridwidth = GridBagConstraints.REMAINDER;
//			
//			common.setLayout(gridBagLayout);
			common.setLayout(null);
			common.add(scanner);
			common.add(parser);
			
			
			int size = TokenList.size();
			String[] title = {"Line","Type","Data"};
			String[][] data = new String[size][3]; 
			
			int i = 0;
			while (iterator.hasNext()) {
				
				Node node = iterator.next();
				data[i][0] = String.valueOf(node.getLine());
				data[i][1] = String.valueOf(node.getType());
				data[i][2] = node.getData();
				i++;
			}
			JTable jTable = new JTable(data, title);
			
			JScrollPane pane = new JScrollPane(jTable);
			pane.setBounds(50,50,400,400);
			common.add(pane);
			parser.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					common.removeAll();
					common.updateUI();
					common.repaint();
					parser_analyse(jPanel, filePath);
				}
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("error");
		}
		
		
		
	}
	
	public JPanel getConsole() {
		JPanel console = new JPanel();
		JLabel label = new JLabel("   Console");
		label.setFont(new Font("Serief", Font.BOLD, 20));
		console.setLayout(new GridLayout(10, 1));
		console.setPreferredSize(new Dimension(600, 300));
		console.setBorder(new LineBorder(Color.BLACK));
		console.setBackground(Color.white);
		console.add(label);
		return console;
	}

}
