package vn.com.unit.fe_credit.config;

import java.io.IOException;

import sun.misc.BASE64Decoder;

public class Convert {

	public static byte[] getByteImage(String str) throws IOException {
		if (str == null || str.isEmpty()) {
			return null;
		}
		// Base64.Decoder decoder = Base64.getDecoder();
		// byte[] output= decoder.decode(input );
		// //Verify the decoded string
		// System.out.println(new String(output));
		@SuppressWarnings("restriction")
		BASE64Decoder decoder = new BASE64Decoder();
		@SuppressWarnings("restriction")
		byte[] decodedBytes = decoder.decodeBuffer(str);
		return decodedBytes;
	}

}
