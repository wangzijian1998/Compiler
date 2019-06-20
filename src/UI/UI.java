package UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;

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

import common.Node;
import lex.scanner;

public class UI extends JFrame{
	
//	JPanel jPanel;
	JPanel common;
	JPanel console;
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
				String filePath = filename.getText();
				try {
					FileReader file = new FileReader( filePath ) ;
					console.removeAll();
					console.updateUI();
					console.repaint();
					JLabel success_open = new JLabel("文件打开成功！");
					JLabel success_read = new JLabel("文件读取成功！");
					JLabel success_wait = new JLabel("......");
					console.add(success_open);
					console.add(success_read);
					console.add(success_wait);
					file.close();
					scanner_analyse(jPanel, filePath);
					
				} catch (Exception e) {
					// TODO: handle exception
					JLabel Error_msg = new JLabel(e.getMessage());
					console.removeAll();
					console.updateUI();
					console.repaint();
					console.add(Error_msg);
				}
			}
		});
	}
	
	public void scanner_analyse(JPanel jPanel, String filePath) {
		scanner sc = new scanner();
		try {
			ArrayList<Node> TokenList = sc.getTokenList(filePath);
			Iterator<Node> iterator = TokenList.iterator();
			common.removeAll();
			common.updateUI();
			common.repaint();
			
			int size = TokenList.size();
			String[] title = {"Line","Name","??"};
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
			common.add(pane);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("error");
		}
		
		
	}
	
	public JPanel getConsole() {
		JPanel console = new JPanel();
		JLabel label = new JLabel("console");
		console.setLayout(new FlowLayout());
		console.setPreferredSize(new Dimension(600, 300));
		console.setBorder(new LineBorder(Color.BLACK));
		console.setBackground(Color.white);
		console.add(label);
		return console;
	}

}
