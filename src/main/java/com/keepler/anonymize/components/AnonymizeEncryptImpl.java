package com.keepler.anonymize.components;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sun.mail.util.BASE64DecoderStream;
import com.sun.mail.util.BASE64EncoderStream;

@Component
public class AnonymizeEncryptImpl implements EncryptComponent {

	@Value("${anonymizeKeepler.encrypt.key}")
	private String key;

	private Cipher cipher;

	@Override
	public String encript(String text) {
		String result = null;
		try {
			
			SecretKey secretKey = new SecretKeySpec(key.getBytes(), "AES");
			
			cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			
			byte[] utf8 = text.getBytes("UTF8");
			byte[] enc = cipher.doFinal(utf8);
			
			result = new String(BASE64EncoderStream.encode(enc));
			
		} catch (NoSuchAlgorithmException e) {
			System.out.println("No Such Algorithm:" + e.getMessage());

		} catch (NoSuchPaddingException e) {
			System.out.println("No Such Padding:" + e.getMessage());

		} catch (InvalidKeyException e) {
			System.out.println("Invalid Key:" + e.getMessage());

		}catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IllegalBlockSizeException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String decrypt(String text) {
		String result = null;
		try {
			
			SecretKey secretKey = new SecretKeySpec(key.getBytes(), "AES");
			
			cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			byte[] dec = BASE64DecoderStream.decode(text.getBytes());
			byte[] utf8 = cipher.doFinal(dec);
			
			
			result = new String(utf8, "UTF8");
			
		} catch (NoSuchAlgorithmException e) {
			System.out.println("No Such Algorithm:" + e.getMessage());

		} catch (NoSuchPaddingException e) {
			System.out.println("No Such Padding:" + e.getMessage());

		} catch (InvalidKeyException e) {
			System.out.println("Invalid Key:" + e.getMessage());

		}catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IllegalBlockSizeException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

}
