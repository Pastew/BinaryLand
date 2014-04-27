package dataModel;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hashing {
	
	public static String hash(String password){ 
        MessageDigest md;
        StringBuffer hexString=new StringBuffer();
        
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(password.getBytes());
			 
	        byte byteData[] = md.digest();
	 
	        //convert the byte to hex format method 2
	        hexString = new StringBuffer();
	    	for (int i=0;i<byteData.length;i++) {
	    		String hex=Integer.toHexString(0xff & byteData[i]);
	   	     	if(hex.length()==1) hexString.append('0');
	   	     	hexString.append(hex);
	    	}
	    	
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    	System.out.println("Hex format : " + hexString.toString());
    	
    	return hexString.toString();
    }
}
