package uniandes.unacloud.utils.security;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Responsible of create hashes and random strings
 * @author CesarF
 * based on //http://howtodoinjava.com/core-java/io/how-to-generate-sha-or-md5-file-checksum-hash-in-java/
 */
public class HashGenerator {
		
	/**
	 * Represents SHA-256 algorithm
	 */
	private static final String SHA_256 = "SHA-256";
	
	/**
	 * Represents SHA-1 algorithm
	 */
	private static final String SHA_1 = "SHA-1";
	
	/**
	 * Represents MD5 algorithm
	 */
	private static final String MD5 = "MD5";
	
	/**
	 * Secure random instance
	 */
	private static SecureRandom random = new SecureRandom();
	
	/**
	 * Char set to generate random keys
	 */
	private static final String charset = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	/**
	 * Generates checksum from a file using MD5 algorithm 
	 * @param file to generate checksum
	 * @return checksum
	 * @throws NoSuchAlgorithmException 
	 * @throws FileNotFoundException 
	 */	
	public static String generateChecksumMD5(File file) throws Exception {		
        MessageDigest digest = MessageDigest.getInstance(MD5);
        return generateCheckSum(digest, file);
	}
	
	/**
	 * Generates checksum from a file using SHA1 algorithm 
	 * @param file to generate checksum
	 * @return checksum
	 * @throws Exception
	 */
	public static String generateChecksumSHA1(File file) throws Exception {		
        MessageDigest digest = MessageDigest.getInstance(SHA_1);
        return generateCheckSum(digest, file);
	}
	
	/**
	 * Generates checksum from a file using a message digest based in a particular algorithm
	 * @param digest to process file
	 * @param file to generate checksum
	 * @return checksum
	 * @throws Exception
	 */
	public static String generateCheckSum(MessageDigest digest, File file) throws Exception {
		//Get file input stream for reading the file content
        FileInputStream fis = new FileInputStream(file);
         
        //Create byte array to read data in chunks
        byte[] byteArray = new byte[1024];
        int bytesCount = 0; 
          
        //Read file data and update in message digest
        while ((bytesCount = fis.read(byteArray)) != -1) {
            digest.update(byteArray, 0, bytesCount);
        };
         
        //close the stream; We don't need it now.
        fis.close();
         
        //Get the hash's bytes
        byte[] bytes = digest.digest();
         
        //This bytes[] has bytes in decimal format;
        //Convert it to hexadecimal format
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length ; i++)
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
              
        //return complete hash
        return sb.toString();
	}
	
	/**
	 * Method to create a hash based in a text using sha256
	 * @param text
	 * @return hash String
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static String hashSha256(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md = MessageDigest.getInstance(SHA_256);
		md.update(text.getBytes("UTF-8"));
		return Base64.getEncoder().encodeToString(md.digest());		
	}
	
	/**
	 * Using to create a randomString based in a length sent by user
	 * @param ln
	 * @return random string number
	 */
	public static String randomStringNumber(int ln) {
		 return new BigInteger(ln, random).toString(32);
	}
	

	/**
	 * Using to create a randomString based in a length sent by user
	 * @param ln
	 * @return random string based in char set
	 * https://stackoverflow.com/questions/39222044/generate-random-string-in-java
	 */
	public static String randomString(int ln) {		
		SecureRandom random = new SecureRandom();
        if (ln <= 0)
            throw new IllegalArgumentException("String length must be a positive integer");        

        StringBuilder sb = new StringBuilder(ln);
        for (int i = 0; i < ln; i++)
            sb.append(charset.charAt(random.nextInt(charset.length())));      

        return sb.toString();
	}
	
}
