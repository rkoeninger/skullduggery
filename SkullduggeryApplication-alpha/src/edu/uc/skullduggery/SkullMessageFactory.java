package edu.uc.skullduggery;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class SkullMessageFactory {
	
	private SecretKey _hashKey;
	private Mac _mac;
	
	public class TrickeryException extends Exception
	{
		private static final long serialVersionUID = -5951444176541432600L;
	}
	
	/* Creates a new instance of SkullMessageFactory with a new hash key */
	/* Note: Not a singleton object. We don't want that; multiple calls should have different hash keys */
	/* A single call should only have one hash key though. */
	/* Returns null upon failure */
	public static SkullMessageFactory getInstance()
	{
		try{
			KeyGenerator keygen;
			SecretKey hashKey;
		
			keygen = KeyGenerator.getInstance(Constants.HASHALGORITHM);
			keygen.init(Constants.HASHKEYSIZE);
			hashKey = keygen.generateKey();
			return new SkullMessageFactory(hashKey);
		}
		catch (NoSuchAlgorithmException e)
		{	
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public SkullMessageFactory(SecretKey hashKey) throws NoSuchAlgorithmException, InvalidKeyException
	{
		_hashKey = new SecretKeySpec(hashKey.getEncoded(), Constants.HASHALGORITHM);
		_mac = Mac.getInstance(Constants.HASHALGORITHM);
		_mac.init(_hashKey);
	}
	
	public SkullMessage createMessage(byte[] data, SkullMessage.MessageType type)
	{
		return new SkullMessage(type, data);
	}
	
	public int getHashSize()
	{
		return _mac.getMacLength();
	}
}
