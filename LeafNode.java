import java.util.*;

class LeafNode extends BPlusTreeNode {
    public List<Double> Values = null;
    public LeafNode leftSibling = null;
    public LeafNode rightSibling = null;

    LeafNode(int m) {
        this.Keys = new ArrayList<Integer>();
        this.Values = new ArrayList<Double>();
        this.MAXNODESIZE = m;
    }

    @Override
    public BPlusTreeNode insert(int key, double value) {
        this.Keys.add(key);// Adds key
        this.Values.add(value);// Adds value
        if (Keys.size() >= 2) {// If there is more than value, sort the values
            for (int i = 0; i < Keys.size(); i++) {// Just a quick and dirty bubble sort
                for (int j = 0; j < Keys.size() - i - 1; j++) {
                    if (Keys.get(j) > Keys.get(j + 1)) {
                        // Sort the keys
                        int keyTemp = Keys.get(j);
                        Keys.set(j, Keys.get(j + 1));
                        Keys.set(j + 1, keyTemp);
                        // Sort the values associated with the keys
                        double valuesTemp = Values.get(j);
                        Values.set(j, Values.get(j + 1));
                        Values.set(j + 1, valuesTemp);
                    }
                }
            }
        }
        if (this.Keys.size() >= MAXNODESIZE) {
            return this.split();

        } else {
            return this;
        }
    }

    @Override
    public BPlusTreeNode split() {
        if (this.Parent == null) {// Case of root insert
            InternalNode parentNode = new InternalNode(this.MAXNODESIZE);
            LeafNode newSibling = new LeafNode(MAXNODESIZE);
            int splitIndex = this.Keys.size() / 2;
            if(this.Keys.size() > 2 && this.Keys.size() % 2 == 0){
                splitIndex = (this.Keys.size() / 2) - 2;
            }
            parentNode.Keys.add(this.Keys.get(splitIndex));
            for (int i = 0; i < splitIndex; i++) {
                newSibling.insert(this.Keys.get(0), this.Values.get(0));
                this.Keys.remove(i);
                this.Values.remove(i);
            }
            parentNode.children.add(newSibling);
            parentNode.children.add(this);
            newSibling.Parent = parentNode;
            this.Parent = parentNode;
        }else{
            LeafNode newSibling = new LeafNode(MAXNODESIZE);
            int splitIndex = this.Keys.size() / 2;
            if(this.Keys.size() > 2 && this.Keys.size() % 2 == 0){
                splitIndex = (this.Keys.size() / 2) - 2;
            }
            newSibling.Parent = this.Parent;
            this.Parent.Keys.add(this.Keys.get(splitIndex));
            for (int i = 0; i < splitIndex; i++) {
                newSibling.insert(this.Keys.get(i), this.Values.get(i));
                this.Keys.remove(i);
                this.Values.remove(i);
            }
            this.Parent.children.add(newSibling);
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

        }
        return this.Parent;
    }
}