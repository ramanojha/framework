package com.myproject.qa.testing.framework.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Base64;

import javax.imageio.ImageIO;

import com.google.common.io.BaseEncoding;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.myproject.qa.testing.framework.exceptions.FrameworkException;

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
			throw new FrameworkException(e, "Image can't be converted to byte array");
		}
	}
	
	//Decode barcode
	public static String decodeBarcode(String urlStr) throws Exception {
		BufferedImage bufferedImage = null;
		if(urlStr.contains("data:image/gif;base64")) {	
			urlStr= urlStr.split("data:image/gif;base64,")[1];
			bufferedImage = decodeImage(urlStr);	
		}
		else {
			URL url = new URL(urlStr);
			bufferedImage = ImageIO.read(url);
		}

		LuminanceSource luminanceSource = new BufferedImageLuminanceSource(bufferedImage);
		BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(luminanceSource));
		Result result = new MultiFormatReader().decode(binaryBitmap); 
		return result.getText();
	}

	//Decode QR Code
	public static String decodeQRCode(String urlStr) throws Exception {
		return decodeBarcode(urlStr);
	}

	//Decode Image and return the BufferedImage
	public static BufferedImage decodeImage(String imageString) throws IOException {

		byte[] imageByte = Base64.getDecoder().decode(imageString);
		InputStream bis = new ByteArrayInputStream(imageByte);
		BufferedImage image = ImageIO.read(bis);
		bis.close();
		return image;
	}
}
