package domeserg1;

public class indexnode implements Comparable<indexnode> {
	int key;													//each node will contain a key and a (page)position
	int pos;

//////////////////////constructor///////////////////////////////
	public indexnode(int key , int pos) {
		this.key=key;
		this.pos=pos;
	}
////////////////////////////////////////////////////////////////
	
/////////////////////////////////getters/////////////////////
	public int getKey() {
		return key;
	}
	public int getPos() {
		return pos;
	}
/////////////////////////////////////////////////////////////
	
    public int compareTo(indexnode indexnode) {				//a line of code used to sort an array of indexnodes based on the key	from https://www.codejava.net/java-core/collections/sorting-arrays-examples-with-comparable-and-comparator
        return this.key - indexnode.key;
    }
	
	
	
	
	
	
}
