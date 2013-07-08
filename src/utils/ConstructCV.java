package utils;

import java.io.File;

/**
 * This is a class to split the CV set 
 * Lingpeng Kong
 *
 */
public class ConstructCV {
	public static void buildCV(File ff, File rf, int fold, int numberOfInstance, File wdir){
		int eachSetNum = (numberOfInstance / fold);
		System.out.println("Each Set Size: " + eachSetNum);
		int testSetNum = numberOfInstance - (numberOfInstance / fold) * (fold - 1);
		System.out.println("Test Set Size: " + testSetNum);
		for(int i = 0; i < fold; i++){
			int testStartPoint = i * eachSetNum;
			int testEndPoint = testStartPoint + testSetNum;
			LineReader lfr = new LineReader(ff);
			LineReader lrr = new LineReader(rf);
			LineWriter lwtrf = new LineWriter(wdir.getAbsolutePath()+"/"+ i +"_train_feature");
			LineWriter lwtef = new LineWriter(wdir.getAbsolutePath()+"/"+ i +"_test_feature");
			LineWriter lwtrr = new LineWriter(wdir.getAbsolutePath()+"/"+ i +"_train_rep");
			LineWriter lwter = new LineWriter(wdir.getAbsolutePath()+"/"+ i +"_test_rep");
			for(int j = 0; j < numberOfInstance; j++){
				String fline = lfr.readNextLine().trim();
				String rline = lrr.readNextLine().trim();
				if(j>= testStartPoint && j < testEndPoint){
					lwtef.writeln(fline);
					lwter.writeln(rline);
				}else{
					lwtrf.writeln(fline);
					lwtrr.writeln(rline);
				}
			}
			lwtrf.closeAll();
			lwtef.closeAll();
			lwtrr.closeAll();
			lwter.closeAll();
			lfr.closeAll();
			lrr.closeAll();
			
		}
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		buildCV(new File("feature"), new File("rep"), 10, 231, new File("CReg"));
	}

}
