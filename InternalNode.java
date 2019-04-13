import java.util.*;

public class InternalNode extends BPlusTreeNode {
    boolean isLeaf = false;
    ArrayList<Integer> Keys = null;
    ArrayList<BPlusTreeNode> children = null;

    public InternalNode(){
        this.Keys = new ArrayList<Integer>();
        this.children = new ArrayList<BPlusTreeNode>();
    }
    @Override
    public BPlusTreeNode split() {
        return null;
    }
    @Override
    public BPlusTreeNode insert(int key, double value) {
        if(this.children.size() == 1){//Case that internal node is of size one
            if(key < Keys.get(0)){
                this.children.set(0, this.children.get(0).insert(key,value));
            }else{
                this.children.set(1, this.children.get(1).insert(key,value));
            }
        }else{
            //Check ranges of every node
        }
        return this;
    }
}