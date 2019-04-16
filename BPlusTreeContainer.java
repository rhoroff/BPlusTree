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
        if(this.root.search(key)== 0.0){
            this.root = this.root.insert(key, value);// Insert into the root and perform operations all the way down
        }
        // 2 Cases
        // Root is a leaf node, can be inserted directly into root and then taken care
        // of
        // Root is an internal node, check which interval the node belongs in
    }

    public void search(int key) {
        double value = this.root.search(key);
        if (value == 0.0) {
            System.out.println("Null");
        } else {
            System.out.println(this.root.search(key));
        }
    }

    public void rangeSearch(int begin, int end) {

    }

    public void delete(int keyToDelete) {
        this.root = this.root.delete(keyToDelete);

    }

}