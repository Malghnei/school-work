import java.util.LinkedList;

public class HashDictionary implements DictionaryADT{
    // Instance variables
    private LinkedList<Layout>[] table;
    private int size;
    
    /**
     * Default constructor initializing the hash table with a default size of 1000.
     * @param size The size of the hash table
     */
    public HashDictionary(int size) {
        this.size = size;
        table = new LinkedList[size];
        for (int i = 0; i < size; i++) {
            table[i] = null;
        }
    }

    /**
     * Hash function to compute the index for a given key.
     * Used a polynomial hash function.
     * @param key The key to be hashed
     * @return The computed hash index
     */
    private long hashFunction(String key) {
        long hash = 0;

        for (int i = 0; i < key.length(); i++) {
            // Prime number 33 is used as a multiplier
            hash = (33 * hash + key.charAt(i)) % size;
        }

        return hash;
    }

    public int put(Layout data) throws DictionaryException {
        int index = (int) hashFunction(data.getBoardLayout());
        int collided = 0;
        
        // Check for collision and existing key.
        if (table[index] != null) {
            // Collision occurred.
            collided = 1;
            
            // Check if the key already exists.
            for (Layout layout : table[index]) {
                if (layout.getBoardLayout().equals(data.getBoardLayout())) {
                    // Key already exists, throw exception.
                    throw new DictionaryException("Key already exists in the dictionary.");
                }
            }
        } else {
            // Initialize the linked list at this index if it's null.
            table[index] = new LinkedList<>();
        }


        // Add the new data to the linked list at the computed index.
        table[index].add(data);
        return collided;
    }

    public void remove(String boardLayout) throws DictionaryException {
        int index = (int) hashFunction(boardLayout);
        
        if (table[index] == null) {
            throw new DictionaryException("Key not found in the dictionary.");
        }

        // Iterate through the linked list to find and remove the key.
        for (Layout layout : table[index]) {
            if (layout.getBoardLayout().equals(boardLayout)) {
                table[index].remove(layout);
                return;
            }
        }

        // If the key was not found in the linked list, throw an exception.
        throw new DictionaryException("Key not found in the dictionary.");  
    }

    public int get(String boardLayout) {
        int index = (int) hashFunction(boardLayout);

        if (table[index] == null) {
            return -1;
        }

        for (Layout layout : table[index]) {
            if (layout.getBoardLayout().equals(boardLayout)) {
                return layout.getScore();
            }
        }

        return -1;
    }
}
