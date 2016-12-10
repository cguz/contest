#include <cmath>
#include <ctime>
#include <string>
#include <cstdio>
#include <limits>
#include <vector>
#include <climits>
#include <cstring>
#include <cstdlib>
#include <fstream>
#include <numeric>
#include <sstream>
#include <iostream>

#define SIZE(a) sizeof(a)/sizeof(a[0])

#define ALPHABET_SIZE (26)

#define CHAR_INDEX(c) ((int)c-(int)'a')


using namespace std;

struct TrieNode{
	struct TrieNode *children[ALPHABET_SIZE];
	
	bool isLeaf;
	int count;
	
};

struct TrieNode *getNode(void){

	struct TrieNode *pNode = NULL;
	
	pNode = (struct TrieNode *) malloc(sizeof(struct TrieNode));
	
	if(pNode){
		int index;
		
		pNode->isLeaf = false;
		pNode->count = 0;
		
		for(index=0; index < ALPHABET_SIZE; index++){
			pNode->children[index] = NULL;
		}
		
	}
	
	return pNode;
}

struct TrieNode *findNode(struct TrieNode *root, string key){

	int level;
	int length = key.length();
	int index;
	struct TrieNode *temp = root;
	
	for(level = 0; level < length; level++){
		index = CHAR_INDEX(key[level]);
		
		if(!temp->children[index]){
			temp = NULL;
			break;
		}
		
		temp = temp->children[index];
	}
	
	return temp;
	
}

int find(struct TrieNode *root, string key){
    struct TrieNode *t = findNode(root, key);
    if(t){
        return t->count;
    }else{
        return 0;
    }
}

void add(struct TrieNode *root, string key){
	int level;
	int length = key.length();
	int index;
	
	/** if the word exist, we do not add it **/
    struct TrieNode *t = findNode(root, key);
	
	if(t)
		return;
		
	struct TrieNode *temp = root;
	for(level = 0; level < length; level++){
		index = CHAR_INDEX(key[level]);
		if(!temp->children[index])
			temp->children[index] = getNode();
		
		temp = temp->children[index];
		temp->count=temp->count+1;
	}
	
	temp->isLeaf = true;
}

int main(){
    int n;
    cin >> n;
    
    struct TrieNode *trie = getNode();
    
    for(int a0 = 0; a0 < n; a0++){
        string op;
        string contact;
        cin >> op >> contact;
        
        if(op == "add"){
        	add(trie, contact);
        }else{
 	       int r = find(trie, contact);
 	       cout << r;
        }
    }
    
    return 0;
}
