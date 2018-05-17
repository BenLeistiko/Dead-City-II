package gamePlay;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class FileIO {

	public static final String fileSeparator = System.getProperty("file.separator");
	public static final String lineSeparator = System.getProperty("line.separator");






	public static ArrayList<String> readFile(String filename){

		Scanner scan = null;

		try {
			ArrayList<String> output = new ArrayList<String>();
			FileReader reader = new FileReader(filename);

			scan = new Scanner(reader);

			while(scan.hasNextLine()) {
				output.add(scan.nextLine());	
			}

			return output;	

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} finally {
			if(scan != null) {
				scan.close();
			}
		}

		return null;

	}
	
	public static String readURL(URL url){

		Scanner scan = null;

		try {
			String output = "";
			
			scan = new Scanner(url.openStream());

			while(scan.hasNextLine()) {
				output+=scan.nextLine();	
			}

			return output;	

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		} finally {
			if(scan != null) {
				scan.close();
			}
		}

		return null;

	}
	
	


	public static void writeFile(String filename, ArrayList<String> fileData){

		FileWriter writer = null;

		try {

			writer = new FileWriter(filename);

			for(String s: fileData) {
				writer.write(s);
				writer.write(lineSeparator);
			}

		}  catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			try {
				if(writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}



}





