import java.util.*;

abstract class BPlusTreeNode{

    ArrayList<Integer> Keys = null;

    static int MAXNODESIZE;
    
    abstract public BPlusTreeNode split();

    abstract public BPlusTreeNode insert(int key, double value);


}