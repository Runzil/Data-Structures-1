package domeserg1;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;



public class Mainclass {

	public static void main(String[] args) throws IOException {

		System.out.println("INITIATING A METHOD");
		String filename="method1file";												
		String cmethodname="method3file";			
		amethod atry=new amethod(filename,cmethodname);			//create an instance of amethod
		atry.generatekey();										//call the generatekey , generate file and findkey methods of the instance atry
		atry.generatefile();
		atry.findkey();
		System.out.println("INITIATING B METHOD");
		
		String filename2="method2file";											
		String dmethodname="method4file";			
		
		bmethod bmet=new bmethod(filename2,atry,dmethodname);		//create an instance of bmethod
		
		bmet.createkeyfile();									//call the createkeyfile , searchkey methods of the instance bmet
		bmet.searchkey();
		System.out.println("INITIATING C METHOD");				//call the gammaSort , binarySearch methods of the instance atry
		atry.gammaSort();										
		atry.binarySearch();
		System.out.println("INITIATING D METHOD");
		bmet.dmethod();											//call the dmethod , binarySearch methods of the instance bmet
		bmet.binarySearch();
		atry.closeall();
		bmet.closeall();
		
		
	}

}
