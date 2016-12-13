package tutorial.crackingTheCodeInterview;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author cguzman@cguz.org
 * 
 * Contacts application
 * 
 * The application must perform two types of operations:
 * 1.) add name, where name is a string denoting a contact name. 
 * This must store as a new contact in the application.
 * 2.) find partial, where partial is a string denoting a partial name 
 * to search the application for. It must count the number of contacts 
 * starting with and print the count on a new line.
 */
public class TrieContacts2 {
    
    /**
    ** class that represent a node in the Trie
    **/
    public class TrieNode{
        
        TrieNode[] children;
        boolean isLeaf;
        int count;
        
        
        /**
        ** Constructor
        **/
        public TrieNode(){
            count = 0;
            children = new TrieNode[26];
        }
        
    }

    /**
     * @author cguzman@cguz.org
     * Structure to save the names
     */
    public class Trie{
        
        /** root node of the trie **/
        private TrieNode root;
        
        public Trie(){
            root = new TrieNode();
        }
        
        /**
         * This function add a string value to the Trie
         * The worst/best/average time complexity is O(|word|)
         * 
         * @param word a String value to add
         */
        public void add(String word){
        	
        	/** if the word exist, we do not add it **/
        	if(findNode(word)!=null)
        		return;
        	
            TrieNode children = root;
            TrieNode temp;
            
            /** for each letter of the word, we insert it in the Trie **/
            for(int i = 0; i < word.length(); i++){
                char c = word.charAt(i);
                int index = c - 'a';
                
                // if the letter exist in the children
                if(children.children[index]==null){
                	temp = new TrieNode();
                	children.children[index] = temp;
                	children = temp;
                }else{
                    children = children.children[index];
                }
                
                children.count++;
                
                // the leaf node
                if(i == word.length()-1){
                    children.isLeaf = true;
                }
            }
        }
        
        /**
         * This function find  the total number of elements 
         * that start with a given String value text in the Trie
         * 
         * @param text String to find in the Trie
         * @return the total number of elements that start with the given String value
         */
        public int find(String text){
            TrieNode t = findNode(text);
            if(t != null){
                return t.count;
            }else{
                return 0;
            }
        }
        
        /**
         * 
         * This function find a String value in the Trie
         * 
         * The worst/best/average time complexity is O(|word|)
         * 
         * @param text
         * @return
         */
        private TrieNode findNode(String text){
        	/** Starting from the root **/
            TrieNode children = root;
            TrieNode t = null;
            
            /** for each letter of the word, we insert it in the Trie **/
            for(int i=0; i < text.length(); i++){
                char c = text.charAt(i);
                int index = c - 'a';
                
                if(children.children[index]==null){
                	t = null;
                    break;
                }
                
                t = children.children[index];
                children = t;
            }
            return t;
        }
    }
    
    public static void main(String[] args) throws FileNotFoundException {
    	File file = new File("./TEST/tutorial/crackingTheCodeInterview/input.txt");
    	Scanner in = new Scanner(file);
        // Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        
        TrieContacts2 t = new TrieContacts2();
        
        Trie trie = t.new Trie();
        
        for(int a0 = 0; a0 < n; a0++){
            String op = in.next();
            String contact = in.next();
            
            if(op.equals("add")){
                trie.add(contact);
            }else{
                System.out.println(trie.find(contact));
            }
            
        }
    }
}