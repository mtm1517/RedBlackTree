package sjsu.Vu.cs146.project3;

import static org.junit.Assert.*;

import org.junit.Test;

public class RedBlackTreeTest {

	@Test
	public void testNode() {
		RedBlackTree rbt = new RedBlackTree();
		
		rbt.insert("D");
		rbt.insert("B");
		rbt.insert("A");
		rbt.insert("C");
		rbt.insert("F");
		rbt.insert("E");
		rbt.insert("H");
		rbt.insert("G");
		rbt.insert("I");
		rbt.insert("J");
				
		assertEquals("DBACFEHGIJ", rbt.specialPreOrderVisitForJUnit(rbt.root));
				
	}
	
	@Test
	public void testColor() {
		RedBlackTree rbt = new RedBlackTree();
		
		rbt.insert("D");
		rbt.insert("B");
		rbt.insert("A");
		rbt.insert("C");
		rbt.insert("F");
		rbt.insert("E");
		rbt.insert("H");
		rbt.insert("G");
		rbt.insert("I");
		rbt.insert("J");
				
		assertEquals("1111110110", rbt.specialPreOrderVisitColorForJUnit(rbt.root));
		
		}
	
	@Test
	public void testParent() {
		RedBlackTree rbt = new RedBlackTree();
		
		rbt.insert("D");
		rbt.insert("B");
		rbt.insert("A");
		rbt.insert("C");
		rbt.insert("F");
		rbt.insert("E");
		rbt.insert("H");
		rbt.insert("G");
		rbt.insert("I");
		rbt.insert("J");
				
		
		String str=     
		"Color: 1, Key:D Parent: \n"+
		"Color: 1, Key:B Parent: D\n"+
		"Color: 1, Key:A Parent: B\n"+
		"Color: 1, Key:C Parent: B\n"+
		"Color: 1, Key:F Parent: D\n"+
		"Color: 1, Key:E Parent: F\n"+
		"Color: 0, Key:H Parent: F\n"+
		"Color: 1, Key:G Parent: H\n"+
		"Color: 1, Key:I Parent: H\n"+
		"Color: 0, Key:J Parent: I\n";
		assertEquals(str,rbt.specialPreOrderVisitParentForJUnit(rbt.root));
		
		}

}
