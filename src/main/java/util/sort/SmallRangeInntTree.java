package util.sort;

import util.ExpandingArray;

/**
 *
 * @author Paavo Toivanen https://github.com/pvto
 */
public class SmallRangeInntTree {

//    public int size = 0;
    public final Node0 root;
    
    public SmallRangeInntTree(int initialSize)
    {
        root = new Node0(initialSize);
    }
    
    public SmallRangeInntTree()
    {
        root = new Node0(4096);
    }

    // I didn't like to pass these parameters around.  Made them static.
    // --> Can use only one active parameterisation per VM!
    // You could copy-paste this class if you need more :P
    private static int
            N0, // node-0 array size in bits (first integral level node)
            N1, // node-1 array size in bits (second integral level node...)
            N0shr,  // shift-right (shr) amount for node-0 key
            N0Nbm,  // node-0 bitmap for negative float keys
            N0Padd, // node-0 array shift for positive float keys
            N1bm   // node-1 bitmap for key
            ;
    static{ init(31, 14); }
    
    private static void init(int INTBITS, int N0)
    {
        if ((INTBITS < 8)
        || (INTBITS > 32)) throw new RuntimeException("8 <= INTBITS <= 32 must be (Yoda said)");
        if (N0 < 5) throw new RuntimeException("must have N0 >= 5");
        if (N0 > 30) throw new RuntimeException("must have N0 <= 30");
        SmallRangeInntTree.N0 = N0;
        SmallRangeInntTree.N1 = N1;
	N0shr = INTBITS - N0;
	N0Nbm = (1 << (N0 - 1)) - 1;
	N0Padd = (1 << (N0 - 1));
	N1bm = (1 << N1) - 1;
    }
    
    public void put(int rank)
    {
        root.put(rank);
//        size++;
    }
    
    public void put(int rank, int n)
    {
        root.put(rank, n);
    }
    
    
    public static class Node0 {
        
        public final ExpandingArray<Node1> children;
        public final ExpandingArray<Node1> negativeChildren;
        
        public Node0(int initialSize)
        {
            children = new ExpandingArray<>(initialSize);
            negativeChildren = new ExpandingArray<>(initialSize);
        }
        
        public void put(int rank)
        {
            int x;
            ExpandingArray<Node1> ch;
            if ((rank & 0x80000000) == 0x80000000) // negative key (rank < 0f)
            {
                ch = negativeChildren;
            }
            else
            {
                ch = children;
            }
            x = rank >>> N0shr;
            Node1 n1 = ch.get(x);
            if (n1 == null)
                ch.put(x, n1 = new Node1());
            n1.put(rank);
        }
        
        public void put(int rank, int n)
        {
            int x;
            ExpandingArray<Node1> ch;
            if ((rank & 0x80000000) == 0x80000000) // negative key (rank < 0f)
            {
                ch = negativeChildren;
            }
            else
            {
                ch = children;
            }
            x = rank >>> N0shr;
            Node1 n1 = ch.get(x);
            if (n1 == null)
                ch.put(x, n1 = new Node1());
            n1.put(rank, n);
        }
    }
   
    public static class Node1 {
        
        public int[] children = new int[1<<N1];
        public int[] counts = new int[1<<N1];
        
        public void put(int rank)
        {
            int x = (rank & N1bm);
            children[x] = rank;
            counts[x]++;
        }
        
        public void put(int rank, int n)
        {
            int x = (rank & N1bm);
            children[x] = rank;
            counts[x] += n;
        }
    }

    
    
    
    
    
    public long estimateSizeInBytes()
    {
        long res = root.children.size() << 2;
        for(Object o : root.children.items) if (o != null)
        {
            res += ((Node1)o).children.length << 3;
        }
        res += root.negativeChildren.size() << 2;
        for(Object o : root.negativeChildren.items) if (o != null)
        {
            res += ((Node1)o).children.length << 3;
        }        
        return res;   
    }
    
    
    
    
}
