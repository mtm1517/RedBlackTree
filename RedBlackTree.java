package sjsu.Vu.cs146.project3;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashSet;
import java.util.Iterator;

public class RedBlackTree {

    protected Node root; //a root Node
    private static final int BLACK = 1; //black equal to 1
    private static final int RED = 0; //red equal to zero
    private String result = "";
    

  //create a Node class inside of RedBlackTree class to save up space
    class Node { 
        public String data; //value of node
        public Node left, right; //left and right nodes
        public Node parent; //a parent node
        int color; //color of node

      //create a new node
        Node(String data) {  
            this.data = data;
            this.left = null;
            this.right = null;
            this.color = RED;
        }
    }

    
    // method that returns a sibling of a node
    private Node getSibling(Node node) {
        if (node.parent == null || node.parent.left == null || node.parent.right == null) return null;
        else {
            if (node == node.parent.left)
                return node.parent.right;
            else
                return node.parent.left;
        }
    }

    //returns aunt of a node, which is a sibling of a parent
    private Node getAunt(Node node) {
        return getSibling(node.parent);
    }

    
    //returns parent a node
    
    private Node getParent(Node node) {
        if (node.parent != null) {
            return node.parent;
        } else return null;
    }

    
     //returns a grandparent of a node
    private Node getGrandParent(Node node) {
        if (node.parent != null) {
            return node.parent.parent;
        } else return null;
    }

    //starts a search of a node
    private boolean search(String data) {
        if (search(root, data)) return true;
        else return false;
    }

    
    //method that handles the logic of search
    private boolean search(Node node, String data) {
        boolean found;
        found = false;
        while (node != null && !found) {
            if (node.data.compareTo(data) > 0)
                node = node.left;
            else if (node.data.compareTo(data) < 0)
                node = node.right;
            else {
                found = true;
                break;
            }
            found = search(node, data);
        }


        return found;
    }

    // print tree
    public void preOrderVisit(Visitor v) {
         preOrderVisit(root, v);
    }

    private String preOrderVisit(Node node, Visitor v) {
        if (node == null);
        String result = node.data;
        //System.out.println(result);
      //  System.out.print(result);
        preOrderVisit(node.left, v);
        preOrderVisit(node.right,v );
        return result;
    }

    
     
   // JUnit Test for Data
    protected String specialPreOrderVisitForJUnit (Node node) {
        if (node == null) return "";
        result += node.data;
        specialPreOrderVisitForJUnit(node.left);
        specialPreOrderVisitForJUnit(node.right);
        return result;
    }
    
    // JUnit Test for Color
    protected String specialPreOrderVisitColorForJUnit (Node node)
    {
    	if (node == null) return "";
        result += node.color;
       
        specialPreOrderVisitColorForJUnit(node.left);
        specialPreOrderVisitColorForJUnit(node.right);
        return result;
    }
    
    // JUnit Test for Parent
    protected String specialPreOrderVisitParentForJUnit (Node node)
    {
    	if (node == null) return "";
    	else if(node == root)
    	{
    		result = "Color: "+node.color+", Key:"+node.data+" Parent: \n";
    	}
    	else
    		result += "Color: "+node.color+", Key:"+node.data+" Parent: "+node.parent.data+"\n";
        
       
    	specialPreOrderVisitParentForJUnit(node.left);
    	specialPreOrderVisitParentForJUnit(node.right);
        return result;
    }

    
     //insert node and data into a tree
    public void insert(String data) {
        root = insert(root, data);
    }

    private Node insert(Node node, String data) 
    {
        Node temp = null;
        Node insertNode = new Node(data);
        while (node != null) {
            temp = node;
            if (data.compareTo(node.data) < 0)
                node = node.left;
            else
                node = node.right;
        }
        insertNode.parent = temp;
        if (temp == null) {
            root = insertNode;
            insertNode.color = BLACK;//black root
            return root;
        } else {
            if (insertNode.data.compareTo(temp.data) < 0)
                temp.left = insertNode;
            else
                temp.right = insertNode;
        }
        insertNode.left = null;
        insertNode.right = null;
        insertNode.color = RED;
        fixTree(insertNode);
        return root;

    }

    // fix Tree
    private Node fixTree(Node node) {

        Node parentCurrent;
        parentCurrent = getParent(node);
        Node greatParent;
        greatParent = getGrandParent(node);
        Node aunt;
        aunt = getAunt(node);

        if (parentCurrent == null) {
            return null;
        } else {
            if (parentCurrent.color == BLACK)
                return node;
            else if (parentCurrent.color == RED) {
                if (aunt == null || aunt.color == BLACK)
                    if (node != parentCurrent.right || parentCurrent != greatParent.left) {
                        if (node == parentCurrent.left && parentCurrent == greatParent.right) {
                            Node currentParent = node.parent;
                            node.parent = currentParent.parent;

                            currentParent.parent.right = node;

                            Node currentRight = node.right;
                            currentRight.parent = currentParent;
                            currentParent.left = currentRight;

                            node.right = currentParent;
                            currentParent.parent = node;

                            rotateLeft(node.right);
                        } else {
                            if (parentCurrent.left == node && greatParent.left == parentCurrent) {
                                rotateRight(node);
                            } else if (parentCurrent.right == node && greatParent.right == parentCurrent)
                                rotateLeft(node);
                        }
                    } else {
                        Node currentParent = node.parent;
                        node.parent = currentParent.parent;
                        currentParent.parent.left = node;

                        Node currentLeft = node.left;
                        currentLeft.parent = currentParent;
                        currentParent.right = currentLeft;
                        node.left = currentParent;
                        currentParent.parent = node;


                        rotateRight(node.left);
                    }
                else {
                    if (aunt != null && aunt.color == RED) {
                        parentCurrent.color = BLACK;
                        aunt.color = BLACK;
                        if (greatParent == root) {
                            return root;
                        }
                        greatParent.color = RED;
                        return fixTree(greatParent);
                    }
                }
            }
        }
        return root;
    }

   // rotate Left Tree
    private void rotateLeft(Node node) {
        Node parentCurrent;
        parentCurrent = getParent(node);
        Node greatParent;
        greatParent = getGrandParent(node);
        if (parentCurrent != null) {
            parentCurrent.color = BLACK;
        }
        if (greatParent != null) {
            greatParent.color = RED;
        }

        if (greatParent != null) {
            if (parentCurrent != null) {
                greatParent.right = parentCurrent.left;
            }
        }

        if (parentCurrent != null) {
            if (parentCurrent.left == null) {
            } else {
                parentCurrent.left.parent = greatParent;
            }
        }

        if (parentCurrent != null) {
            parentCurrent.left = parentCurrent.parent;
        }

        if (parentCurrent != null) {
            if (greatParent != null) {
                parentCurrent.parent = greatParent.parent;
            }
        }

        if (greatParent != null) {
            if (greatParent.parent != null) {
                if (parentCurrent.parent == greatParent.parent.left)
                    greatParent.parent.left = parentCurrent;
                else
                    greatParent.parent.right = parentCurrent;
            } else root = parentCurrent;
        }

        if (greatParent != null) {
            greatParent.parent = parentCurrent;
        }
    }

    // rotate Right Tree
    private void rotateRight(Node node) {
        Node parentCurrent;
        parentCurrent = getParent(node);
        Node greatParent;
        greatParent = getGrandParent(node);
        if (parentCurrent != null) {
            parentCurrent.color = BLACK; //black
        }
        if (greatParent != null) {
            greatParent.color = RED; //red
        }

        if (greatParent != null) {
            if (parentCurrent != null) {
                greatParent.left = parentCurrent.right;
            }
        }
        if (parentCurrent != null && parentCurrent.right != null) {
            parentCurrent.right.parent = greatParent;
        }

        if (parentCurrent != null) {
            parentCurrent.right = parentCurrent.parent;
        }

        if (parentCurrent != null) {
            if (greatParent != null) {
                parentCurrent.parent = greatParent.parent;
            }
        }

        if (greatParent != null) {
            if (greatParent.parent == null)
                root = parentCurrent;
            else {
                if (parentCurrent != null) {
                    if (parentCurrent.parent == greatParent.parent.left)
                        greatParent.parent.left = parentCurrent;
                    else
                        greatParent.parent.right = parentCurrent;
                }
            }
        }

        if (greatParent != null) {
            greatParent.parent = parentCurrent;
        }

    }
    
    public interface Visitor {
        void visit(Node n);
    }

    
  //add tester for spell checker
  	public static String makeString(RedBlackTree t)
  	{
  		
  		class MyVisitor implements RedBlackTree.Visitor 
  		{
  			
  			String result = "";
  			public void visit(RedBlackTree.Node n)
  			{
  				
  				result = result + n.data;
  				System.out.println(result);
  			}
  		};
  	
  		System.out.println("-----------");
  		MyVisitor v = new MyVisitor();
  		
  		t.preOrderVisit(v);
  		System.out.println(v.result);
  		return v.result;
  	}
  	
  	public static String makeStringDetails(RedBlackTree t) {
  		{
  		       class MyVisitor implements RedBlackTree.Visitor {
  		          String result = "";
  		          public void visit(RedBlackTree.Node n)
  		          {
  		             if(!(n.data).equals(""))
  		                   result = result +"Color:" +n.color+", Key:"+n.data+" Parent: "+ n.parent.data+"\n";
  		          }
  		       };
  		       MyVisitor v = new MyVisitor();
  		       t.preOrderVisit(v);
  		       return v.result;
  		 }
  		}
    
    
   // main method
    public static void main(String[] args) 
    {

        File file = new File("src//sjsu//Vu//cs146//project3//dictionary.txt");
        File poem = new File("src//sjsu//Vu//cs146//project3//Caged Bird.txt");
        
        boolean searchResult;
  
        RedBlackTree dictionary = new RedBlackTree();
        try {
            
        	// read dictionary file
        	Scanner scanner = new Scanner(file);
            long currentTime = System.currentTimeMillis();
            while (scanner.hasNext()) {
                String string = scanner.nextLine();
                dictionary.insert(string);
            }
            long endingTime = System.currentTimeMillis();
            scanner.close();
            int totalTime = (int) (endingTime - currentTime);
            System.out.println("The total time to insert is: " + totalTime + " ms");

            
           
            // read Poem file
            Scanner scannerPoem = new Scanner(poem);
            HashSet<String> set = new HashSet<String>();
           
            currentTime = System.currentTimeMillis();
            System.out.println("The words are not in the dictionary!");
            while(scannerPoem.hasNext()) {
            	String stringPoem = scannerPoem.nextLine();
            	stringPoem = stringPoem.replace(","," ");
            	stringPoem = stringPoem.replace("."," ");
            	String[] words = stringPoem.split(" ");
 
            	  for(String i: words)
                  {
            		  // search node
            		  searchResult = dictionary.search(i);
            		  if(!searchResult)
            			  // Add words to HashSet
            			 // set.add(i);
            		  {
            			  System.out.println(i);
            		  }
                  }
            }
            
            endingTime = System.currentTimeMillis();
            scannerPoem.close();
            
            //Iterator<String> itr = set.iterator();
            
            long totalTimeMs = (endingTime - currentTime);  
            System.out.println("The time to search the words in Dictionary " + totalTimeMs + " ms");
            //System.out.println("The words are not in the dictionary!");
            // print out the words aren't in the dictionary from HashSet
            /*while(itr.hasNext())
            	System.out.println(itr.next());
            	*/
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

   
}