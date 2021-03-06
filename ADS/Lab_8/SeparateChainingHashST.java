import java.util.*;
public class SeparateChainingHashST<Key, Value> {
    private static final int INIT_CAPACITY = 4;


    private int n;                                // number of key-value pairs
    private int m;                                // hash table size
    private SequentialSearchST<Key, Value>[] st;  // array of linked-list symbol tables
    /**
     * Initializes an empty symbol table.
     */

    SequentialSearchST obj = new SequentialSearchST();
    public SeparateChainingHashST() {
        this(INIT_CAPACITY);
    } 

    /**
     * Initializes an empty symbol table with {@code m} chains.
     * @param m the initial number of chains
     */

    public SeparateChainingHashST(int m) {
        this.m = m ;
        st = (SequentialSearchST<Key, Value>[]) new SequentialSearchST[m];
        for (int i = 0; i < m; i++)
            st[i] = new SequentialSearchST<Key, Value>();
    } 

    // resize the hash table to have the given number of chains,
    // rehashing all of the keys
    private void resize(int chains) {
        SeparateChainingHashST<Key, Value> temp = new SeparateChainingHashST<Key, Value>(chains);
        for (int i = 0; i < m; i++) {
            for (Key key : st[i].keys()) {
                temp.put(key, st[i].get(key));
            }
        }
        this.m  = m;
        this.n  = n;
        this.st = st;
    }

    // hash function for keys - returns value between 0 and m-1
    private int hashTextbook(Key key) {
        return(key.hashCode() & 0x7fffffff) % m;
    }
    
    //(>>>) it is a unsigned operator 
    private int hash(Key key){
        int h = key.hashCode();
        h ^= (h >>> 20) ^ (h >>> 12) ^ (h >>> 7) ^ (h >>> 4);
        return h & (m-1);
   }
 
    /**
     * Returns the number of key-value pairs in this symbol table.
     *
     * @return the number of key-value pairs in this symbol table
     */
    public int size() {
       return n;
    } 

    /**
     * Returns true if this symbol table is empty.
     *
     * @return {@code true} if this symbol table is empty;
     *         {@code false} otherwise
     */
    public boolean isEmpty() {
         if(size()==0)
        {
            return true;
        }
        return false;
    }

    /**
     * Returns true if this symbol table contains the specified key.
     *
     * @param  key the key
     * @return {@code true} if this symbol table contains {@code key};
     *         {@code false} otherwise
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public boolean contains(Key key) {
        if(key==null)
        throw new IllegalArgumentException("Argument contains() is null");
       
         else if(get(key)==null)
            {
                return false;
            }
        return true;
    } 

    /**
     * Returns the value associated with the specified key in this symbol table.
     *
     * @param  key the key
     * @return the value associated with {@code key} in the symbol table;
     *         {@code null} if no such value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public Value get(Key key) {
          if(key==null) throw new IllegalArgumentException("Key is null");
        int l=hashTextbook(key);
        return st[l].get(key);    
    } 

    /**
     * Inserts the specified key-value pair into the symbol table, overwriting the old 
     * value with the new value if the symbol table already contains the specified key.
     * Deletes the specified key (and its associated value) from this symbol table
     * if the specified value is {@code null}.
     *
     * @param  key the key
     * @param  val the value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void put(Key key, Value val) {
      if (key == null) throw new IllegalArgumentException("key is null");
        if (val == null) {
            delete(key);
            return;
        }

        // double table size if average length of list >= 10
        if (n >= 10*m) resize(2*m);

        int i = hash(key);
        if (!st[i].contains(key)) n++;
        st[i].put(key, val);
    }

   


    /**
     * Removes the specified key and its associated value from this symbol table     
     * (if the key is in this symbol table).    
     *
     * @param  key the key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void delete(Key key) {
         if (key == null) throw new IllegalArgumentException("Key is null");

        int i = hash(key);
        if (st[i].contains(key)) n--;
        st[i].delete(key);

        // halve table size if average length of list <= 2
        if (m > INIT_CAPACITY && n <= 2*m) resize(m/2); 
    } 

    // return keys in symbol table as an Iterable
    public Iterable<Key> keys() {
         LinkedList<Key> link = new LinkedList<Key>();
        for (int i=0;i<m;i++) {
        for(Key key:st[i].keys()){
            link.add(key);
            }    
        }
        return link;
    } 
    /**
     * Unit tests the {@code SeparateChainingHashST} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) { 
        SeparateChainingHashST<String, Integer> st = new SeparateChainingHashST<String, Integer>();
        st.put("A",3);
        st.put("D",6);
        st.put("Z",1);
        st.put("E",8);
        st.put("H",4);
        System.out.println("The size of the Hash table is: "+st.size());
        System.out.println("The keys present in hash table are: "+st.keys());

        st.delete("D");
        System.out.println("The size of the Hash table is: "+st.size());
        System.out.println("The keys present in hash table are: "+st.keys());
        System.out.println("Is your table empty?: "+st.isEmpty());


        st.delete("E");
        st.delete("A");
        System.out.println("The size of the Hash table is: "+st.size());
        System.out.println("The keys present in hash table are: "+st.keys());

        assert(st.isEmpty() == false);
        assert(st.size() == 2);

        System.out.println("All Test Cases Passed");
    }

}