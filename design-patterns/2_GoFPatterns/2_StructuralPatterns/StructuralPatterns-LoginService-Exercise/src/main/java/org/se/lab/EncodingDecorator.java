package org.se.lab;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class EncodingDecorator // package private
{

	// TODO
	
	/*
	 * Utility methods
	 * (in the case of an exam, these methods would be provided for you!
	 */
	
	private String toMD5(String message)
	{
		byte[] bytes;
		try
		{
			MessageDigest algorithm = MessageDigest.getInstance("MD5");		
			algorithm.update(message.getBytes("UTF-8"));
			bytes = algorithm.digest();
			String hash = convertToHexString(bytes);
			return hash;
		}
		catch (NoSuchAlgorithmException e)
		{
			throw new IllegalStateException(e);
		}
		catch (UnsupportedEncodingException e)
		{
			throw new IllegalStateException(e);
		}
	}
	
	private String convertToHexString(byte[] bytes)
	{
		StringBuilder hex = new StringBuilder();
		for(byte b : bytes)
		{
			int i = (b & 0xff); 
			hex.append(String.format("%02x", i));
		}
		return hex.toString();
	}
}
