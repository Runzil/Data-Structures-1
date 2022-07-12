package domeserg1;

import java.util.Arrays;

public class Page {
	private Node[] nodes= {new Node(),new Node(),new Node(),new Node()};
	private byte[] data;
///////////////constructors///////////////////////
	public Page(Node[] nodes) {
		this.data= new byte[128];												//data becomes a new byte array the contents do not bother us
		java.nio.ByteBuffer bb2= java.nio.ByteBuffer.allocate(128);				//bb2 is a new byte buffer of 128 bytes
		int count=0;
		for(Node currentnode:nodes) {											//loop for each node in nodes and currentnode becomes that node			//from https://www.geeksforgeeks.org/iterating-arrays-java/
			this.nodes[count]=currentnode;									
			bb2.put(currentnode.tobyteArray());									//put currentnode as an array to bb2
			count++;
		}
		data=bb2.array();
	}
	public Page(byte [] lotbytes) {
		this.data=lotbytes; 
		nodes[0]= new Node(Arrays.copyOfRange(lotbytes, 0, 32));				//break the 128 bytes into 4 32 byte chunks and make them nodes
		nodes[1]= new Node(Arrays.copyOfRange(lotbytes, 32, 64));
		nodes[2]= new Node(Arrays.copyOfRange(lotbytes, 64, 96));
		nodes[3]= new Node(Arrays.copyOfRange(lotbytes, 96, 128));
	}
//////////////////////////////////////////////////
	
	public void printdata() {					//prints the insides of the page
		for(Node currentnode:nodes) {			//for every node
			System.out.println(Arrays.toString(currentnode.tobyteArray()));	//print every node individualy
		}
		System.out.println(Arrays.toString(data)); //print all the nodes again at 1 line
	}
	public byte[] tobyteArray() {												//σχεδον copy paste απο διαφανειες εργαστηριου 3 η κλαση tobyteArray κανει merge το key και data σε ενα byte array
	    java.nio.ByteBuffer bb3 = java.nio.ByteBuffer.allocate(128);			//bb3 is a new bytebuffer
		bb3.put(nodes[0].tobyteArray());										//put each node to the bb3 bytebuffer
		bb3.put(nodes[1].tobyteArray());
		bb3.put(nodes[2].tobyteArray());
		bb3.put(nodes[3].tobyteArray());
		byte[] result=bb3.array();
		return result;
	}

	public Node[] getNodes() {		//node getter
		return nodes;
	}
	
	
	
	
}