package utils;

import java.io.File;

/**
 * This is a simple wrapper for CReg in Java... Don't know if anyone else has
 * done this before... Whatever... I will build my own...
 * 
 * @author Lingpeng Kong
 * 
 */

/*
 * README FILE FOR CREG (for a reference):
  Logistic regression example (training only):

	./dist/bin/creg -x test_data/iris.trainfeat -y test_data/iris.trainresp --l1 1.0 > weights.txt

  * To load initial values for weights from a file (warm start), use `-w FILENAME`.

Logistic regression example (training and testing):

	./dist/bin/creg -x test_data/iris.trainfeat -y test_data/iris.trainresp --l1 1.0 \
	     --tx test_data/iris.testfeat --ty test_data/iris.testresp > weights.txt

Logistic regression example (training and prediction):

	./dist/bin/creg -x test_data/iris.trainfeat -y test_data/iris.trainresp --l1 1.0 --tx test_data/iris.testfeat

  * By default, the test set predictions and learned weights are written to stdout.
  * If `-D` is specified, the full posterior distribution over predicted labels will be written.
  * To write weights to a file instead of stdout, specify `--z FILENAME`. To suppress outputting of weights altogether, supply the `-W` flag.
 */

/*
 *  cmd = "/Users/konglingpeng/Documents/GFL/creg-master/dist/bin/creg -x /Users/konglingpeng/Documents/Company_Relation_Extraction/workspace/Company_Relation_Extraction/CReg/" + str(i) + "_train_feature -y /Users/konglingpeng/Documents/Company_Relation_Extraction/workspace/Company_Relation_Extraction/CReg/" + str(i) + "_train_rep --l1 1.0 --tx /Users/konglingpeng/Documents/Company_Relation_Extraction/workspace/Company_Relation_Extraction/CReg/" + str(i) + "_test_feature --ty /Users/konglingpeng/Documents/Company_Relation_Extraction/workspace/Company_Relation_Extraction/CReg/" + str(i) + "_test_rep  > weights.txt"
       
 */
	// to test without training
	// ./dist/bin/creg -w weights.txt -W --tx test_data/iris.testfeat 

public class CRegWrapper {
	public static String CREG_COM = "/Users/konglingpeng/Documents/GFL/creg-master/dist/bin/creg";
	
	public static void train(File x, File y, File w, String arg)
	{
		String train_command =  CREG_COM +" -x " + x.getAbsolutePath() + " -y "+ y.getAbsolutePath() + " "+ arg + " --z " + w.getAbsolutePath();
		System.out.println("Start Training, the command line is: "+ train_command);
		CommandRunner.runCommand(train_command);
	}
	
	public static void predict(File w, File tx, File predict){
		// ./dist/bin/creg -w weights.txt -W  --tx test_data/iris.testfeat
		String predict_command = CREG_COM + " -w " + w.getAbsolutePath() + " -D -W --tx " + tx.getAbsolutePath() + " > " + predict.getAbsolutePath();
		System.out.println("Start Training, the command line is: "+ predict_command);
		CommandRunner.runCommand(predict_command);
	}
	
	
	public static void main(String[] args) {
		//train(new File("/Users/konglingpeng/Documents/GFL/creg-master/test_data/iris.trainfeat"), new File("/Users/konglingpeng/Documents/GFL/creg-master/test_data/iris.trainresp"), new File("/Users/konglingpeng/Documents/GFL/creg-master/test_output"), "--l1 1.0");
		predict(new File("/Users/konglingpeng/Documents/GFL/creg-master/test_output"), new File("/Users/konglingpeng/Documents/GFL/creg-master/test_data/iris.testfeat"),  new File("/Users/konglingpeng/Documents/GFL/creg-master/predict_output"));
	}

}
