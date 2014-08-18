package database;

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
	 
	        hexString = new StringBuffer();
	    	for (int i=0;i<byteData.length;i++) {
	    		String hex=Integer.toHexString(0xff & byteData[i]);
	   	     	if(hex.length()==1) hexString.append('0');
	   	     	hexString.append(hex);
	    	}
	    	
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        
    	System.out.println("Hex format : " + hexString.toString());
    	
    	return hexString.toString();
    }
}
