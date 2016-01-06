/**
 * 
 */
package com.enjoy.love.common.encrypt.ucf;

import java.io.PrintStream;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author lenovo
 *
 */
public class AESCoder {
	private static final String KEY_ALGORITHM = "AES";
	  private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

	  public static String createKey()
	  {
	    byte[] key = initSecretKey();
	    return Hex.encodeHexString(key);
	  }

	  private static byte[] initSecretKey()
	  {
	    KeyGenerator kg = null;
	    try {
	      kg = KeyGenerator.getInstance("AES");
	    } catch (NoSuchAlgorithmException e) {
	      e.printStackTrace();
	      return new byte[0];
	    }

	    kg.init(128);

	    SecretKey secretKey = kg.generateKey();
	    return secretKey.getEncoded();
	  }

	  public static Key toKey(byte[] key)
	  {
	    return new SecretKeySpec(key, "AES");
	  }

	  public static byte[] encrypt(byte[] data, Key key)
	    throws GeneralSecurityException
	  {
	    return encrypt(data, key, "AES/ECB/PKCS5Padding");
	  }

	  public static byte[] encrypt(byte[] data, byte[] key)
	    throws GeneralSecurityException
	  {
	    return encrypt(data, key, "AES/ECB/PKCS5Padding");
	  }

	  public static byte[] encrypt(byte[] data, byte[] key, String cipherAlgorithm)
	    throws GeneralSecurityException
	  {
	    Key k = toKey(key);
	    return encrypt(data, k, cipherAlgorithm);
	  }

	  public static byte[] encrypt(byte[] data, Key key, String cipherAlgorithm)
	    throws GeneralSecurityException
	  {
	    Cipher cipher = Cipher.getInstance(cipherAlgorithm);

	    cipher.init(1, key);

	    return cipher.doFinal(data);
	  }

	  public static byte[] decrypt(byte[] data, byte[] key)
	    throws GeneralSecurityException
	  {
	    return decrypt(data, key, "AES/ECB/PKCS5Padding");
	  }

	  public static byte[] decrypt(byte[] data, Key key)
	    throws Exception
	  {
	    return decrypt(data, key, "AES/ECB/PKCS5Padding");
	  }

	  public static byte[] decrypt(byte[] data, byte[] key, String cipherAlgorithm)
	    throws GeneralSecurityException
	  {
	    Key k = toKey(key);
	    return decrypt(data, k, cipherAlgorithm);
	  }

	  public static byte[] decrypt(byte[] data, Key key, String cipherAlgorithm)
	    throws GeneralSecurityException
	  {
	    Cipher cipher = Cipher.getInstance(cipherAlgorithm);

	    cipher.init(2, key);

	    return cipher.doFinal(data);
	  }

	  private static String showByteArray(byte[] data) {
	    if (null == data) {
	      return null;
	    }
	    StringBuilder sb = new StringBuilder("{");
	    for (byte b : data) {
	      sb.append(b).append(",");
	    }
	    sb.deleteCharAt(sb.length() - 1);
	    sb.append("}");
	    return sb.toString();
	  }

	  public static void main(String[] args) throws Exception {
	    byte[] key = initSecretKey();
	    System.out.println(new StringBuilder().append("key：").append(showByteArray(key)).toString());
	    System.out.println(new StringBuilder().append("key：").append(Hex.encodeHexString(key)).toString());
	    System.out.println(new StringBuilder().append("key：").append(showByteArray(Hex.decodeHex(Hex.encodeHexString(key).toCharArray()))).toString());

	    key = Hex.decodeHex(Hex.encodeHexString(key).toCharArray());
	    Key k = toKey(key);

	    String data = "AES数据";
	    System.out.println(new StringBuilder().append("加密前数据: string:").append(data).toString());
	    System.out.println(new StringBuilder().append("加密前数据: byte[]:").append(showByteArray(data.getBytes())).toString());
	    System.out.println();
	    byte[] encryptData = encrypt(data.getBytes(), k);
	    System.out.println(new StringBuilder().append("加密后数据: byte[]:").append(showByteArray(encryptData)).toString());
	    System.out.println(new StringBuilder().append("加密后数据: hexStr:").append(Hex.encodeHexString(encryptData)).toString());
	    System.out.println();
	    byte[] decryptData = decrypt(encryptData, k);
	    System.out.println(new StringBuilder().append("解密后数据: byte[]:").append(showByteArray(decryptData)).toString());
	    System.out.println(new StringBuilder().append("解密后数据: string:").append(new String(decryptData)).toString());

	    System.out.println();
	  }
}
