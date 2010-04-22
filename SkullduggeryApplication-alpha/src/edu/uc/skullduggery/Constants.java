package edu.uc.skullduggery;

import java.io.IOException;
import java.net.InetAddress;
import java.util.StringTokenizer;

public class Constants {
	public static final String USERDBNAME = "SkullStorage";
	public static final String USERINFOTABLE = "UserInfo";
	public static final String USERINFOTABLESCHEMA =
		"CREATE TABLE IF NOT EXISTS " + USERINFOTABLE + "(" +
		"UserID INTEGER PRIMARY KEY ASC AUTOINCREMENT," +
		"Number CHAR(10), " +
		"Salt BLOB," +
		"PubKeyHash BLOB);";

	public static final String KEYDBNAME = "SkeletonKeys";
    public static final String KEYTABLE = "CryptoKeys";
    public static final String KEYTABLESCHEMA = 
            "CREATE TABLE IF NOT EXISTS " + KEYTABLE + "(" +
            "KeyID INTEGER PRIMARY KEY ASC AUTOINCREMENT," +
            "Modulus BLOB NOT NULL," +
            "PublicExponent BLOB NOT NULL," +
            "PrivateExponent BLOB NOT NULL);";
    
	//public static final String KEYALIAS = "SkullKeys";	
	
	public static final String HASHALGORITHM = "HmacSHA1";
	public static final int HASHKEYSIZE = 128;
	public static final String SYMMALGORITHM = "AES";
	public static final int SYMKEYSIZE = 128;
	public static final String ASYMALGORITHM = "RSA";
	public static final int ASYMKEYSIZE = 4096;
	public static final byte[] MAGICBYTES = "SKUL".getBytes();
	
	
	public static int ipBytesToInt(byte[] ipBytes){
		int ip32 = 0;
		for (int x = 0; x < ipBytes.length; ++x){
			ip32 |= (ipBytes[x] & 0xff) << ((ipBytes.length - 1 - x) * 8); 
		}
		return ip32;
	}
	public static byte[] ipIntToBytes(int ip32){
		byte[] ipBytes = new byte[4];
		for (int x = 0; x < ipBytes.length; ++x){
			ipBytes[x] = (byte)
			((ip32 >> ((ipBytes.length - 1 - x) * 8)) & 0xff);
		}
		return ipBytes;
	}
	public static byte[] ipStringToBytes(String ipString){
		try{
			return InetAddress.getByName(ipString).getAddress();
		}catch (IOException ioe){
			StringTokenizer tokens = new StringTokenizer(ipString);
			byte[] ipBytes = new byte[4];
			for (int x = 0; x < ipBytes.length; ++x){
				ipBytes[x] = (byte) Integer.parseInt(tokens.nextToken());
			}
			return ipBytes;
		}
	}
	public static String ipBytesToString(byte[] ipBytes){
		try{
			return InetAddress.getByAddress(ipBytes).getHostAddress();
		}catch (IOException ioe){
			return ((int) ipBytes[0]) + "." + ((int) ipBytes[1]) +
			"." + ((int) ipBytes[2]) + "." + ((int) ipBytes[3]);
		}
	}
	
}
