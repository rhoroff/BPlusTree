import java.util.*;

public class InternalNode extends BPlusTreeNode {
    boolean isLeaf = false;
    ArrayList<BPlusTreeNode> children = null;
    BPlusTreeNode parent = null;

    public InternalNode(int m) {
        this.Keys = new ArrayList<Integer>();
        this.children = new ArrayList<BPlusTreeNode>();
        this.MAXNODESIZE = m;
    }

    @Override
    public BPlusTreeNode split() {
        return null;
    }

    @Override
    public BPlusTreeNode insert(int key, double value) {
        if (this.Keys.size() == 1) {// Case that internal node is of size one
            if (key < Keys.get(0)) {
                this.children.get(0).insert(key, value);
            } else {
                this.children.get(1).insert(key, value);
            }
        } else {
            int low = Integer.MIN_VALUE;
            int high = this.Keys.get(0);
            for(int i = 0; i < this.Keys.size();i++){
                if(key < high && key > low){
                    this.children.get(i).insert(key,value);
                }else{
                    if(i == this.Keys.size() -1){
                        this.children.get(i + 1).insert(key,value);
                        break;
                    }else{
                        low = this.Keys.get(i);
                        high = this.Keys.get(i + 1);
                    }
                }
            }
        }

        if (this.Keys.size() >= this.MAXNODESIZE) {
            if (this.parent == null) {
                InternalNode newParent = new InternalNode(this.MAXNODESIZE);
                InternalNode newSibling = new InternalNode(this.MAXNODESIZE);
                int splitIndex = this.Keys.size() / 2;
                newParent.Keys.add(this.Keys.get(splitIndex));// Take middle value and make new parent node out of it
                for(int i = 0; i < splitIndex;i++){
                    newSibling.Keys.add(this.Keys.get(0));//Take key from this new node and put in sibling
                    this.Keys.remove(0);
                }
                for(int i = 0; i<=splitIndex;i++){//Two separate nodes are now made
                    newSibling.children.add(this.children.get(0));
                    this.children.remove(0);
                }
                this.Keys.remove(0);
                newParent.children.add(newSibling);
                newParent.children.add(this);
                this.parent = newParent;
                newSibling.parent = this.parent;
                return this.parent;
            }

        }
        return this;
    }

}