public class BPlusTreeContainer {
    private BPlusTreeNode root = null;;
    public int degree;

    public BPlusTreeContainer() {
        this.root = null;
        this.degree = 0;
    }

    public BPlusTreeContainer initialize(int m) {// m is the degree of the B+ tree
        this.degree = m;
        this.root = new LeafNode(m);
        return this;
    }

    public void insert(int key, double value) {
        this.root.insert(key, value);// Insert into the root and perfrom operations all the way down
    }
    
}