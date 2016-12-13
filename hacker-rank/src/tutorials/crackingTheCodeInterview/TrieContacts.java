package tutorials.crackingTheCodeInterview;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
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
public class TrieContacts {
    
    /**
    ** class that represent a node in the Trie
    **/
    public class TrieNode{
        
        char letter;
        HashMap<Character, TrieNode> children = new HashMap<Character, TrieNode>();
        boolean isLeaf;
        int count;
        
        
        /**
        ** Constructor
        **/
        public TrieNode(){
            count = 0;
        }
        
        /**
        ** Constructor
        ** @param letter for a trie
        **/
        public TrieNode(char c){
            this.letter = c;
            count = 0;
        }
        
    }

    /**
     * @author cguzman@cguz.org
     *
     */
    public class Trie{
        
        private TrieNode root;
        
        public Trie(){
            root = new TrieNode();
        }
        
        public void add(String word){
        	
        	if(findNode(word)!=null)
        		return;
        	
            HashMap<Character, TrieNode> children = root.children;
            
            for(int i = 0; i < word.length(); i++){
                char c = word.charAt(i);
                
                TrieNode t;
                // if the letter exist in the children
                if(children.containsKey(c)){
                    t = children.get(c);
                }else{
                    t = new TrieNode(c);
                    children.put(c, t);
                }
                
                children = t.children;
                t.count++;
                
                // the leaf node
                if(i == word.length()-1){
                    t.isLeaf = true;
                }
            }
        }
        
        public int find(String text){
            TrieNode t = findNode(text);
            if(t != null){
                return t.count;
            }else{
                return 0;
            }
        }
        
        private TrieNode findNode(String text){
            Map<Character, TrieNode> children = root.children;
            TrieNode t = null;
            for(int i=0; i < text.length(); i++){
                char c = text.charAt(i);
                
                if(!children.containsKey(c)){
                	t = null;
                    break;
                }
                
                t = children.get(c);
                children = t.children;
            }
            return t;
        }
    }
    
    public static void main(String[] args) throws FileNotFoundException {
    	File file = new File("./TEST/tutorials/crackingTheCodeInterview/input.txt");
    	Scanner in = new Scanner(file);
        // Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        
        TrieContacts t = new TrieContacts();
        
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