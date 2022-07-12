package domeserg1;					
import java.io.FileNotFoundException;												//THIS CLASS ACTUALLY CONTAINS CODE FOR BOTH A AND C METHODS
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.Vector;



public class amethod {
	
	public static int numberofkeys = 10000;	

	
	String filename;
	String filenamecmethod;
	RandomAccessFile MyFile;
	RandomAccessFile MyFilecmethod;
	byte[] data= new byte[0];
	byte[] buffer=new byte[128];
	int diskaccess=0;
	int diskaccesscmethod=0;
	int numofwrites=0;
	Vector totaldiskaccesses=new Vector();
	Vector totaldiskaccessescmethod=new Vector();
	Vector Usedkeys=new Vector();
	Vector sortedUsedkeys=new Vector();
	Vector random20keys=new Vector();
	Vector keypositions=new Vector();
/////////////constructors///////////////////////
	public amethod(String filename,String filenamecmethod) {
		this.filename=filename;
		this.filenamecmethod=filenamecmethod;
		this.numofwrites=0;
	}
////////////////////////////////////////////////
		int[] usedkeys;

   void  generatekey() {											//from https://www.geeksforgeeks.org/java-math-random-method-examples/
        int max = 1000000; //1000000
        int min = 1; 
        int range = max - min + 1; 
        
        
        for (int i = 0; i < numberofkeys; i++) { //10000							//do the following for 10k times
            int rand = (int)(Math.random() * range) + min; 					//create a random number from 1 to 1000000 using math.random and casting it to int
                 
            while(Usedkeys.contains(rand)) {				//while rand exists in Usedkeys aka if the key is duplicated create a new rand
             rand = (int)(Math.random() * range) + min; 
            }
            Usedkeys.add(rand);								//add the random number to the usedkeys vector with the add() function
        }
        

	}
   
   
   
	int generatefile() {
		try {
			this.MyFile= new RandomAccessFile(filename,"rw");					//create a nwe file named MyFile with the read write commands
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

			int k=0;
			while(k<2500*4) {													//with 10.000 nodes we get 2.500 pages
			Node first = new Node((int)Usedkeys.toArray()[k],data);				//make 4 nodes using the constructor that uses only a key
			k=k+1;
			Node second=new Node((int)Usedkeys.toArray()[k],data);
			k=k+1;
			Node third=new Node((int)Usedkeys.toArray()[k],data);
			k=k+1;
			Node fourth=new Node((int)Usedkeys.toArray()[k],data);
			k=k+1;
			numofwrites=numofwrites+1;
			Page MyPage=new Page(new Node[] {first,second,third,fourth});		//make a page using the 4 nodes made
			buffer=MyPage.tobyteArray();
		try {
			MyFile.write(buffer);
		} catch (IOException e) {
			e.printStackTrace();
		}
			}
		return 1;
	}
	int findkey() {
		
		
			int max2 = 10000; 
	        int min2 = 1; 
	        int range2 = max2 - min2 + 1; 
	        	        
	        for (int i = 0; i < 20; i++) {									//creating 20random locations
	            int rand2 = (int)(Math.random() * range2) + min2; 			//create a random number from 1 to 10000 using math.random and casting it to int (we create them so we feed them to the key array to get 20 random unique keys)
	                 
	            while(random20keys.contains(rand2)) {						//while rand exists in random20keys aka if the location is duplicated create a new rand
	             rand2 = (int)(Math.random() * range2) + min2; 
	            }
	            random20keys.add(rand2);									//add the location to the random20keys vector using add()
	        }
	        
//------------------------------------TAKING 20 DIFFERENT TESTS------------------------------------//
	     for(int x=0;x<20;x++) {															//for 20 loops

			int currentkey=(int)Usedkeys.toArray()[(int)random20keys.toArray()[x]];	 		//take each element from the random20keys (casted as int) and use it as an index for Usedkeys array (casted as int)
	
		try {
			MyFile.seek(0);																	//move the cursor of Myfile to position 0
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		diskaccess=0; 																		//initialize diskaccess to 0
		while(true) {
		try {
			MyFile.read(buffer);															//read 128 bytes from MyFile and store them in the buffer array
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
		diskaccess=diskaccess+1;
		byte[] possiblekey1=Arrays.copyOfRange(buffer, 0, 4);								//take each of the 4 keys of the buffer by taking 4 specific bytes from the buffer each time
		byte[] possiblekey2=Arrays.copyOfRange(buffer, 32, 36);
		byte[] possiblekey3=Arrays.copyOfRange(buffer, 64, 68);
		byte[] possiblekey4=Arrays.copyOfRange(buffer, 96, 100);
		
		ByteBuffer wrapped1 = ByteBuffer.wrap(possiblekey1); 								//make each of the 4 bytes into an int
		int num1 = wrapped1.getInt(); 
		
		ByteBuffer wrapped2 = ByteBuffer.wrap(possiblekey2); 
		int num2 = wrapped2.getInt(); 
		
		ByteBuffer wrapped3 = ByteBuffer.wrap(possiblekey3); 
		int num3 = wrapped3.getInt(); 
		
		ByteBuffer wrapped4 = ByteBuffer.wrap(possiblekey4); 
		int num4 = wrapped4.getInt(); 

		if(num1 ==currentkey|| num2 ==currentkey||num3 ==currentkey||num4 ==currentkey) {			//if any of the numbers is equal to the current key then break out of the loop
			break;
		}
		}
		totaldiskaccesses.add(diskaccess);															//add the diskaccess to the totaldiskaccesses vector using the add() function
		}
//------------------------------------TAKING 20 DIFFERENT TESTS------------------------------------//

	      int Mo=0;
	      for(int z=0;z<20;z++) {													//for 20 loop
	    	  Mo=Mo+(int)totaldiskaccesses.toArray()[z];							// add all the elements of totaldiskaccesses to Mo then divide it by 20 to find the median value 
	      }
	      Mo=Mo/20;																	
	      System.out.println("mesos oros="+Mo);
	      //System.out.println("num of writes="+numofwrites);
		return 1;
	}

	
	
	
	
	
	////////getters////////////////////////
	public byte[] getData() {
		return data;
	}

	
	public Vector getUsedkeys() {
		return Usedkeys;
	}

	
	public Vector getRandom20keys() {
		return random20keys;
		}

	public RandomAccessFile getMyFile() {
		return MyFile;
	}



///////////////////////////////////Gamma method//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void gammaSort() {
		
		Vector sortedUsedkeys = new Vector(Usedkeys);						//create a new vector								//from https://stackoverflow.com/questions/11414027/copy-vector-in-a-vector-in-java
		Collections.sort(sortedUsedkeys);									// sorte the vector using the collections			//create the sorted key vector
		
		
		
//////////////////////////////////////////////////////////////////////////creating the sorted file//////////////////////////////////////
		try {
			this.MyFilecmethod= new RandomAccessFile(filenamecmethod,"rw");		//create a nwe file named MyFilecmethod with the read write commands
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

			int k=0;
			while(k<2500*4) {													//with 10.000 nodes we get 2.500 pages
			Node firstc = new Node((int)sortedUsedkeys.toArray()[k],data);		//take each element of sortedUsedkeys and create a node with it and data
			k=k+1;
			Node secondc=new Node((int)sortedUsedkeys.toArray()[k],data);
			k=k+1;
			Node thirdc=new Node((int)sortedUsedkeys.toArray()[k],data);
			k=k+1;
			Node fourthc=new Node((int)sortedUsedkeys.toArray()[k],data);
			k=k+1;
			
			Page MyPagec=new Page(new Node[] {firstc,secondc,thirdc,fourthc});		//create a new page with the 4 previously created nodes in each loop
			buffer=MyPagec.tobyteArray();											//make the page into a byte array and write it into MyFilecmethod
		try {
			MyFilecmethod.write(buffer);
		} catch (IOException e) {
			e.printStackTrace();
		}
			}
//////////////////////////////////////////////////////////////////////////creating the sorted file//////////////////////////////////////

			
			
			
			
			try {
				MyFilecmethod.seek(0);												//get the cursor of MyFilecmethod to 0
				MyFilecmethod.read(buffer);											//read 128 bytes from MyFilecmethod and save it to the buffer array
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			byte[] possiblekey1c=Arrays.copyOfRange(buffer, 0, 4);					//take each key of the buffer by taking 4 specific bytes each time
			byte[] possiblekey2c=Arrays.copyOfRange(buffer, 32, 36);	
			byte[] possiblekey3c=Arrays.copyOfRange(buffer, 64, 68);
			byte[] possiblekey4c=Arrays.copyOfRange(buffer, 96, 100);
			
			ByteBuffer wrapped1 = ByteBuffer.wrap(possiblekey1c); 					//turn each of the 4 bytes into integers
			int num1 = wrapped1.getInt(); 
			
			ByteBuffer wrapped2 = ByteBuffer.wrap(possiblekey2c); 
			int num2 = wrapped2.getInt(); 
			
			ByteBuffer wrapped3 = ByteBuffer.wrap(possiblekey3c); 
			int num3 = wrapped3.getInt(); 
			
			ByteBuffer wrapped4 = ByteBuffer.wrap(possiblekey4c); 
			int num4 = wrapped4.getInt(); 

		}

	
	
			
	    void binarySearch() 										//from https://www.geeksforgeeks.org/binary-search/
	    { 
	    	
	    	for(int i=0;i<20;i++) {											//for 20 loops
	    		diskaccesscmethod=0;
	    		int index=(int)random20keys.toArray()[i];					//take each element from the random20keys (casted as int) and use it as an index for Usedkeys array (casted as int)
	    		int cmethodcurrentkey=(int)Usedkeys.toArray()[index];
	    		
	    		
	        int left = 0, right = 2500 - 1; 	//2500 is the number of nodes
	        
	        while (true) {														//infinite loop
	            int middle = left + (right - left) / 2; 
	  
	           
	            try {
	            	MyFilecmethod.seek(middle*128);								//get the cursor of MyFilecmethod to the middle*128 positions (since each page has 128 bytes)
	    		} catch (IOException e1) {
	    			e1.printStackTrace();
	    		}
	        		try {
	        			MyFilecmethod.read(buffer);								//read 128 bytes from MyFilecmethod and store them in the buffer byte array
	        		} catch (IOException e) {
	        			e.printStackTrace();
	        		}
	        		diskaccesscmethod=diskaccesscmethod+1;
	        		byte[] possiblekey1c=Arrays.copyOfRange(buffer, 0, 4);				//take each of the 4 keys of the buffer
	        		byte[] possiblekey2c=Arrays.copyOfRange(buffer, 32, 36);		
	        		byte[] possiblekey3c=Arrays.copyOfRange(buffer, 64, 68);			
	        		byte[] possiblekey4c=Arrays.copyOfRange(buffer, 96, 100);			
	        		
	        		ByteBuffer wrapped1c = ByteBuffer.wrap(possiblekey1c); 				//make each of the 4 bytes into a integer
	        		int num1 = wrapped1c.getInt(); 
	        		
	        		ByteBuffer wrapped2c = ByteBuffer.wrap(possiblekey2c); 
	        		int num2 = wrapped2c.getInt(); 
	        		
	        		ByteBuffer wrapped3c = ByteBuffer.wrap(possiblekey3c); 
	        		int num3 = wrapped3c.getInt(); 
	        		
	        		ByteBuffer wrapped4c = ByteBuffer.wrap(possiblekey4c); 
	        		int num4 = wrapped4c.getInt();       
	            
	            
	        		
	            
	            
	            
	            
	            if (num1 == cmethodcurrentkey ||num2 == cmethodcurrentkey || num3 == cmethodcurrentkey|| num4 == cmethodcurrentkey) {				// Check if the key is present at mid 			
	            	break;
	            }
	  
	     
	            if (num4 < cmethodcurrentkey) {						 // If the wanted key is greater than num4, ignore left half
	                left = middle + 1; 
	            }
	            else {
	            	right = middle - 1; 								// If the wanted key is smaller, ignore right half 
	            }
	            
	            
	            
	            }

	        totaldiskaccessescmethod.add(diskaccesscmethod);		//add diskaccesscmethod to the totaldiskaccessescmethod method
	    	}
	  
	        
	        
	        
	     
		
	    	
		      int Mocmethod=0;										
		      for(int z=0;z<20;z++) {															//for 20 loops
		    	  Mocmethod=Mocmethod+(int)totaldiskaccessescmethod.toArray()[z];				//add each element of totaldiskaccessescmethod vector and divide by 20 to find the median
		      }
		      Mocmethod=Mocmethod/20;
		      System.out.println("cmethod mesos oros="+Mocmethod);
	    	
		
		
}

		
	    
	    
	    
	    
	    //getter
	    public Vector getSortedUsedkeys() {
			Vector sortedUsedkeys = new Vector(Usedkeys);					//from https://stackoverflow.com/questions/11414027/copy-vector-in-a-vector-in-java
			Collections.sort(sortedUsedkeys);								//create the sorted key vector
			return sortedUsedkeys;
		}



	    void closeall() {
	    	try {
				MyFile.close();
				MyFilecmethod.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	    
	    
	    
}	   

	
	
	
	
	
	
	
	
		
	
	

	
	
	
	
	
	
	
	
	
	

