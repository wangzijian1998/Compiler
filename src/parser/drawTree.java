package parser;
 
import common.treeNode;
import javax.swing.* ;
 
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics ;
 
public class drawTree extends JFrame {
	
	static int  X ;
	static int  Space = 30 ;
	static int  Width , High ;
	static NewPanel Panel ;
	
	public static void main( String[] args ) throws Exception {
		
		treeNode root = new treeNode() ;	
		
		Parser parser = new Parser() ;
		
		root = parser.getTree( "/Users/wangzj/eclipse-workspace/workplace2/编译原理/file.txt" ) ;
		
		drawtree( root ) ;
	}
	
	public  drawTree() {
		Panel = new NewPanel() ;
		Panel.setPreferredSize( new Dimension( Width , High ) ) ;
		JScrollPane pane = new JScrollPane( Panel );
		add( pane ) ;
	}
	
	public  static int getTreeInf( treeNode root , int Y ) {
		int  temp , Length , width = 0 ;			
		String str = "" ;
		
		if( root.getflag() == 0 || ( root.getchildNum() == 0 ) ) {
			if( root.getflag() == 0 ) str += root.getData() ;
			else str += root.getNonTerminal() ;
			Length = str.length() * 8 ;
			width = Length + Space ;
			root.setLength( Length ) ;
			root.setWidth( width ) ;
			root.setX( X ) ;
			root.setY( Y ) ;
			X += width ;
		}
		else {
			str += root.getNonTerminal() ;
			Length = str.length() * 8 ;
			root.setLength( Length ) ;
			root.setY( Y ) ;
			temp = X ;
			for( int i = 0 ; i < root.getchildNum() ; i ++ ) {
				width += getTreeInf( root.getChild( i ) , Y + 150 ) ;
			}
			root.setX( temp + width / 2 - Length / 2 ) ;
			if( width < Length ) {
				width = Length / 2 + width / 2 ;
				X += Length - width + Space ;
			}
			root.setWidth( width ) ;
			
		}
		return width ;
	}
	
	public  static void drawtree( treeNode root ) {
		X = 20 ;
		High = getTreeInf( root , 20 ) ;
		Width = X ;
		Panel.Root = root ;
		
		drawTree frame = new drawTree() ;
		frame.setTitle( "语法分析树" ) ;
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE ) ;
		frame.setSize( 800 , 600 ) ;
		frame.setVisible( true ) ;
	}
}
 
class NewPanel extends JLabel {
	
	public static treeNode Root ;
	
	public void draw( treeNode root , Graphics g ) {
		g.drawRect( root.getX() - 3 , root.getY() - 15 , root.getLength() + 1 , 20 ) ;
		if( root.getflag() == 0 ) {
			g.drawString( root.getData() , root.getX() , root.getY() ) ;
		}
		else {
			g.drawString( "" + root.getNonTerminal() , root.getX() , root.getY() ) ;
			if( root.getchildNum() != 0 ) {
				for( int i = 0 ; i < root.getchildNum() ; i ++ ) {
					g.drawLine( root.getX() + root.getLength() / 2 , root.getY() + 5  , root.getChild(i).getX() + root.getChild(i).getLength() / 2 , root.getChild(i).getY() - 15 ) ;
					draw( root.getChild( i ) , g ) ;
				}
			}
		}
	}
	
	public void paint( Graphics g ) {
		super.paint( g ) ;
		g.setColor( Color.white ) ;
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor( Color.blue ) ;
		draw( Root , g ) ;
	}
}
