package com.test;



import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.axis.types.HexBinary;
import org.postgresql.util.Base64;

public class TEST {

	
	public static void main(String[] args) {
		
		String plainText="ankit.rana@nvish.com";
		String returnValue="ankit.rana@nvish.com";
		String key = "919BF4212BAF5AF2C26534F5870170B1DAADDD220E84C947";
		String initializationVector = "67FB9E4C95EAD32F";
			if(returnValue != null && !returnValue.trim().equalsIgnoreCase("")){
				
					try{

						byte[] plaintext = plainText.getBytes();
						HexBinary hb = new HexBinary(key);

						byte[] tdesKeyData = hb.getBytes();
						HexBinary hb1 = new HexBinary(initializationVector);
						byte[] myIV = hb1.getBytes();

						Cipher c3des = Cipher.getInstance("DESede/CBC/PKCS5Padding");
						SecretKeySpec myKey = new SecretKeySpec(tdesKeyData, "DESede");
						IvParameterSpec ivspec = new IvParameterSpec(myIV);

						c3des.init(Cipher.ENCRYPT_MODE, myKey, ivspec);
						byte[] cipherText = c3des.doFinal(plaintext);
						returnValue=Base64.encodeBytes(cipherText,Base64.DONT_BREAK_LINES);

						System.out.println("1111111 : "+ returnValue);
						
						
					}catch(Exception e){
						

						
					}
				}
			
		
		
	}
	
}
