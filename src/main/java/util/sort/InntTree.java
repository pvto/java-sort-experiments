package util.sort;


/**
 * This is a somewhat memory-hungry fast-put fast-get treebag
 * implementation.  What it does?  Counts inserted ints.
 * 
 * @author Paavo Toivanen https://github.com/pvto
 */
public class InntTree {
        
//    public int size = 0;
    public Node0 root = new Node0();

    // I didn't like to pass these parameters around.  Made them static.
    // --> Can use only one active parameterisation per VM!
    // You could copy-paste this class if you need more :P
    private static int
            N0, // node-0 array size in bits (first integral level node)
            N1, // node-1 array size in bits (second integral level node...)
            N2,
            N0shr,  // shift-right (shr) amount for node-0 key
            N1shr,
            N0Nbm,  // node-0 bitmap for negative float keys
            N0Padd, // node-0 array shift for positive float keys
            N1bm,   // node-1 bitmap for key
            N2bm
            ;
    static{ init(31, 16, 9); }
    
    private static void init(int INTBITS, int N0, int N1)
    {
        if ((INTBITS < 8)
        || (INTBITS > 32)) throw new RuntimeException("8 <= INTBITS <= 32 must be (Yoda said)");
        if (N0 + N1 > INTBITS - 4) throw new RuntimeException("must have N0 + N1 <= " + (INTBITS - 4));
        if (N0 < 5) throw new RuntimeException("must have N0 >= 5");
        if (N1 < 2) throw new RuntimeException("must have N1 >= 2");
        if (N0 + N1 > 30) throw new RuntimeException("must have N2 >= 2");
        InntTree.N0 = N0;
        InntTree.N1 = N1;
        InntTree.N2 = INTBITS - N0 + 1 - N1;
	N0shr = INTBITS - N0;
	N1shr = N1;
	N0Nbm = (1 << (N0 - 1)) - 1;
	N0Padd = (1 << (N0 - 1));
	N1bm = (1 << (INTBITS - N0)) - 1;
	N2bm = (1 << (INTBITS - N0 + 1 - N1)) - 1;
    }
    
    public void put(int rank)
    {
        root.put(rank);
//        size++;
    }
    
    
    public static class Node0 {
        
        public Node1[] children = new Node1[1<<N0];
        
        public void put(int rank)
        {
            int x;
            if ((rank & 0x80000000) == 0x80000000) // negative key (rank < 0f)
            {
                x = (rank >> N0shr) & N0Nbm;
            }
            else
            {
                x = (rank >> N0shr) + N0Padd;
            }
            if (children[x] == null)
                children[x] = new Node1();
            children[x].put(rank);
        }
    }
    
    public static class Node1 {
        
        public Node2[] children = new Node2[1<<N1];
        
        public void put(int rank)
        {
            int x = (rank & N1bm) >> N1shr;
            if (children[x] == null)
                children[x] = new Node2();
            children[x].put(rank);
        }
    }
    
    public static class Node2 {
        
        public int[] children = new int[1<<N2];
        public int[] counts = new int[1<<N2];
        
        public void put(int rank)
        {
            int x = (rank & N2bm);
            children[x] = rank;
            counts[x]++;
        }
    }
    
}
