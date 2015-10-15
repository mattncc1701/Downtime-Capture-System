public class TreeNode<E> {
	private E value;
	private TreeNode[] next;	

	public TreeNode(E value) {
		this.value = value;
		this.next = null;
	}

	public TreeNode(E value, TreeNode[] next) {
		this.value = value;
		this.next = next;
	}

	public E getValue() {
		if(value == null){
			return null;
		}
		return value;
	}

	public void setValue(E value) {
		this.value = value;
	}

	public TreeNode getNode(int index) {
		return next[index];
	}
	
	public TreeNode[] getTreeNodeArray(){
		return next;
	}

	public void setNext(TreeNode[] next) {
		this.next = next;	
	}

	public boolean hasNext() {
		return (next != null);
	}
}
