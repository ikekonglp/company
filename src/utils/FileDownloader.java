package utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class FileDownloader {

	public static void dlFile(List<String> srcs, String host, String user,
			String psw) throws Exception {
		Session session = null;
		Channel channel = null;
		JSch jsch = new JSch();

		session = jsch.getSession(user, host);

		if (session == null) {
			throw new Exception("session is null");
		}

		session.setPassword(psw);
		session.setConfig("StrictHostKeyChecking", "no");
		session.connect(30000);
		int downloadedNum = 0;
		try {
			channel = (Channel) session.openChannel("sftp");
			channel.connect(1000);
			ChannelSftp sftp = (ChannelSftp) channel;

			sftp.cd("/cab1/lingpenk/sloan_nlp/");

			// Vector v = sftp.ls("*");
			// for (int i = 0; i < v.size(); i++) {
			// System.out.println(v.get(i));
			// }
			
			for (String fn : srcs) {
				downloadedNum++;
				if ((new File(
						"download/" + fn.substring(fn.lastIndexOf("/")+1))).exists()) {
					System.out.println("Skip: "+fn);
					continue;}
				System.out.println("Downloading: "+ fn);
				OutputStream outstream = new FileOutputStream(new File(
						"download/" + fn.substring(fn.lastIndexOf("/")+1)));
				InputStream instream = sftp.get(fn);
				if(instream==null){
					System.err.println("Error in downloading.");
				}
				
				
				byte b[] = new byte[1024];
				int n;
				while ((n = instream.read(b)) != -1) {
					outstream.write(b, 0, n);
				}
				outstream.flush();
				outstream.close();
				instream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.disconnect();
			channel.disconnect();
		}
		System.err.println("Downloaded: " + downloadedNum);
	}

	public static void dlFile(List<String> srcs) throws Exception {
		dlFile(srcs, "cab.ark.cs.cmu.edu", "lingpenk", "!Leiyun881023");
	}

	public static void main(String[] args) {
		try {
			dlFile(null, "cab.ark.cs.cmu.edu", "lingpenk", "!Leiyun881023");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
