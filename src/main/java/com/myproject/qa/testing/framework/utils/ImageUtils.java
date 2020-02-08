package com.myproject.qa.testing.framework.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.google.common.io.BaseEncoding;

public interface ImageUtils {

	public static String decodeBase64(String string) throws Exception {
		return new String(BaseEncoding.base64().decode(string));
	}

	public static byte[] bufferedImageToByteArray(BufferedImage originalImage) throws Exception{

		try{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write( originalImage, "png", baos );
			baos.flush();
			byte[] imageInByte = baos.toByteArray();
			baos.close();
			return imageInByte;
		}catch(IOException e){
			throw new Exception(e);
		}		
	}
}
