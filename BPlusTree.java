public class BPlusTree{
    private BPlusTreeNode root = null;;
    public int degree;

    public BPlusTree(){
        this.root = null;
        this.degree = 0;
    }

    public BPlusTree initialize(int m){//m is the degree of the B+ tree
        this.degree = m;
        this.root = new LeafNode();
        return this;
    }
    
}