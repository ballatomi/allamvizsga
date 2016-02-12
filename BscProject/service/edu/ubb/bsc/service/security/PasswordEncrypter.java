package edu.ubb.bsc.service.security;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PasswordEncrypter {

	private static final Logger log = LoggerFactory.getLogger(PasswordEncrypter.class);

	public static String generateHashedPassword(final String password) {
		String hashedPassword = "";
		byte[] initialBytes;
		try {
			initialBytes = (password).getBytes("ISO-8859-1");
			final MessageDigest algorithm = MessageDigest.getInstance("SHA");

			algorithm.reset();
			algorithm.update(initialBytes);

			final byte[] hashedBytes = algorithm.digest();
			hashedPassword = new String(hashedBytes);
		} catch (final UnsupportedEncodingException e) {
			log.error("Password encryption failed: unsupported encoding", e);
		} catch (final NoSuchAlgorithmException nsae) {
			log.error("Password encryption failed: hashing algorithm not supported", nsae);
		}
		return hashedPassword;
	}

	public static void main(String[] args) {
		String pwd = PasswordEncrypter.generateHashedPassword("root");
		System.out.println(pwd);

	}

}
