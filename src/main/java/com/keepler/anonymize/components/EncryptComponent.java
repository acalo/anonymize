package com.keepler.anonymize.components;

import org.springframework.stereotype.Component;


@Component
public interface EncryptComponent {

	public String encript(String text);
	public String decrypt(String text);
	
}
