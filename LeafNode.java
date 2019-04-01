import java.util.*;

class LeafNode extends BPlusTreeNode {
    boolean isLeaf = true;
    public static int MAXNODESIZE;
    public List<Integer> Keys = null;
    public List<Double> Values = null;
    public LeafNode leftSibling = null;
    public LeafNode rightSibling = null;

    LeafNode() {
        this.Keys = new ArrayList<Integer>();
        this.Values = new ArrayList<Double>();
    }

    @Override
    public void insert(int key, double value) {

    }

    @Override
    public void split() {

    }

}