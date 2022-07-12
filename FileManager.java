package domeserg1;
import java.io.ByteArrayOutputStream;
import java.io.File;  		 // Import the File class
import java.io.IOException;  // Import the IOException class to handle errors
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;


public class FileManager {
	RandomAccessFile MyFile;
	

	byte[] filebuffer=new byte[128];

	int cursor;
	String filename;
	int totalpages;
	int numofreads;
	
	///////////////contructor////////
	public FileManager() {
		this.cursor=0;
		this.totalpages=0;
		this.numofreads=0;
	}
	
	
	private void filehandler() {															
		int saved=this.cursor;								//save the cursor so we can use it later
		this.cursor=0;
		Node nodename = new Node(0,filename.getBytes(StandardCharsets.US_ASCII));			//create a 4 new nodes
		Node cursor=new Node(1,saved);														
		Node totalpages=new Node(2,this.totalpages);
		Node readstot=new Node(3,this.numofreads);
		Page MyPage=new Page(new Node[] {nodename,cursor,totalpages,readstot});				
		try {
			MyFile.seek(0);												//get tohe cursor to position 0 of the file
			MyFile.write(MyPage.tobyteArray());							//write the a 128 byte array to MyFile (by using the tobytearry() method of the page class)
		}
		catch(IOException e ){
		    }
		this.cursor=saved;											//restore the cursor
	}
	
	
	int CreateFile(String filename) 					//from https://www.w3schools.com/java/java_files_create.asp
	 { 	
	
		try {								
			  this.filename=filename;
		      this.MyFile= new RandomAccessFile(filename,"rw");			//create a new randomaccefile with read write command
		      this.totalpages=1;										
		      this.cursor=0;											
		      filehandler();												//call refresh
		     return 1;													//return 1 since it's a success
		    }
			 catch (IOException e)
			{
		     e.printStackTrace();
		     
		    }
			return 0;													//if this point is reached file cretion failed so return 0;
	}
	
	
	int OpenFile(String filename) {
		
		
		      readblock(0);							//call readblock
		      Page first=new Page(filebuffer);		//create a new page
		      first.printdata();					//use the printdata method of page class
		      first.getNodes(); 					//use the getNodes method of page class
		      Node kk=first.getNodes()[2];			//use the third node of first page
		      System.out.println(kk.getKey());
		      ByteBuffer wrapped3 = ByteBuffer.wrap(kk.getData()); 		// make the byte array (using the getData method of node) into an integer
		      int num3 = wrapped3.getInt(); 							
		      System.out.println("selides:"+num3);
		      return num3;

	 
	}
	
	
	int readblock(int pageposition) {
		if (pageposition+1>totalpages) {
			System.out.println("page does not exist");
			return 0;
		}
		cursor=pageposition;								//cursor becomes the position requested
		System.out.println("cursor="+cursor);
		try {
			this.MyFile.seek(pageposition*128);				//get to the pageposition*128 (because a page is 128 bytes)
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
		try {
			this.MyFile.read(filebuffer);					//read 128 bytes from MyFile and save them in filebuffer
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
		
		
		return 1;
	}
	
	
	int ReadNextBlock() {
		readblock(cursor+1);
		return 1;
	}
	
	
	
	int WriteBlock(int pos) {
		try {
			this.MyFile.seek(pos*128);					//get to the pos*128 (because a page is 128 bytes)
			this.MyFile.write(this.filebuffer);			//write the 128 bytes of filebuffer in MyFile
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
		
		
	}
	
	
	int WriteNextBlock() {
		System.out.println("cursor write next="+cursor);
		if (this.cursor +1==totalpages) {					//if cursor+1 is bigger than the total pages then we are in the last page and call upon AppendBlock()
			AppendBlock();
		}else {
			WriteBlock(cursor+1);							//else call WriteBlock since the next page exists
		}
		return 1;
	}
	
	
	int AppendBlock() {
		try {
			this.MyFile.seek(MyFile.length());				//get to the the end of the file
			this.totalpages=totalpages+1;					
			this.cursor=totalpages-1;					
			this.MyFile.write(filebuffer);				//write the 128 bytes of filebuffer in MyFile
			filehandler();									//call refresh
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	
	int DeleteBlock(int pageposition) {
	 	int saved=this.cursor;
		readblock(totalpages-1);							//call readblock 	
		WriteBlock(pageposition-1);							//call writeblock
		this.totalpages=this.totalpages-1;
		this.cursor=this.totalpages-1;
		filehandler();											//call refresh
		try {
			this.MyFile.setLength(totalpages*128);			//make the MyFile smaller by deleting the last page
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
		
		this.cursor=saved;
		return 1;
	}
	
	
	int CloseFile(String filename) {
		WriteBlock(0);							//call writeblock which writes the filebuffer
		try {
			this.MyFile.close();				//close the file
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

}

