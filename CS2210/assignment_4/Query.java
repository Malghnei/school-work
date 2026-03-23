import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Query {
    // Instance Variables.
    private BSTOrderedDictionary dictionary;

    // Constructor.

    /**
     * Initializes a new Query object.
     * 
     * @param inputFile the name of the file for input.
     */
    public Query(String inputFile) {
        dictionary = new BSTOrderedDictionary();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            String line;
            String key = null;
            boolean readingKey = true;

            while ((line = reader.readLine()) != null) {
                if (readingKey) {
                    key = line.toLowerCase();
                    readingKey = false;
                } else {
                    int type = determineType(line);
                    dictionary.put(dictionary.getRoot(), key, line, type);
                    readingKey = true;
                }
            }

            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    /**
     * Helper method that processess the given content name and checks for type of
     * the content.
     * 
     * @param content the name of the content.
     * @return the type of the content.
     */
    private int determineType(String content) {
        if (content.contains(" ") || !content.contains(".")) {
            return 1; // Text
        }

        String extension = content.substring(content.lastIndexOf(".") + 1).toLowerCase();

        if (extension.equals("wav") || extension.equals("mid")) {
            return 2; // Audio
        } else if (extension.equals("jpg") || extension.equals("gif")) {
            return 3; // Image
        } else if (extension.equals("html")) {
            return 4; // HTML
        } else {
            return 1; // Text
        }
    }

    /**
     * Receives an input command as a String object and returns an output
     * accordingly.
     * 
     * @param command a string of an input command.
     * @return an output.
     */
    public String processCommand(String command) {
        StringTokenizer tokenizer = new StringTokenizer(command);

        if (!tokenizer.hasMoreTokens()) {
            return "Invalid command";
        }

        String cmd = tokenizer.nextToken().toLowerCase();

        try {
            switch (cmd) {
                // Gets the content with the given name.
                case "get":
                    return processGet(tokenizer);
                // Removes the node with the given key attribute.
                case "remove":
                    return processRemove(tokenizer);
                // Deletes the content in the given node and key attribute.
                case "delete":
                    return processDelete(tokenizer);
                // Adds new node with given key attribute.
                case "add":
                    return processAdd(tokenizer);
                // Finds the successor of the given node and key (If exists) in the ordered
                // dictionary.
                case "next":
                    return processNext(tokenizer);
                // Finds the predecessor of the given node and key (If exists) in the ordered
                // dictionary.
                case "prev":
                    return processPrev(tokenizer);
                // Finds the smallest key attribute in the dictionary.
                case "first":
                    return processFirst();
                // Finds the largest key attribute in the ordered dictionary.
                case "last":
                    return processLast();
                // Finds the size of the ordered dictionary.
                case "size":
                    return processSize();
                // Terminates the program.
                case "end":
                    return "end";
                // If none of the above.
                default:
                    return "Invalid command";
            }
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    /**
     * Helper method that gets the MultimediaItem object from the ordered dictionary
     * and performs certain actions based on the type of content.
     * 
     * @param tokenizer input command tokenized.
     * 
     * @return the output if exists; otherwise the predecessor and the successor.
     */
    private String processGet(StringTokenizer tokenizer) {
        if (!tokenizer.hasMoreTokens()) {
            return "Invalid command";
        }

        String key = tokenizer.nextToken().toLowerCase();
        ArrayList<MultimediaItem> media = dictionary.get(dictionary.getRoot(), key);

        if (media == null) {
            StringBuilder result = new StringBuilder();
            result.append("The word ").append(key).append(" is not in the ordered dictionary.\n");

            Data pred = findPredecessorForMissing(key);
            Data succ = findSuccessorForMissing(key);

            result.append("Preceding word: ");
            if (pred != null) {
                result.append(pred.getName());
            }
            result.append("\n");

            result.append("Following word: ");
            if (succ != null) {
                result.append(succ.getName());
            }

            return result.toString();
        }

        StringBuilder output = new StringBuilder();

        for (MultimediaItem item : media) {
            try {
                switch (item.getType()) {
                    case 1: // Text
                        output.append(item.getContent()).append("\n");
                        break;
                    case 2: // Audio
                        SoundPlayer player = new SoundPlayer();
                        player.play(item.getContent());
                        break;
                    case 3: // Image
                        PictureViewer viewer = new PictureViewer();
                        viewer.show(item.getContent());
                        break;
                    case 4: // HTML
                        ShowHTML html = new ShowHTML();
                        html.show(item.getContent());
                        break;
                }
            } catch (MultimediaException e) {
                output.append("Error processing ").append(item.getContent()).append(": ").append(e.getMessage())
                        .append("\n");
            }
        }

        return output.toString();
    }

    /**
     * Helper method that removes the MultimediaItem from the node with the given
     * key.
     * 
     * @param tokenizer input command tokenized.
     * @return errors if incorrect.
     */
    private String processRemove(StringTokenizer tokenizer) {
        if (!tokenizer.hasMoreTokens()) {
            return "Invalid command";
        }

        String key = tokenizer.nextToken().toLowerCase();

        try {
            dictionary.remove(dictionary.getRoot(), key);
            return "";
        } catch (DictionaryException e) {
            return "No record in the ordered dictionary has key " + key + ".";
        }
    }

    /**
     * Helper method that deletes the MultimediaItem objects with the given type in
     * the node with the given key.
     * 
     * @param tokenizer input command tokenized.
     * @return errors if incorrect.
     */
    private String processDelete(StringTokenizer tokenizer) {
        if (!tokenizer.hasMoreTokens()) {
            return "Invalid command";
        }

        String key = tokenizer.nextToken().toLowerCase();

        if (!tokenizer.hasMoreTokens()) {
            return "Invalid command";
        }

        int type = Integer.parseInt(tokenizer.nextToken());

        try {
            dictionary.remove(dictionary.getRoot(), key, type);
            return "";
        } catch (DictionaryException e) {
            return "No record in the ordered dictionary has key " + key + ".";
        }
    }

    /**
     * Helper method that adds the given MultimediaItem object with the given name
     * and type to the node with the given key.
     * 
     * @param tokenizer input command tokenized.
     * @return errors if incorrect.
     */
    private String processAdd(StringTokenizer tokenizer) {
        if (!tokenizer.hasMoreTokens()) {
            return "Invalid command";
        }

        String key = tokenizer.nextToken().toLowerCase();

        if (!tokenizer.hasMoreTokens()) {
            return "Invalid command";
        }

        String content = tokenizer.nextToken();

        if (!tokenizer.hasMoreTokens()) {
            return "Invalid command";
        }

        int type = Integer.parseInt(tokenizer.nextToken());

        dictionary.put(dictionary.getRoot(), key, content, type);
        return "";
    }

    /**
     * Helper method that finds the successor of the node with the given key.
     * 
     * @param tokenizer input command tokenized.
     * @return errors if incorrect.
     */
    private String processNext(StringTokenizer tokenizer) {
        if (!tokenizer.hasMoreTokens()) {
            return "Invalid command";
        }

        String key = tokenizer.nextToken().toLowerCase();

        if (!tokenizer.hasMoreTokens()) {
            return "Invalid command";
        }

        int d = Integer.parseInt(tokenizer.nextToken());

        BSTNode node = findNodeOrLeaf(dictionary.getRoot(), key);
        StringBuilder result = new StringBuilder();

        if (!node.isLeaf()) {
            result.append(node.getData().getName());
        }

        Data current = node.isLeaf() ? findSuccessorForMissing(key) : dictionary.successor(dictionary.getRoot(), key);

        for (int i = 0; i < d && current != null; i++) {
            if (result.length() > 0) {
                result.append(" ");
            }
            result.append(current.getName());
            current = dictionary.successor(dictionary.getRoot(), current.getName());
        }

        if (result.length() == 0) {
            return "There are no keys larger than or equal to " + key;
        }

        return result.toString();
    }

    /**
     * Helper method that finds the predecessor of the node with the given key.
     * 
     * @param tokenizer input command tokenized.
     * @return errors if incorrect.
     */
    private String processPrev(StringTokenizer tokenizer) {
        if (!tokenizer.hasMoreTokens()) {
            return "Invalid command";
        }

        String key = tokenizer.nextToken().toLowerCase();

        if (!tokenizer.hasMoreTokens()) {
            return "Invalid command";
        }

        int d = Integer.parseInt(tokenizer.nextToken());

        BSTNode node = findNodeOrLeaf(dictionary.getRoot(), key);
        StringBuilder result = new StringBuilder();

        if (!node.isLeaf()) {
            result.append(node.getData().getName());
        }

        Data current = node.isLeaf() ? findPredecessorForMissing(key)
                : dictionary.predecessor(dictionary.getRoot(), key);

        for (int i = 0; i < d && current != null; i++) {
            if (result.length() > 0) {
                result.append(" ");
            }
            result.append(current.getName());
            current = dictionary.predecessor(dictionary.getRoot(), current.getName());
        }

        if (result.length() == 0) {
            return "There are no keys smaller than or equal to " + key;
        }

        return result.toString();
    }

    /**
     * Helper method that finds the node with the smallest key attribute in the
     * dictionary.
     * 
     * @return name of the data if the dictionary is not empty; otherwise error.
     */
    private String processFirst() {
        Data smallest = dictionary.smallest(dictionary.getRoot());

        if (smallest == null) {
            return "The ordered dictionary is empty.";
        }

        return smallest.getName();
    }

    /**
     * Helper method that finds the node with the largest key attribute in the
     * dictionary.
     * 
     * @return name of the data if the dictionary is not empty; otherwise error.
     */
    private String processLast() {
        Data largest = dictionary.largest(dictionary.getRoot());

        if (largest == null) {
            return "The ordered dictionary is empty.";
        }

        return largest.getName();
    }

    /**
     * Helper method that finds the size of the
     * 
     * @return the output.
     */
    private String processSize() {
        int size = dictionary.getNumInternalNodes();
        return "There are " + size + " keys in the ordered dictionary";
    }

    /**
     * Helper method that finds the node with the given key.
     * 
     * @param r   a BSTNode object.
     * @param key the name of the data.
     * @return an internal or leaf node.
     */
    private BSTNode findNodeOrLeaf(BSTNode r, String key) {
        if (r.isLeaf()) {
            return r;
        }

        int comparison = key.compareTo(r.getData().getName());

        if (comparison == 0) {
            return r;
        } else if (comparison < 0) {
            return findNodeOrLeaf(r.getLeftChild(), key);
        } else {
            return findNodeOrLeaf(r.getRightChild(), key);
        }
    }

    /**
     * Helper method that finds the predecessor if the node with the given key does
     * not exist.
     * 
     * @param key the name of the data.
     * @return the predecessor node.
     */
    private Data findPredecessorForMissing(String key) {
        BSTNode node = findNodeOrLeaf(dictionary.getRoot(), key);

        if (node.isLeaf()) {
            BSTNode parent = node.getParent();

            if (parent == null) {
                return null;
            }

            if (node == parent.getRightChild()) {
                return parent.getData();
            } else {
                // Go up until we find an ancestor where we came from right
                BSTNode current = node;
                BSTNode p = parent;

                while (p != null && current == p.getLeftChild()) {
                    current = p;
                    p = current.getParent();
                }

                if (p == null) {
                    return null;
                }

                return p.getData();
            }
        }

        return dictionary.predecessor(dictionary.getRoot(), key);
    }

    /**
     * Helper method that finds the successor if the node with the given key does
     * not exist.
     * 
     * @param key
     * @return
     */
    private Data findSuccessorForMissing(String key) {
        BSTNode node = findNodeOrLeaf(dictionary.getRoot(), key);

        if (node.isLeaf()) {
            BSTNode parent = node.getParent();

            if (parent == null) {
                return null;
            }

            if (node == parent.getLeftChild()) {
                return parent.getData();
            } else {
                // Go up until we find an ancestor where we came from left
                BSTNode current = node;
                BSTNode p = parent;

                while (p != null && current == p.getRightChild()) {
                    current = p;
                    p = current.getParent();
                }

                if (p == null) {
                    return null;
                }

                return p.getData();
            }
        }

        return dictionary.successor(dictionary.getRoot(), key);
    }

    public static void main(String[] args) {
        String result, nextCommand;
        StringReader keyboard = new StringReader();

        if (args.length != 1) {
            System.out.println("Usage: java Query fileName");
            System.exit(0);
        }

        Query myProgram = new Query(args[0]);

        while (true) {
            nextCommand = keyboard.read("Enter next command: ");
            result = myProgram.processCommand(nextCommand);

            if (result.equals("end")) {
                break;
            }

            if (!result.isEmpty()) {
                System.out.println(result);
            }
        }
    }
}