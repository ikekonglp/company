package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

import edu.stanford.nlp.pipeline.Annotation;

public class SerializeTool {

	public static void writeAnnotation(File outputf, Annotation an){
		FileOutputStream f = null;
		ObjectOutputStream s = null;
		try {
			f = new FileOutputStream(outputf);
			s = new ObjectOutputStream(f);
			s.writeObject(an);
			s.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				f.close();
				s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static Annotation readAnnotation(File file){
		
		FileInputStream in = null;
		ObjectInputStream os = null;
		try {
			in = new FileInputStream(file);
			os = new ObjectInputStream(in);
			Annotation an = (Annotation)os.readObject();
			return an;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				in.close();
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		return null;
	}
	
	public static void main(String[] args) {
		FileOutputStream f = null;
		ObjectOutputStream s = null;
		try {
			f = new FileOutputStream("tmp");
			s = new ObjectOutputStream(f);
			s.writeInt(2);
			s.writeObject("Today");
			s.writeObject(new Date());
			s.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				f.close();
				s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		FileInputStream in = null;
		ObjectInputStream os = null;
		try {
			in = new FileInputStream("tmp");
			os = new ObjectInputStream(in);
			int i = os.readInt();
			System.out.println(i);
			String today = (String)os.readObject();
			Date date = (Date)os.readObject();

			System.out.println(today);
			System.out.println(date.toString());
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			try {
				in.close();
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
}
