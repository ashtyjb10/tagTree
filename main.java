package assignment05;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class main {

	public static void main(String[] args) throws FileNotFoundException 
	{
		
		Scanner s = new Scanner(new FileReader(new File("Resources/isTwoTree.txt")));
		TagTree t = new TagTree(s);
//		System.out.println(t.getHeight());
//		System.out.println(t.getMaxDegree());
//		System.out.println(t.outputPrefix());
//		System.out.println(t.outputPostfix());
//		System.out.println(t.isTwoTree());
//		System.out.println(t.isBinaryTree());
//		System.out.println(t.isFullBinaryTree());
		System.out.println(t.findDepth("red"));
	}

}
