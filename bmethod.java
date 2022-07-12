package domeserg1;
																			//THIS CLASS ACTUALLY CONTAINS CODE FOR BOTH B AND D METHODS
import java.io.FileNotFoundException;		//imports
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Vector;



public class bmethod {

	public static int numberofkeys = 10000;	

	
	int diskaccess=0;
	String filenamedmethod;
	String filename;
	amethod amethod;
	RandomAccessFile keyfile;
	RandomAccessFile keyfiledmethod;
	RandomAccessFile datafile;
	byte[] bufferb=new byte[128];
	byte[] keyandpos=new byte[128];
	java.nio.ByteBuffer bufferb2 = java.nio.ByteBuffer.allocate(128);
	Vector totaldiskaccesses=new Vector();
	Vector numofpage=new Vector();
	indexnode[] indexes=new indexnode[10000];
	Vector diskaccessesdmethodtotal=new Vector();
	int diskaccessesdmethod=0;
	
	int[] possiblepage=new int[17];
	
/////////////constructors///////////////////////
public bmethod(String filename,amethod amethod,String filenamedmethod) {
	this.filename=filename;
	this.amethod=amethod;
	this.filenamedmethod=filenamedmethod;
}
////////////////////////////////////////////////
	
	
	void createkeyfile() {
		amethod.getUsedkeys();													//get the key vector from the amethod
		try {
			this.keyfile= new RandomAccessFile(filename,"rw");		//keyfile			//	open the keyfile with the read write command
		} catch (FileNotFoundException e) {										// if an exception occurs print its details
			e.printStackTrace();
		}
		
		int numofpage=0;
		
		for(int i=0;i<numberofkeys;i++) {													

				int ckey=(int)amethod.getUsedkeys().toArray()[i];				//turn the usedkeys vector of amethod into a array and cast it as an int do this for every of the 10k keys and save each in time in ckey	
			
				if(i%4==0 &&i!=0) {												//every 4 iterations(if imodulo0==0) add 1 to the number of pages that is because a page contains 4 keys
					numofpage=numofpage+1;
				}

				indexes[i]=new indexnode(ckey,numofpage);						//create a new indexnode each time with the key and position of each key of the used keys arrays of a method
		}
		
		bufferb2.clear();														//clear the buffer
		for(int i=0;i<numberofkeys;i=i+16) {											//put 16+16 integers (every key and position by the end of the loop) each time in the bytebuffer b2 (by getting the key and pos from index node)
			
			bufferb2.putInt(indexes[i].getKey());
			bufferb2.putInt(indexes[i].getPos());
			
			bufferb2.putInt(indexes[i+1].getKey());
			bufferb2.putInt(indexes[i+1].getPos());
			
			bufferb2.putInt(indexes[i+2].getKey());
			bufferb2.putInt(indexes[i+2].getPos());
			
			bufferb2.putInt(indexes[i+3].getKey());
			bufferb2.putInt(indexes[i+3].getPos());
			
			bufferb2.putInt(indexes[i+4].getKey());
			bufferb2.putInt(indexes[i+4].getPos());
			
			bufferb2.putInt(indexes[i+5].getKey());
			bufferb2.putInt(indexes[i+5].getPos());
			
			bufferb2.putInt(indexes[i+6].getKey());
			bufferb2.putInt(indexes[i+6].getPos());
			
			bufferb2.putInt(indexes[i+7].getKey());
			bufferb2.putInt(indexes[i+7].getPos());
			
			bufferb2.putInt(indexes[i+8].getKey());
			bufferb2.putInt(indexes[i+8].getPos());
			
			bufferb2.putInt(indexes[i+9].getKey());
			bufferb2.putInt(indexes[i+9].getPos());
			
			bufferb2.putInt(indexes[i+10].getKey());
			bufferb2.putInt(indexes[i+10].getPos());
			
			bufferb2.putInt(indexes[i+11].getKey());
			bufferb2.putInt(indexes[i+11].getPos());
			
			bufferb2.putInt(indexes[i+12].getKey());
			bufferb2.putInt(indexes[i+12].getPos());
			
			bufferb2.putInt(indexes[i+13].getKey());
			bufferb2.putInt(indexes[i+13].getPos());
			
			bufferb2.putInt(indexes[i+14].getKey());
			bufferb2.putInt(indexes[i+14].getPos());
				
			bufferb2.putInt(indexes[i+15].getKey());
			bufferb2.putInt(indexes[i+15].getPos());
				try {
					keyfile.write(bufferb2.array());							//write the buffeb2 to the keyfile (the cursor of the file is automatically updated so no need for a seek())
				} catch (IOException e) {
					e.printStackTrace();
				}
				bufferb2.clear();
			}
		
		
		
		
		
		
		
	}
		
	void searchkey() {
		
		for(int y=0;y<20;y++) {
			int deikths=(int)this.amethod.getRandom20keys().toArray()[y];					//deikths becomes an element of the random20keys (which is in fact the positions of the 20 keys in the array which holds them all) so we get it from the amethod turn it into an array and cast it as an int
			int wantedkey=(int)this.amethod.getUsedkeys().toArray()[deikths];				//put the deikths as an index to the getusedkeys array (turned from a vector) and receive an object which we cast into an int and that int is one of the 20 keys we need to search for		
			
			diskaccess=0;
			
			int breaktotal=0;
			try {
				this.keyfile.seek(0);							//go to keyfile and have the cursor take the position 0
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			
			
			while(true) {											//infinite loop
			
			try {
				this.keyfile.read(bufferb);							//read from the keyfile and store the contents in the bufferb array(the cursor of the file is automatically updated so no need for a seek())	
				diskaccess=diskaccess+1;							//add +1 to the diskaccess 
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			

				for(int z=0;z<2*16;z=z+2) {
					byte[] key1=Arrays.copyOfRange(bufferb, 0+4*z, 4+4*z);		//check each key by taking the first 0-4 bytes and then alternately skipping 4 and take 4 again up to bytes 120-124
					ByteBuffer wrapped = ByteBuffer.wrap(key1);					//turn the 4 bytes into an integer
					int possiblekey = wrapped.getInt(); 						//
			
					byte[] pos1=Arrays.copyOfRange(bufferb, 4+4*z, 8+4*z);		//check each position by taking the first 4-8 bytes and then alternately skipping 4 and take 4 again up to bytes 124-128
					ByteBuffer wrapped2 = ByteBuffer.wrap(pos1);				//turn the 4 bytes into an integer
					int possibleposition = wrapped2.getInt(); 
			
						if(possiblekey==wantedkey) {							//if we find the key 
							this.numofpage.add(possibleposition);				//save the possibleposition to the numofpage vector
							breaktotal=1;
							break;											    //break out of the for loop (inner loop)
						}
					}
				
				if(breaktotal==1) {												//we need to break out of both loops so we use a variable to break out of the outerloop
					break;
				}
				
				
			}
			
		
			try {
				this.amethod.getMyFile().seek((int)numofpage.toArray()[y]*128);		//let the cursor of the MyFile of amethod to the numofpage*128 (since a page has 128 bytes) 
				this.amethod.getMyFile().read(bufferb);								//read 128 bytes after the cursor and save them in bufferb				
				
				
				byte[] possiblekey1=Arrays.copyOfRange(bufferb, 0, 4);				//take the 4 keys of the page found at positions 0-4,32-34,64-68,96-100
				byte[] possiblekey2=Arrays.copyOfRange(bufferb, 32, 36);			
				byte[] possiblekey3=Arrays.copyOfRange(bufferb, 64, 68);			
				byte[] possiblekey4=Arrays.copyOfRange(bufferb, 96, 100);			
				
				ByteBuffer wrapped1 = ByteBuffer.wrap(possiblekey1); 				//make all the sets of 4bytes into integers
				int num1 = wrapped1.getInt(); 
				
				ByteBuffer wrapped2 = ByteBuffer.wrap(possiblekey2); 
				int num2 = wrapped2.getInt(); 
				
				ByteBuffer wrapped3 = ByteBuffer.wrap(possiblekey3); 
				int num3 = wrapped3.getInt(); 
				
				ByteBuffer wrapped4 = ByteBuffer.wrap(possiblekey4); 
				int num4 = wrapped4.getInt(); 

				diskaccess=diskaccess+1;
				
				
				this.totaldiskaccesses.add(diskaccess);					//add the diskaccesseses to the diskaccess vector
			} catch (IOException e) {
				e.printStackTrace();
			}
	
			
		}
		
		int Mo=0;
	      for(int z=0;z<20;z++) {													//add the total of the 20 diskaccessess and divide them by 20 to find the median
	    	
	    	  Mo=Mo+(int)totaldiskaccesses.toArray()[z];					//turn totadiskaccesses from a vector to an array (to pick a specific element easier) and cast it as an int
	      }
	      Mo=Mo/20;
	      System.out.println("mesos oros="+Mo);
		
		
	
	}
	
	void debug() {
			try {
				this.keyfile.seek(0);
				this.keyfile.read(bufferb);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			byte[] key1=Arrays.copyOfRange(bufferb, 0, 4);
			byte[] pos1=Arrays.copyOfRange(bufferb, 4, 8);
			byte[] key2=Arrays.copyOfRange(bufferb, 8, 12);
			byte[] pos2=Arrays.copyOfRange(bufferb, 12, 16);
			byte[] key3=Arrays.copyOfRange(bufferb, 16, 20);
			byte[] pos3=Arrays.copyOfRange(bufferb, 20, 24);
			byte[] key4=Arrays.copyOfRange(bufferb, 24, 28);
			byte[] pos4=Arrays.copyOfRange(bufferb, 28, 32);
			byte[] key5=Arrays.copyOfRange(bufferb, 32, 36);
			byte[] pos5=Arrays.copyOfRange(bufferb, 36, 40);
			
			
			ByteBuffer wrapped = ByteBuffer.wrap(key1);
			int num1 = wrapped.getInt(); 
			
			ByteBuffer wrapped2 = ByteBuffer.wrap(pos1);
			int num2 = wrapped2.getInt(); 
			
			ByteBuffer wrapped3 = ByteBuffer.wrap(key2);
			int num3 = wrapped3.getInt(); 
			
			ByteBuffer wrapped4 = ByteBuffer.wrap(pos2);
			int num4 = wrapped4.getInt(); 
			
			ByteBuffer wrapped5 = ByteBuffer.wrap(key3);
			int num5 = wrapped5.getInt(); 
			
			ByteBuffer wrapped6 = ByteBuffer.wrap(pos3);
			int num6 = wrapped6.getInt(); 
			
			ByteBuffer wrapped7 = ByteBuffer.wrap(key4);
			int num7 = wrapped7.getInt(); 
			
			ByteBuffer wrapped8 = ByteBuffer.wrap(pos4);
			int num8 = wrapped8.getInt(); 
			
			ByteBuffer wrapped9 = ByteBuffer.wrap(key5);
			int num9 = wrapped9.getInt(); 		
			
			ByteBuffer wrapped91 = ByteBuffer.wrap(pos5);
			int num10 = wrapped91.getInt(); 				
			
			
			
			
			System.out.println("key1="+num1+" page1="+num2);
			System.out.println("key2="+num3+" page2="+num4);
			
			System.out.println("key3="+num5+" page3="+num6);
			System.out.println("key4="+num7+" page4="+num8);
			
			System.out.println("key5="+num9+" page5="+num10);
		}

	void debug2() {
			System.out.println("==================");
			for(int p=0;p<3;p++) {
			try {
				this.keyfile.seek(128*p);
				this.keyfile.read(bufferb);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
			for(int z=0;z<2*16;z=z+2) {
			
			byte[] key1=Arrays.copyOfRange(bufferb, 0+4*z, 4+4*z);
			byte[] pos1=Arrays.copyOfRange(bufferb, 4+4*z, 8+4*z);
			
			ByteBuffer wrapped = ByteBuffer.wrap(key1);
			int num1 = wrapped.getInt(); 
			
			ByteBuffer wrapped2 = ByteBuffer.wrap(pos1);
			int num2 = wrapped2.getInt(); 
			
			System.out.println("key="+num1+" page="+num2);
		
			}
			}
			
		}
		
///////////////////////////////////////////////////////////////////////////METHODOSD////////////////////////////////////////////
	
	void dmethod() {
		
		Arrays.sort(indexes);		//from https://www.codejava.net/java-core/collections/sorting-arrays-examples-with-comparable-and-comparator
		
		try {
			this.keyfiledmethod= new RandomAccessFile(filenamedmethod,"rw");		//lekker		//try to create a new RandomAccessFile with read write
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
		bufferb2.clear();															//clear the bufferb2
		for(int i=0;i<numberofkeys;i=i+16) {												//get each key and pos of  128 byte chunks and store them in bufferb2
																						
			bufferb2.putInt(indexes[i].getKey());									
			bufferb2.putInt(indexes[i].getPos());									
			
			bufferb2.putInt(indexes[i+1].getKey());
			bufferb2.putInt(indexes[i+1].getPos());
			
			bufferb2.putInt(indexes[i+2].getKey());
			bufferb2.putInt(indexes[i+2].getPos());
			
			bufferb2.putInt(indexes[i+3].getKey());
			bufferb2.putInt(indexes[i+3].getPos());
			
			bufferb2.putInt(indexes[i+4].getKey());
			bufferb2.putInt(indexes[i+4].getPos());
			
			bufferb2.putInt(indexes[i+5].getKey());
			bufferb2.putInt(indexes[i+5].getPos());
			
			bufferb2.putInt(indexes[i+6].getKey());
			bufferb2.putInt(indexes[i+6].getPos());
			
			bufferb2.putInt(indexes[i+7].getKey());
			bufferb2.putInt(indexes[i+7].getPos());
			
			bufferb2.putInt(indexes[i+8].getKey());
			bufferb2.putInt(indexes[i+8].getPos());
			
			bufferb2.putInt(indexes[i+9].getKey());
			bufferb2.putInt(indexes[i+9].getPos());
			
			bufferb2.putInt(indexes[i+10].getKey());
			bufferb2.putInt(indexes[i+10].getPos());
			
			bufferb2.putInt(indexes[i+11].getKey());
			bufferb2.putInt(indexes[i+11].getPos());
			
			bufferb2.putInt(indexes[i+12].getKey());
			bufferb2.putInt(indexes[i+12].getPos());
			
			bufferb2.putInt(indexes[i+13].getKey());
			bufferb2.putInt(indexes[i+13].getPos());
			
			bufferb2.putInt(indexes[i+14].getKey());
			bufferb2.putInt(indexes[i+14].getPos());
				
			bufferb2.putInt(indexes[i+15].getKey());
			bufferb2.putInt(indexes[i+15].getPos());
				try {
					keyfiledmethod.write(bufferb2.array());					//write in the keyfiledmethod file the bufferb2
				} catch (IOException e) {
					e.printStackTrace();
				}
				bufferb2.clear();
			}

	}
	
	

	void binarySearch() 										//from https://www.geeksforgeeks.org/binary-search/
    { 
    	
    	for(int i=0;i<20;i++) {																
    		diskaccessesdmethod=0;															
    		int index=(int)amethod.getRandom20keys().toArray()[i];							
    		int dmethodcurrentkey=(int)amethod.getUsedkeys().toArray()[index];
    		
        int left = 0, right = 625 - 1; //625 is the number of nodes														
        int finalpositions=0;
        
        
        while (true) {													//infinite loop
            int middle = left + (right - left) / 2; 				
            
            
            try {
            	keyfiledmethod.seek(middle*128);						//seek the middle*128 position and place the cursor there (becase a page has 128 bytes)
    		} catch (IOException e1) {
    			e1.printStackTrace();
    		}
        		try {
        			keyfiledmethod.read(bufferb);						//read from keyfiledmethod file and store the read content in bufferb
        			diskaccessesdmethod=diskaccessesdmethod+1;			
        		} catch (IOException e) {
        			e.printStackTrace();
        		}
        		//finding the keys
        		byte[] possiblekey1d=Arrays.copyOfRange(bufferb, 0, 4);			//take the 16 keys from the bufferb by splitting the bufferb array
        		byte[] possiblekey2d=Arrays.copyOfRange(bufferb, 8, 12);
        		byte[] possiblekey3d=Arrays.copyOfRange(bufferb, 16, 20);
        		byte[] possiblekey4d=Arrays.copyOfRange(bufferb, 24, 28);
        		
        		byte[] possiblekey5d=Arrays.copyOfRange(bufferb, 32, 36);
        		byte[] possiblekey6d=Arrays.copyOfRange(bufferb, 40, 44);
        		byte[] possiblekey7d=Arrays.copyOfRange(bufferb, 48, 52);
        		byte[] possiblekey8d=Arrays.copyOfRange(bufferb, 56, 60);
        		
        		byte[] possiblekey9d=Arrays.copyOfRange(bufferb, 64, 68);
        		byte[] possiblekey10d=Arrays.copyOfRange(bufferb, 72, 76);
        		byte[] possiblekey11d=Arrays.copyOfRange(bufferb, 80, 84);
        		byte[] possiblekey12d=Arrays.copyOfRange(bufferb, 88, 92);
        		
        		byte[] possiblekey13d=Arrays.copyOfRange(bufferb, 96, 100);
        		byte[] possiblekey14d=Arrays.copyOfRange(bufferb, 104, 108);
        		byte[] possiblekey15d=Arrays.copyOfRange(bufferb, 112, 116);
        		byte[] possiblekey16d=Arrays.copyOfRange(bufferb, 120, 124);
        		
        		ByteBuffer wrapped1d = ByteBuffer.wrap(possiblekey1d); 				//turn the 4 byte array into an integer
        		int num1 = wrapped1d.getInt(); 
        		
        		ByteBuffer wrapped2d = ByteBuffer.wrap(possiblekey2d); 
        		int num2 = wrapped2d.getInt(); 
        		
        		ByteBuffer wrapped3d = ByteBuffer.wrap(possiblekey3d); 
        		int num3 = wrapped3d.getInt(); 
        		
        		ByteBuffer wrapped4d = ByteBuffer.wrap(possiblekey4d); 
        		int num4 = wrapped4d.getInt();       
            
        		
        		ByteBuffer wrapped5d = ByteBuffer.wrap(possiblekey5d); 
        		int num5 = wrapped5d.getInt(); 
        		
        		ByteBuffer wrapped6d = ByteBuffer.wrap(possiblekey6d); 
        		int num6 = wrapped6d.getInt(); 
        		
        		ByteBuffer wrapped7d = ByteBuffer.wrap(possiblekey7d); 
        		int num7 = wrapped7d.getInt(); 
        		
        		ByteBuffer wrapped8d = ByteBuffer.wrap(possiblekey8d); 
        		int num8 = wrapped8d.getInt();       
            
        		
        		ByteBuffer wrapped9d = ByteBuffer.wrap(possiblekey9d); 
        		int num9 = wrapped9d.getInt(); 
        		
        		ByteBuffer wrapped10d = ByteBuffer.wrap(possiblekey10d); 
        		int num10 = wrapped10d.getInt(); 
        		
        		ByteBuffer wrapped11d = ByteBuffer.wrap(possiblekey11d); 
        		int num11 = wrapped11d.getInt(); 
        		
        		ByteBuffer wrapped12d = ByteBuffer.wrap(possiblekey12d); 
        		int num12 = wrapped12d.getInt();     
        		
        		
           		ByteBuffer wrapped13d = ByteBuffer.wrap(possiblekey13d); 
        		int num13 = wrapped13d.getInt(); 
        		
        		ByteBuffer wrapped14d = ByteBuffer.wrap(possiblekey14d); 
        		int num14 = wrapped14d.getInt(); 
        		
        		ByteBuffer wrapped15d = ByteBuffer.wrap(possiblekey15d); 
        		int num15 = wrapped15d.getInt(); 
        		
        		ByteBuffer wrapped16d = ByteBuffer.wrap(possiblekey16d); 
        		int num16 = wrapped16d.getInt();     
        		
        		//finding the number of page
        		for(int f=1;f<17;f++) {
        			byte[] possiblepaged=Arrays.copyOfRange(bufferb, 4*f, 4*f+4);			//take all of the positions  the bufferb byte array and turn them into integers then store tem in an integer array
        			ByteBuffer wrappedpage = ByteBuffer.wrap(possiblekey13d); 
            		int dpage = wrappedpage.getInt(); 
            		
        			possiblepage[f]=dpage;
        		}
        		
        		
            if (num1 == dmethodcurrentkey) {			// Check if the key is present in this page by comparing it to every key the page contains
            	finalpositions=possiblepage[1];			//if the key is here save the position of the page of the key and break out of the loop
            	break;
            	
            }
            else if (num2 == dmethodcurrentkey) {	
            	finalpositions=possiblepage[2];
            	break;
            }
            else if (num3 == dmethodcurrentkey) {	
            	finalpositions=possiblepage[3];
            	break;

            }
            else if (num4 == dmethodcurrentkey) {	
            	finalpositions=possiblepage[4];
            	break;
            }
            else if (num5 == dmethodcurrentkey) {	
            	finalpositions=possiblepage[5];
            	break;
            }
            else if (num6 == dmethodcurrentkey) {	
            	finalpositions=possiblepage[6];
            	break;
            }
            else if (num7 == dmethodcurrentkey) {	
            	finalpositions=possiblepage[7];
            	break;
            }
            else if (num8 == dmethodcurrentkey) {	
            	finalpositions=possiblepage[8];
            	break;
            }
            else if (num9 == dmethodcurrentkey) {	
            	finalpositions=possiblepage[9];
            	break;
            }
            else if (num10 == dmethodcurrentkey) {	
            	finalpositions=possiblepage[10];
            	break;
            }
            else if (num11 == dmethodcurrentkey) {	
            	finalpositions=possiblepage[11];
            	break;
            }
            else if (num12== dmethodcurrentkey) {	
            	finalpositions=possiblepage[12];
            	break;
            }
            else if (num13== dmethodcurrentkey) {	
            	finalpositions=possiblepage[13];
            	break;
            }
            else if (num14== dmethodcurrentkey) {	
            	finalpositions=possiblepage[14];
            	break;
            }
            else if (num15 == dmethodcurrentkey) {	
            	finalpositions=possiblepage[15];
            	break;
            }
            else if (num16 == dmethodcurrentkey) {	
            	finalpositions=possiblepage[16];
            	break;
            }


            
            if (num16 < dmethodcurrentkey) {					 // If the key is greater than the biggest key of the page , ignore left half 	  
                left = middle + 1; 
            }
            else {
            	right = middle - 1; 								// else if it is smaller, ignore right half 
            }
            
            
            
            }//while's end
        
        
        
        try {																					
			amethod.getMyFile().seek(finalpositions*128);								//get the cursor of the MyFile of amethod to the finalpositions*128 and read and put the read contents in bufferb
	        amethod.getMyFile().read(bufferb);														
	        diskaccessesdmethod=diskaccessesdmethod+1;														
		} catch (IOException e) {																		
			e.printStackTrace();										
		}
        
		byte[] possiblekey1final=Arrays.copyOfRange(bufferb, 0, 4);						//take each of the 4 keys of the next page and turn them from byte arrays to integers
		ByteBuffer wrapped1dfinal = ByteBuffer.wrap(possiblekey1final); 
		int pkf1 = wrapped1dfinal.getInt();
        
		byte[] possiblekey2final=Arrays.copyOfRange(bufferb, 32, 36);
		ByteBuffer wrapped2dfinal = ByteBuffer.wrap(possiblekey2final); 
		int pkf2 = wrapped2dfinal.getInt();
        	
		byte[] possiblekey3final=Arrays.copyOfRange(bufferb, 64, 68);
		ByteBuffer wrapped3dfinal = ByteBuffer.wrap(possiblekey3final); 
		int pkf3 = wrapped3dfinal.getInt();
		
		byte[] possiblekey4final=Arrays.copyOfRange(bufferb, 96, 100);
		ByteBuffer wrapped4dfinal = ByteBuffer.wrap(possiblekey4final); 
		int pkf4 = wrapped4dfinal.getInt();
        
		if(pkf1==dmethodcurrentkey) {													//if the key of the page equals the found key then read the 32 bytes after the byte and store them in a byte array 
			byte[] datafinal=Arrays.copyOfRange(bufferb, 4, 36);
		}
		else if(dmethodcurrentkey==pkf2){
			byte[] datafinal=Arrays.copyOfRange(bufferb, 36, 64);
        }
		else if(dmethodcurrentkey==pkf3){
			byte[] datafinal=Arrays.copyOfRange(bufferb, 68, 96);
        }
		else if(dmethodcurrentkey==pkf4){
			byte[] datafinal=Arrays.copyOfRange(bufferb, 100, 128);
        }
        
		
		
        diskaccessesdmethodtotal.add(diskaccessesdmethod);									//add a new element into the vector
    	}
    	
    	
    	
    	
  
	      int Modmethod=0;
	      for(int z=0;z<20;z++) {																//add the total of the 20 diskaccessess and divide them by 20 to find the median
	    	  Modmethod=Modmethod+(int)diskaccessesdmethodtotal.toArray()[z];
	      }
	      Modmethod=Modmethod/20;
	      System.out.println("dmethod mesos oros="+Modmethod);									//turn totadiskaccesses from a vector to an array (to pick a specific element easier) and cast it as an int
    	


		    
}

	
    void closeall() {
  	  try {
  	  keyfile.close();
  	  keyfiledmethod.close();
    } catch (IOException e) {
			e.printStackTrace();
		}
	    }
	
	
}
