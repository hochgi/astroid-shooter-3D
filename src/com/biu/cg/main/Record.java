package com.biu.cg.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;

public class Record implements Serializable {
	int record;
	
	public Record(int newRec){
		this.record = newRec;
	}
	
	public int getRecord() {
		return record;
	}
	
	public static Record readFile(){
		Object res=null;
		File myFile=new File("record.rec");
		
		FileInputStream fOut;
		try {
			fOut = new FileInputStream(myFile);
			ObjectInput myOutWriter = new ObjectInputStream(fOut);
			res = myOutWriter.readObject();	
			myOutWriter.close();
			fOut.close();
		} catch (FileNotFoundException e) {
			//e.printStackTrace();
		} catch (StreamCorruptedException e) {
			//e.printStackTrace();
		} catch (IOException e) {
			//e.printStackTrace();
		} catch (ClassNotFoundException e) {
			//e.printStackTrace();
		}
		if(res==null)
			return new Record(0);
		return (Record)res;
	}
	
	public static void writeFile(Record rec){
		File myFile=new File("record.rec");
		try {
			myFile.createNewFile();
			FileOutputStream fOut = new FileOutputStream(myFile);
			ObjectOutput myOutWriter = new ObjectOutputStream(fOut);
			myOutWriter.writeObject(rec);
			myOutWriter.close();
			fOut.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
