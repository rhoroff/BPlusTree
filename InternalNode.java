import java.util.*;

public class InternalNode extends BPlusTreeNode {
    boolean isLeaf = false;
    ArrayList<BPlusTreeNode> children = null;

    public InternalNode(int m) {
        this.Keys = new ArrayList<Integer>();
        this.children = new ArrayList<BPlusTreeNode>();
        this.MAXNODESIZE = m;
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
            for (int i = 0; i < this.Keys.size(); i++) {
                if (key < high && key > low) {
                    this.children.get(i).insert(key, value);
                    break;
                } else {
                    if (i == this.Keys.size() - 1) {
                        this.children.get(i + 1).insert(key, value);
                        break;
                    } else {
                        low = this.Keys.get(i);
                        high = this.Keys.get(i + 1);
                    }
                }
            }
        }

        if (this.Keys.size() >= this.MAXNODESIZE) {
            return this.split();
        }
        return this;
    }

    @Override
    public BPlusTreeNode split() {
        if (this.Parent == null) {
            InternalNode newParent = new InternalNode(this.MAXNODESIZE);
            InternalNode newSibling = new InternalNode(this.MAXNODESIZE);
            int splitIndex = this.Keys.size() / 2;
            if (this.Keys.size() > 2 && this.Keys.size() % 2 == 0) {
                splitIndex = (this.Keys.size() / 2) - 2;
            }
            newParent.Keys.add(this.Keys.get(splitIndex));// Take middle value and make new Parent node out of it
            for (int i = 0; i < splitIndex; i++) {
                newSibling.Keys.add(this.Keys.get(0));// Take key from this new node and put in sibling
                this.Keys.remove(0);
            }
            for (int i = 0; i <= splitIndex; i++) {// Two separate nodes are now made
                BPlusTreeNode newSiblingsChild = this.children.get(0);
                newSiblingsChild.Parent = newSibling;
                newSibling.children.add(newSiblingsChild);
                this.children.remove(0);
            }
            this.Keys.remove(0);
            newParent.children.add(newSibling);
            newParent.children.add(this);
            this.Parent = newParent;
            newSibling.Parent = this.Parent;
            return this.Parent;
        } else {
            InternalNode newInternalSibling = new InternalNode(this.MAXNODESIZE);
            int splitIndex = this.Keys.size() / 2;
            if (this.Keys.size() > 2 && this.Keys.size() % 2 == 0) {
                splitIndex = (this.Keys.size() / 2) - 2;
            }
            this.Parent.Keys.add(this.Keys.get(splitIndex));
            for (int i = 0; i < splitIndex; i++) {
                newInternalSibling.Keys.add(this.Keys.get(0));// Take key from this new node and put in sibling
                this.Keys.remove(0);
            }
            for (int i = 0; i <= splitIndex; i++) {// Two separate nodes are now made
                BPlusTreeNode newInternalSiblingsChild = this.children.get(0);
                newInternalSiblingsChild.Parent = newInternalSibling;
                newInternalSibling.children.add(newInternalSiblingsChild);
                this.children.remove(0);
            }
            this.Keys.remove(0);
            this.Parent.children.add(newInternalSibling);
            newInternalSibling.Parent = this.Parent;

            for (int i = 0; i < this.Parent.Keys.size(); i++) {// Just a quick and dirty bubble sort
                for (int j = 0; j < this.Parent.Keys.size() - i - 1; j++) {
                    if (this.Parent.Keys.get(j) > this.Parent.Keys.get(j + 1)) {
                        // Sort the keys
                        int keyTemp = this.Parent.Keys.get(j);
                        this.Parent.Keys.set(j, this.Parent.Keys.get(j + 1));
                        this.Parent.Keys.set(j + 1, keyTemp);
                    }
                }
            }
            if(this.Parent.children.size() > 1){
                for (int i = 0; i < this.Parent.children.size(); i++) {// Just a quick and dirty bubble sort
                    for (int j = 0; j < this.Parent.children.size() - i - 1; j++) {
                        if ((this.Parent.children.get(j)).Keys.get(0) > (this.Parent.children.get(j+1)).Keys.get(0)) {
                            // Sort the keys
                            BPlusTreeNode childrenTemp = this.Parent.children.get(j);
                            this.Parent.children.set(j, this.Parent.children.get(j + 1));
                            this.Parent.children.set(j + 1, childrenTemp);
                        }
                    }
                }
            }


            return this.Parent;

        }
    }
}