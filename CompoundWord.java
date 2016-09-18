import java.util.*;
public class CompoundWord{
	
	public static void main(String[] args){
		Trie dictionary = new Trie();

		// take text/user input until EOF condition
		// insert each line into the dictionary
		Scanner scan = new Scanner(System.in);
		String word = scan.nextLine();
		dictionary.insert(word);
		while(scan.hasNext()){
			word = scan.nextLine();
			dictionary.insert(word);
		}

		
		// get the list of compound words
		Iterator iterator = dictionary.findRemainders().iterator();
		while(iterator.hasNext()){
			System.out.print(iterator.next()+"\n");
		}
	}
}

class Trie{
	Node root;
	LinkedList <String>compounds = new LinkedList<String>();
	LinkedList <String>remainders = new LinkedList<String>();

	public Trie(){
		this.root=new Node(' ');
	}

	public void insert(String word){
		Node current = this.root;
		for (int i=0;i<word.length();i++){							//iterate through each letter
			Character letter 		= 	word.charAt(i);
			Boolean found 			= 	false;		
			int letterIndex 		= 	current.searchChildren(letter);

			if(letterIndex!=-1){									//if letter is found among current nodes children
				Node child = current.children.get(letterIndex);
				if(child.fullWord){
					String remainder = word.substring(i+1);
					compounds.add(word);
					remainders.add(remainder);
				}
				current = child;
			}
			else{
				current=current.addChild(letter);
			}
			if(i==word.length()-1){
				current.isFullWord();
			}
		}
	}

	public LinkedList<String> findRemainders(){
		for (int i=0; i<remainders.size(); i++ ) {
			String word = remainders.get(i);
			System.out.print(word);
			if(!findString(word)){
				compounds.remove(i);
				remainders.remove(i);
			}
		}
		return compounds;
	}

	public Boolean findString(String word){
		Node current = this.root;
		Boolean found = true;
		for (int i=0; i<word.length() ; i++) {
			Character letter 	= 	word.charAt(i);
			int letterIndex 	= 	current.searchChildren(letter);
			if(letterIndex!=-1){
				Node child 		= 	current.children.get(letterIndex);
				current 		= 	child;				
			}
			else{
				found = false;
			}	
		}
		return found;
	}
}

class Node{
	public Character value;
	public LinkedList <Node>children;
	public Boolean fullWord;
	public Node parent;

	public Node(Character letter){
		this.value 		=	letter;
		this.children 	= 	new LinkedList<Node>();
		this.fullWord 	= 	false;
	}
	public void isFullWord(){
		this.fullWord 	= 	true;
	}

	public Node addChild(Character letter){
		Node child = new Node(letter);
		child.value = letter;
		child.parent = this;
		this.children.add(child);
		return child;
	}

	public int searchChildren(Character letter){
		int index = -1;
		for (int i=0; i<this.children.size(); i++ ) {
			if(this.children.get(i).value==letter){
				index = i;
			}
		}
		return index;
	}
}
