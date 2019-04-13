import java.util.*;

class LeafNode extends BPlusTreeNode {
    boolean isLeaf = true;
    public static int MAXNODESIZE;
    public List<Integer> Keys = null;
    public List<Double> Values = null;
    public LeafNode leftSibling = null;
    public LeafNode rightSibling = null;
    public boolean isOverfilled;

    LeafNode(int m) {
        this.Keys = new ArrayList<Integer>();
        this.Values = new ArrayList<Double>();
        this.MAXNODESIZE = m;
    }

    @Override
    public BPlusTreeNode insert(int key, double value) {
        this.Keys.add(key);// Adds key
        this.Values.add(value);// Adds value
        if (Keys.size() > 2) {// If there is more than value, sort the values
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
        if (Keys.size() >= MAXNODESIZE){
            this.isOverfilled = true;
            return this.split();

        }else{
            return this;
        }
    }

    @Override
    public BPlusTreeNode split() {
        InternalNode parentNode = new InternalNode();
        LeafNode newSibling = new LeafNode(MAXNODESIZE);
        int splitIndex = this.Keys.size() / 2;
        parentNode.Keys.add(this.Keys.get(splitIndex));
        for(int i = 0; i < splitIndex;i++){
            newSibling.insert(this.Keys.get(i), this.Values.get(i));
            this.Keys.remove(i);
            this.Values.remove(i);
        }
        parentNode.children.add(newSibling);
        parentNode.children.add(this);
        return parentNode;

    }

}