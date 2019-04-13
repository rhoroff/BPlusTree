import java.util.*;

abstract class BPlusTreeNode {

    public boolean isLeaf;

    public List<Integer> Keys;

    public static int MAXNODESIZE;

    public BPlusTreeNode Parent;

    abstract public BPlusTreeNode split();

    abstract public BPlusTreeNode insert(int key, double value);


}