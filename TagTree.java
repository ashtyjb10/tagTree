/**
 * @author ashtonschmidt
 * @date 10/25/17
 */
package assignment05;

import java.util.ArrayList;
import java.util.Scanner;

public class TagTree 
{
	 private Scanner scanner;
	 private Node root;
	
	/**
	 * This will use the scanner object to scan in the tag tree data and build a tag tree data
	 * structure. It is the caller's responsibility to build the Scanner object on some valid source of data. 
	 * @param scanner
	 */
	public TagTree(Scanner scanner)
	{
		this.scanner = scanner;
		if(scanner.hasNext())
		{
			root = parse(scanner.next(), scanner);
		}
		
	}
	
	/**
	 * 
	 * @return the height of the tree.
	 */
	public int getHeight()
	{
		return getHeightHelper(root);
	}
	
	/**
	 * 
	 * @return the maximum degree(max child count)
	 */
	public int getMaxDegree()
	{
		return getMaxDegreeHelp(root);
	}
	
	/**
	 * 
	 * @return the returned string should contain the node names in prefix order (pre-order Traversal)
	 * separated by whitespace.
	 */
	public String outputPrefix()
	{
		return outputPrefixHelp(root, new String());
	}
	
	/**
	 * 
	 * @return a string that contains the node names in postfix order(Post-order traversal order)
	 * seperated by whitespace.
	 */
	public String outputPostfix()
	{
		return outputPostfixHelp(root, new String());
	}
	
	/**
	 * 
	 * @return true if the tree is a binary tree.
	 */
	public boolean isBinaryTree()
	{
		return isBinaryTreeHelper(root);
	}
	/**
	 * 
	 * @return true if this tag tree is a two-tree. otherwise return false
	 */
	public boolean isTwoTree()
	{
		return isTwoTreeHelper(root);
	}
	
	/**
	 * @return true if the tag tree is a full binary tree, and false otherwise.
	 */
	public boolean isFullBinaryTree()
	{
		int maxNumberOfNodes = (int) Math.pow(2, this.getHeight() + 1) - 1;
		int numNodesInTree = isFullBinaryTree(root);
		return maxNumberOfNodes == numNodesInTree;
	}
	
	
	/**
	 * This method searches for the node whos name equals the String and returns the depth of that node.
	 * If the string is not the name of any node -1 is returned. If the string is the name of more than 
	 * one Node the depth of the node that occurred first in the tag data will be returned.
	 * @param s
	 * @return
	 */
	public int findDepth(String key)
	{
		return findDepthHelper(key, root) -1;
	}
	
	//*********Helper methods.
	private Node parse(String beginTag, Scanner s)
	{
		String nameTag = beginTag.charAt(0) + "/" + beginTag.substring(1,beginTag.length()) +">";
		String current = scanner.next();
		current = current.substring(0, current.length()-1);
		Node currentNode = new Node(current);
		while(scanner.hasNext() && current != nameTag)
		{
			current = scanner.next();
			if (current.charAt(0) == '<' && current.charAt(1) != '/')
			{
				//found new tag
				currentNode.children.add(parse(current,scanner));
			}
			else if(current.equals(nameTag))
			{
				//found the end tag return node
				break;
			}

		}
		return currentNode;
		
	}
	
	/**
	 * The recursive method to the getHeight driver.
	 * @param current
	 * @return the height of the tree
	 */
	static private int getHeightHelper(Node current)
	{
		if(current.children.size() == 0)
		{
			//no children at the bottom of the tree
			return 0;
		}
		int maxHeight = 0;
		for(Node nodes: current.children)
		{
			//more children to go
			maxHeight = Math.max(maxHeight, getHeightHelper(nodes));
		}
		return maxHeight + 1;
	}
	
	/**
	 * The recursive method to the getMaxDegree driver.
	 * @param current
	 * @return the Max Degree
	 */
	static private int getMaxDegreeHelp(Node current)
	{
		if(current.children.size() == 0)
		{
			return 0;
		}
		int maxDegree = current.children.size();
		for(Node nodes: current.children)
		{
			maxDegree = Math.max(maxDegree, getMaxDegreeHelp(nodes));
		}
		return maxDegree;
	}
	
	
	/**
	 * The recursive method to the outputPrefix driver.
	 * @param current
	 * @param inOrder
	 * @return a string in pre-order traversal.
	 */
	private String outputPrefixHelp(Node current, String inOrder)
	{
		if(current == root)
		{
			inOrder+= current.data;
		}
		else
		{
			inOrder += " " + current.data;
		}
		for(Node childNode: current.children)
		{
			inOrder= outputPrefixHelp(childNode, inOrder);
		}
		return inOrder;
	}
	
	/**
	 * The recursive method to the outputPostfix driver.
	 * @param current
	 * @param postOrder
	 * @return a string in post-order traversal.
	 */
	private String outputPostfixHelp(Node current, String postOrder)
	{
		for(Node childNode: current.children)
		{
			postOrder = outputPostfixHelp(childNode, postOrder);
		}
		if(current == root)
		{
			postOrder+= current.data;
		}
		else
		{
			postOrder+=  current.data + " ";
		}
		return postOrder;
	}
	
	/**
	 * The recursive method to the isBinaryTree driver.
	 * @param current
	 * @return true if the current node tree is a binary tree.
	 */
	private boolean isBinaryTreeHelper(Node current)
	{
		boolean isBinary = false;
		if (current.children.size() > 2)
		{
			return false;
		}
		if(current.children.size() == 0)
		{
			return true;
		}
		else
		{
			for(Node childNode: current.children)
			{
				isBinary = isBinaryTreeHelper(childNode);
			}
			return isBinary;
		}
	}
	
	/**
	 * The recursive method to the isTwoTree driver.
	 * @param current
	 * @return true if the current root tree is a twoTree.
	 */
	private boolean isTwoTreeHelper(Node current)
	{
		boolean isTwoTree = true;
		if(current.children.size() == 2)
		{
			isTwoTree = true;
		}
		if(current.children.size() == 1 || current.children.size() > 2)
		{
			isTwoTree = false;
		}
		else
		{
			for(Node children: current.children)
			{
				return isTwoTreeHelper(children);
			}
		}
		return isTwoTree;
	}
	
	/**
	 * returns the number of nodes that the tree contains
	 * @param current
	 * @return number of nodes in the tree.
	 */
	private int isFullBinaryTree(Node current)
	{
		if(current.children.size() == 0)
		{
			return 1;
		}
		int numberOfNodes = 0;

		for(Node childNodes: current.children)
		{
			numberOfNodes += isFullBinaryTree(childNodes);
		}

		return numberOfNodes + 1;
	}
	
	/**
	 * The recursive method to the findDepthHelper driver.
	 * @param key
	 * @param current
	 * @return the level of the the key if it is in the tree, otherwise return -1 if not in the tree.
	 */
	private int findDepthHelper(String key, Node current)
	{
		if(!current.data.equals(key) && current.children.size() == 0)
		{
			return 0;
		}
		else if(current.data.equals(key))
		{
			return 1;
		}

		int levels = 0;
		for(Node childNodes: current.children)
		{
			levels = findDepthHelper(key, childNodes);
			if(levels >= 1)// is what the key returns.
			{
				break; //break out of the loop so we dont visit any of the children.
			}
		}
		if(levels == 0)
		{
			return levels; // so that we stay at zero and dont increase levels if there is no correct string.
		}
		return levels + 1;
	}

	
	
	
	//****************************************** Helper nested class.
	static class Node
	{
		String data;
		ArrayList<Node> children = new ArrayList<Node>();
		Node(String data)
		{
			this.data = data;
		}		
	}
}
