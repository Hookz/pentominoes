class Node {
	private int data;
	private Node up;
	private Node down;
	private Node right;
	private Node left;
	private Node column;
	private int rowID;
	private int colID;
	private int nodeCount;

	public Node(){
		this.up=null;
		this.down=null;
		this.right=null;
		this.left=null;
		this.column=null;
		this.rowID=0;
		this.colID=0;
		this.nodeCount=0;

	}
	public Node(Node up, Node down, Node left, Node right, Node column, int rowID, int colID){
		this.up=up;
		this.down=down;
		this.left=left;
		this.right=right;
		this.column=column;
		this.rowID=rowID;
		this.colID=colID;
		this.nodeCount=0;
	}

	public Node getUp(){
		return this.up;
	}
	public Node getDown(){
		return this.down;
	}
	public Node getLeft(){
		return this.left;
	}
	public Node getRight(){
		return this.right;
	}
	public Node getColumn(){
		return this.column;
	}
	public int getRowID(){
		return this.rowID;
	}
	public int getColID(){
		return this.colID;
	}
	public int getNodeCount(){
		return this.nodeCount;
	}

	public void setUp(Node up){
		this.up=up;
	}
	public void setDown(Node down){
		this.down=down;
	}
	public void setLeft(Node left){
		this.left=left;
	}
	public void setRight(Node right){
		this.right=right;
	}
	public void setColumn(Node column){
		this.column=column;
	}
	public void setRowID(int rowID){
		this.rowID=rowID;
	}
	public void setColID(int colID){
		this.colID=colID;
	}
	public void setNodeCount(int nodeCount){
		this.nodeCount=nodeCount;
	}
	public void addToNodeCount(int value){
		this.nodeCount=nodeCount+value;
	}
	public void incrementNodeCount(){
		addToNodeCount(1);
	}

	public boolean hasLeft(){
		if(this.getLeft()!=null) return true;
		else return false;
	}
	public boolean hasRight(){
		if(this.getRight()!=null) return true;
		else return false;
	}
	public boolean hasUp(){
		if(this.getUp()!=null) return true;
		else return false;
	}
	public boolean hasDown(){
		if(this.getDown()!=null) return true;
		else return false;
	}
	public boolean hasColumn(){
		if(this.getColumn()!=null) return true;
		else return false;
	}
}