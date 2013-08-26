package model;

/**
 * This should be the tree we modeled, we focus on different layer decisions,
 * and try to consider them together, maybe reduce to the longest distance path
 * (log maybe...) problem.
 * 
 * @author Lingpeng Kong
 * 
 */
public class ProbablityTree {
	private PTreeNode head;
	private int cutoffLayer; // It seems that we should have a cutoff layer
								// where we want to stop
	
	public PTreeNode getHead() {
		return head;
	}
	
	public void setHead(PTreeNode head) {
		this.head = head;
	}



	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
