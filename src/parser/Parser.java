
package parser;
 
import java.util.*;
 
import  lex.scanner  ; 
import  common.* ;
import  common.Enum  ;
import  common.Error ;
 
public class Parser {
	
	static SNLproduct Product = new SNLproduct() ;
	static SNLpredict Predict = new SNLpredict() ;
	static scanner Scanner = new scanner() ;
	static ArrayList < Node > TokenList = new ArrayList< Node > () ;
	static int cur ;
 
	public Parser() {
		cur = 0 ; 
	}
	
	
	/*****************/
	/*递归地匹配一个非终极符*/
	/*****************/
	public static treeNode match( Enum.nonTerminals NonTerminal , treeNode father ) {
		
		int  i , j , choose = -1 ;
		treeNode root = new treeNode() ;
		Enum.nonTerminals temp ;
		Enum.lexType curLex = TokenList.get(cur).getType() ;
			
		root.setflag( 1 ) ;
		root.setNonTerminal( NonTerminal ) ;
		root.setFather( father ) ;
		
		for( i = 1 ; i <= 104 ; i ++ ) {
			int flag = 0 ; 
			temp = Product.product[i].getHead() ;
			for( j = 0 ; j < Predict.predict[i].getPredictNum() ; j ++ ) {
				if( curLex == Predict.predict[i].getPredict( j ) ) {
					flag = 1 ; 
					break ;
				}
			}
			if(  flag == 1 && temp == NonTerminal ) {
				choose = i ; 
				break ;
			}
		}
		
		if( choose == -1 ) {
			Error.setError( TokenList.get(cur).getLine() , TokenList.get(cur).getRow() , 2 ) ;
			return null ;
		}
		else {
			for( i = 0 ; i < Product.product[choose].getproductNum() ; i ++ ) {
				if( Product.product[choose].getflag( i ) == 0 ) {
					treeNode leaf = new treeNode() ;
					leaf.setFather( father ) ;
					leaf.setflag( 0 ) ;
					leaf.setTerminal( Product.product[choose].getProductTerminal( i ) ) ;
					leaf.setData( TokenList.get( cur ).getData() ) ;
					root.setChild( leaf ) ;
					cur ++ ;
				}
				else {
					treeNode child ;
					Enum.nonTerminals NonTerminals = Product.product[choose].getProductNonterminal(i) ;
					child = match( NonTerminals , root ) ;
					root.setChild( child ) ;
				}
			}
		}
		
		return root ; 
	}
	
	
	/*********************/
	/*得到一个语法分析树，返回树根*/
	/*********************/
	public static treeNode getTree( String filePath ) throws Exception {
		
		TokenList = Scanner.getTokenList( filePath ) ;
		
		if( Error.getFlag() == true ) {
			return null ;
		}
		else {
			treeNode root = new treeNode() ;
			cur = 0 ;
			root = match( Enum.nonTerminals.Program , root ) ;
			if( TokenList.get(cur).getType() != Enum.lexType.ENDFILE ){
				Error.setError( TokenList.get(cur).getLine() , TokenList.get(cur).getRow() , 2 ) ;
			}
			if( Error.getFlag() == true ) {
				Error.printError() ;
				return null ;
			}
			return root ; 
		}
	}
}
