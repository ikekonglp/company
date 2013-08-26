package annoproc;

import java.io.File;
import java.util.ArrayList;

import utils.FileDownloader;

public class DownloadRelated {
	public static void download(){
		File[] fs = (new File("Annotate")).listFiles();
		ArrayList<String> dns = new ArrayList<String>();
		for(File f : fs){
			String dn =getSourceName(f.getName());
			dns.add(dn);
		}
		try {
			FileDownloader.dlFile(dns);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String getSourceName(String annoFileName){
		// 1000278-8-K-20021007.press.toked.nlp.readable.line11.txt
		String fns = annoFileName.trim().substring(0, annoFileName.trim().indexOf('.'));
		String date = fns.substring(fns.lastIndexOf('-') + 1, fns.length());
		String sf = date.substring(0,4)+"/"+ date.substring(4,6)+"/"+annoFileName.substring(0,annoFileName.indexOf(".nlp")+4)+".obj";
		return sf;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(getSourceName("1000278-8-K-20021007.press.toked.nlp.readable.line11.txt"));
		download();
	}

}
