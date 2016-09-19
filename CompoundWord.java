import java.util.*;
public class CompoundWord{
	
	public static void main(String[] args){

		Trie dictionary = new Trie();

		// Store possible compound words in a hash of { prefix -> [suffixes] }
		Map<String, ArrayList<String>> compounds = new HashMap<String, ArrayList<String>>();


		// take text/user input until EOF condition
		// insert each line into the dictionary
		Scanner scan = new Scanner(System.in);
		String input_word = scan.nextLine();
		dictionary.insert(input_word);
		
		while(scan.hasNext()){
			input_word = scan.nextLine();
			ArrayList<String> prefixes = dictionary.get_prefixes(input_word);
			for(String p: prefixes){
				ArrayList<String> suffixes = compounds.get(p);
				if(suffixes == null){
					suffixes = compounds.put(p, new ArrayList<String>());
				}  
				compounds.get(p).add(input_word.replace(p, ""));			
			}
			dictionary.insert(input_word);
		}

		// Iterate through all prefix + suffix combinations
		for (Map.Entry<String,ArrayList<String>> entry : compounds.entrySet()) {
		  String prefix = entry.getKey();
		  ArrayList<String> suffixes = entry.getValue();
		  ArrayList<String> verified_compounds = new ArrayList<String>();

		  // verify suffixes
		  for(String s: suffixes){
		  	if (dictionary.findString(s)){
		  		verified_compounds.add(prefix + s);
		  	}
		  }

		  // pretty print
		  if(verified_compounds.size() > 0){
		  	System.out.println(prefix);
		  	for(String vc : verified_compounds){
		  		System.out.println("    "+vc);
		  	}
		  }
		}
	}
}

class Trie{
	Node root;

	public Trie(){
		this.root=new Node(' ');
	}

	public ArrayList<String> get_prefixes(String word){
		Node current = this.root;
		ArrayList<String> prefixes = new ArrayList<String>();
		String prefix = "";

		for (int i=0;i<word.length()-1;i++){							//iterate through each letter
			Character letter 		= 	word.charAt(i);
			prefix += letter;
			int letterIndex 		= 	current.searchChildren(letter);

			if(letterIndex!=-1){									//if letter is found among current nodes children
				current = current.children.get(letterIndex);
				if(current.fullWord){
					String remainder = word.substring(i+1);
					prefixes.add(prefix);
				}
			}
		}
		return prefixes;
	}

	public void insert(String word){
		Node current = this.root;
		for (int i=0;i<word.length();i++){							//iterate through each letter

			Character letter 		= 	word.charAt(i);
			int letterIndex 		= 	current.searchChildren(letter);

			if(letterIndex!=-1){									//if letter is found among current nodes children
				current = current.children.get(letterIndex);
			}
			else{
				current=current.addChild(letter);
			}
			if(i==word.length()-1){
				current.isFullWord();
			}
		}
	}

	public Boolean findString(String word){
		Node current = this.root;
		for (int i=0; i<word.length() ; i++) {
			Character letter 	= 	word.charAt(i);
			int letterIndex 	= 	current.searchChildren(letter);
			if(letterIndex!=-1){
				current 		= 	current.children.get(letterIndex);
			}
			else { return false; }
		}
		return current.fullWord;
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

