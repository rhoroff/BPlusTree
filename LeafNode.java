import java.util.*;

class LeafNode extends BPlusTreeNode {
    boolean isLeaf = true;
    public static int MAXNODESIZE;
    public List<Integer> Keys = null;
    public List<Double> Values = null;
    public LeafNode leftSibling = null;
    public LeafNode rightSibling = null;

    LeafNode(int m) {
        this.Keys = new ArrayList<Integer>();
        this.Values = new ArrayList<Double>();
        this.MAXNODESIZE=m;
    }

    @Override
    public void insert(int key, double value) {
        if(this.Keys.size() < MAXNODESIZE){//Inserting into a < m node
            this.Keys.add(key);//Adds key
            this.Values.add(value);//Adds value
        }else{//Inserting into a full node
            //Split node down the middle into two node          
            //TODO: Remove debugging print statment  
            System.out.println("The size of the node has become too big");
        }
    }

    @Override
    public void split() {

    }

}