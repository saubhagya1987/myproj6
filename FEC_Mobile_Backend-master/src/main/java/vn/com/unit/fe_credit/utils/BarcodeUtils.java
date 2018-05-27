package vn.com.unit.fe_credit.utils;

import java.io.File;
import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;

public class BarcodeUtils {

	public static void generateBarcodeAsPNG(String input, String pathFile, String imageFileName){
        Barcode barcode = null;
        File f = null;
        try {            
            barcode = BarcodeFactory.createCode128(input);
            f = new File(pathFile + "/" + imageFileName + ".png");
            BarcodeImageHandler.savePNG(barcode, f);
        } catch (Exception e) {
        }
    }
	
	public static void main(String[] args) {
		generateBarcodeAsPNG("IT4050614837121", "D:/", "IT4050614837121");
	}
}
