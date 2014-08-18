package ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilters;
import org.apache.commons.net.ftp.FTPReply;

public class FTPManager {
	
	private FTPClient ftp = null;
	
	/*
     * host - is2012.vxm.pl
     */
	
	public FTPManager(String host, String user, String pwd) throws Exception {
        ftp = new FTPClient();
        ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
        int reply;
        ftp.connect(host);
        reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            throw new Exception("Exception in connecting to FTP Server");
        }
        ftp.login(user, pwd);
        ftp.setFileType(FTP.BINARY_FILE_TYPE);
        ftp.enterLocalPassiveMode();
    }

	public void downloadFile(String remoteFilePath, String localFilePath) {
        try (FileOutputStream fos = new FileOutputStream(localFilePath)) {
            this.ftp.retrieveFile(remoteFilePath, fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	/*
     * localFileFullName- C:\\tmp\\002.png
     * 					  .\\levels\\level_001.xml
     * fileName- image.png - nazwa pliku docelowego
     * hostDir - /binaryLand/, istnieje tutaj: is2012.vxm.pl/binaryLand/
     */
    public void uploadFile(String localFileFullName, String hostFileName, String hostDir)
            throws Exception {
        try(InputStream input = new FileInputStream(new File(localFileFullName))){
        this.ftp.storeFile(hostDir + hostFileName, input);
        }
    }
	
	public boolean isOK(){
		if(ftp.isConnected() && ftp.isAvailable())
			return true;
		else
			return false;
	}
	
	public void disconnect() {
        if (this.ftp.isConnected()) {
            try {
                this.ftp.logout();
                this.ftp.disconnect();
            } catch (IOException f) {
            }
        }
    }

	public ArrayList<String> getListOfFiles(){
		FTPFile[] listOfFiles = null;
		try {
			listOfFiles = ftp.listFiles("binaryLand/levels", FTPFileFilters.NON_NULL);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ArrayList<String> list = new ArrayList<String>();
		for( FTPFile x : listOfFiles){
			String tmp = x.toString();
			list.add(tmp.substring(tmp.lastIndexOf(" ")+1));
		}			
		
		return list;
	}
}
