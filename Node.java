package domeserg1;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class Node {

	private int key;
	private byte[] data;
	
//////////////////constructors/////////////////////////
	public Node() {
		this.key=0;
		this.data= new byte[0];
	}	   
	public Node(int key) {
		this.key=key;
		this.data= new byte[0];
	}
	public Node(int key ,byte[] data ) {
		this.key=key;
		this.data=data;
	}
	public Node(byte[] data) {
		byte[] bytekey = Arrays.copyOfRange(data, 0, 4);		//take the first 4 bytes out of the input array we do it because we need to seperate the key 	https://stackoverflow.com/questions/11001720/get-only-part-of-an-array-in-java
		ByteBuffer wrapped = ByteBuffer.wrap(bytekey); 			//make the 4byte key into an integer																								//from https://stackoverflow.com/questions/7619058/convert-a-byte-array-to-integer-in-java-and-vice-versa
		this.key = wrapped.getInt(); 													
		this.data = Arrays.copyOfRange(data, 4, 32);			//take the bytes 4-32 and put the into the data byte
	}
	public Node(int key,int data) {
		java.nio.ByteBuffer datatobyte = java.nio.ByteBuffer.allocate(28);	//create a new bytebuffer for 28 bytes
		this.key=key;
		this.data=datatobyte.putInt(data).array();
	}
////////////////////////////////////////////////////////	
	
	public byte[] tobyteArray() {										//σχεδον copy paste απο διαφανειες εργαστηριου 3 η κλαση tobyteArray κανει merge το key και data σε ενα byte array
	    java.nio.ByteBuffer bb = java.nio.ByteBuffer.allocate(32);		//create a new bytebuffer for 32 bytes
		bb.putInt(key);													//put the key and data into the bb byte buffer
		bb.put(data);
		byte[] result=bb.array();										//make the bytebuffet into an array and return it
		return result;													
	}
	//getters///////////////////////
	public int getKey() {
		return key;
	}
	public byte[] getData() {
		return data;
	}

	
	
}
