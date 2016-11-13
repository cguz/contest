package algorithms.Sorting.TheFullCountingSort;

import java.io.*;
import java.util.*;

public class Solution {

    public static class Node{
        
        List<String> value;
        
        public Node(String n){
            value = new ArrayList<String>();
            value.add(n);
        }
        
        public void add(String n){
            value.add(n);
        }
        
        public void print(){
            for(String n: value){
                System.out.print(n+" ");
            }
        }
    }
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        
        int N = in.nextInt();
        String[] temp;
        Node[] a = new Node[100];
        
        in.nextLine();
        for(int i=0; i <N; i++){
            temp = in.nextLine().split(" ");
            int index = Integer.parseInt(temp[0]);
            String value = "-";
            if(i >= N/2)
                value = temp[1];
            
            if(a[index] == null){
                a[index] = new Node(value);
            }else{
                a[index].add(value);
            }
        }
        
        for(int i=0; i <a.length; i++){
            if(a[i] != null)
                a[i].print();
        }
        
    }
}