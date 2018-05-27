package vn.com.unit.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.ImageIO;

public class CompressJPEGFile {
	
	public static String Process(String folderPath, String fileName){
		//document: http://examples.javacodegeeks.com/desktop-java/imageio/compress-a-jpeg-file/
		String target = "";
		try {
			target = fileName.substring(0, fileName.lastIndexOf('.'))+(new Date()).getTime() + ".jpg";
			File imageFile = new File(folderPath + fileName);
			File compressedImageFile = new File(folderPath + target);
	
			InputStream is = new FileInputStream(imageFile);
			OutputStream os = new FileOutputStream(compressedImageFile);
	
			float quality = 0.5f;
	
			// create a BufferedImage as the result of decoding the supplied InputStream
			BufferedImage image = ImageIO.read(is);
	
			// get all image writers for JPG format
			Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
	
			if (!writers.hasNext())
				throw new IllegalStateException("No writers found");
	
			ImageWriter writer = (ImageWriter) writers.next();
			ImageOutputStream ios = ImageIO.createImageOutputStream(os);
			writer.setOutput(ios);
	
			ImageWriteParam param = writer.getDefaultWriteParam();
	
			// compress to a given quality
			param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			param.setCompressionQuality(quality);
	
			// appends a complete image stream containing a single image and
		    //associated stream and image metadata and thumbnails to the output
			writer.write(null, new IIOImage(image, null, null), param);
	
			// close all streams
			is.close();
			os.close();
			ios.close();
			writer.dispose();
			imageFile.delete();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return target;
	}
}
