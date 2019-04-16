import java.util.*;

abstract class BPlusTreeNode{

    InternalNode Parent = null;

    ArrayList<Integer> Keys = null;

    static int MAXNODESIZE;
    
    abstract public BPlusTreeNode split();

    abstract public BPlusTreeNode insert(int key, double value);

    abstract public double search(int key);

    abstract public BPlusTreeNode delete(int key);

    abstract public BPlusTreeNode merge();


}