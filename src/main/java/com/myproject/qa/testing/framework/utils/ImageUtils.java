package com.myproject.qa.testing.framework.utils;

import com.google.common.io.BaseEncoding;

public interface ImageUtils {

	public static String decodeBase64(String string) throws Exception {
		return new String(BaseEncoding.base64().decode(string));
	}
	
}
