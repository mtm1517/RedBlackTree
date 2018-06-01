package sjsu.Vu.cs146.project3;

public class Node  
{
	protected static final boolean RED = false;
	protected static final boolean BLACK = true;
	
	protected Node left, right;
	protected String data;
	protected boolean color;
	
	public Node(String data, Node left,Node right)
	{
		this.left = left;
		this.right = right;
		this.data = data;
		this.color = BLACK;
	}
	
	public Node(String data)
	{
		this(data,null,null);
	}
	
	public int compareTo(Node node)
	{
		return this.data.compareTo(node.data);
	}
	
	
}

