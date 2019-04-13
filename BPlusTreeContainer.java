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
        this.root = this.root.insert(key, value);// Insert into the root and perform operations all the way down
        //2 Cases
            //Root is a leaf node, can be inserted directly into root and then taken care of
            //Root is an internal node, check which interval the node belongs in 
    }
    
}