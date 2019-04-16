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
        } else {// Search through ranges between children to find where to put child
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

        if (this.Keys.size() >= this.MAXNODESIZE) {// If node is overfilled, split it
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
            this.Keys.remove(0);// Remove the split index
            newParent.children.add(newSibling);// Add nodes to parent
            newParent.children.add(this);
            this.Parent = newParent;// Set parent of both nodes to maintain tree
            newSibling.Parent = this.Parent;
            return this.Parent;
        } else {
            InternalNode newInternalSibling = new InternalNode(this.MAXNODESIZE);
            int splitIndex = this.Keys.size() / 2;
            if (this.Keys.size() > 2 && this.Keys.size() % 2 == 0) {// Formatting
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
            this.Keys.remove(0);// Remove the split index
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
            if (this.Parent.children.size() > 1) {
                for (int i = 0; i < this.Parent.children.size(); i++) {// Just a quick and dirty bubble sort
                    for (int j = 0; j < this.Parent.children.size() - i - 1; j++) {
                        if ((this.Parent.children.get(j)).Keys.get(0) > (this.Parent.children.get(j + 1)).Keys.get(0)) {
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

    @Override
    public double search(int key) {
        if (this.Keys.size() == 1) {// Case that internal node is of size one
            if (key < Keys.get(0)) {
                return this.children.get(0).search(key);
            } else {
                return this.children.get(1).search(key);
            }
        } else {
            int low = Integer.MIN_VALUE;
            int high = this.Keys.get(0);
            for (int i = 0; i < this.Keys.size(); i++) {
                if (key < high && key >= low) {
                    return this.children.get(i).search(key);
                } else {
                    if (i == this.Keys.size() - 1) {
                        return this.children.get(i + 1).search(key);
                    } else {
                        low = this.Keys.get(i);
                        high = this.Keys.get(i + 1);
                    }
                }
            }
        }
        return 0.0;
    }

    @Override
    public BPlusTreeNode delete(int key){
        // if (this.Keys.size() == 1) {// Case that internal node is of size one
        //     if (key < Keys.get(0)) {
        //         this.children.get(0).delete(key);
        //     } else {
        //         this.children.get(1).delete(key);
        //     }
        // } else {// Search through ranges between children to find where to put child
        //     int low = Integer.MIN_VALUE;
        //     int high = this.Keys.get(0);
        //     for (int i = 0; i < this.Keys.size(); i++) {
        //         if (key < high && key > low) {
        //             this.children.get(i).delete(key);
        //             break;
        //         } else {
        //             if (i == this.Keys.size() - 1) {
        //                 this.children.get(i + 1).delete(key);
        //                 break;
        //             } else {
        //                 low = this.Keys.get(i);
        //                 high = this.Keys.get(i + 1);
        //             }
        //         }
        //     }
        // }
        // if(this.Keys.indexOf(key) != -1){
        //     this.Keys.remove(this.Keys.indexOf(key));
        // }

        // if (this.Keys.size() < 2) {// If node is overfilled, split it
        //     return this.merge();
        // }
        return this;
    }

    @Override
    public BPlusTreeNode merge(){
        if(this.Keys.size() == 0 && this.Parent.Keys.size() == 1){
            this.Keys.add(this.Parent.Keys.get(0));//Get the parent's key
            this.Keys.add(this.Parent.children.get(0).Keys.get(0));//Get a key from a same level sibling
            this.children.add(((InternalNode)(this.Parent.children.get(0))).children.get(0));
            ((InternalNode)(this.Parent.children.get(0))).children.remove(0);
            this.children.add(((InternalNode)(this.Parent.children.get(0))).children.get(0));
            ((InternalNode)(this.Parent.children.get(0))).children.remove(0);
            return this;
        }
        return this;
    }
}