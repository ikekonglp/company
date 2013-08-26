package utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CommandRunner {

	public static void runCommand(String cmd){
		Runtime run = Runtime.getRuntime();
		StringBuffer sb = new StringBuffer();
		// http://stackoverflow.com/questions/7200307/execute-unix-system-command-from-java-problem
		String[] cmds = new String[]{"/bin/bash", "-c", cmd};
        try {  
            Process p = run.exec(cmds);
//            BufferedInputStream in = new BufferedInputStream(p.getInputStream());  
//            BufferedReader inBr = new BufferedReader(new InputStreamReader(in)); 
//             
//            String lineStr;  
//            while ((lineStr = inBr.readLine()) != null)
//            	System.out.println(lineStr);
//            	sb.append(lineStr+"\n");
//            	
//            if (p.waitFor() != 0) {  
//                if (p.exitValue() == 1) 
//                    System.err.println("Error in excuting the command");  
//            }  
//            inBr.close();  
//            in.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
       // return sb.toString();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		  //windows  
//      String cmd = "F:\\apache-tomcat-6.0.20.exe";  
//      String cmd = "D:\\Program Files\\Microsoft Office\\OFFICE11\\WINWORD.EXE F:\\test.doc";  
//      String cmd = "cmd.exe /c start F:\\test.doc";  
        String cmd = "ls";  
          
     
	}

}
