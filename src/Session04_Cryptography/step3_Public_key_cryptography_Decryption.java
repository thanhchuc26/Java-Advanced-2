/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Session04_Cryptography;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.io.FileInputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import javax.crypto.Cipher;

/**
 *
 * @author June
 */
public class step3_Public_key_cryptography_Decryption {

    public static void main(String[] args) {
        try {
            // Đọc file chứa private key
            FileInputStream fis = new FileInputStream("priKey.bin");
            byte[] b = new byte[fis.available()];
            fis.read(b);
            fis.close();

            // Tạo private key
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(b);
            KeyFactory factory = KeyFactory.getInstance("RSA");
            PrivateKey priKey = factory.generatePrivate(spec);

            // Giải mã dữ liệu
            Cipher c = Cipher.getInstance("RSA");
            c.init(Cipher.DECRYPT_MODE, priKey);
            byte decryptOut[] = c.doFinal(Base64.decode("kkeIbn4YxrGg4QnGKgg2g8yFc5/IBvuUWwa61UJWJz81TRdKzJ4cwmTuV+kql4CzDnpmGlMu8JqCjUNkCSRqjTFz/+djiFnQesdFQfJ5bpmcJrknUkvRwWd6DIxzBPARLEoY4me+6VHu1QOliojiCny2fUPw1/tgz+DrtlUmW5M5m0X2PNJL0Y3CCP9zSbwNKHfeZRiz6+3fZDla9y+NBszh3o+TfYcNzC0c0Y2u0p2cf0NR/bgxqRxLBkyBU5bduFdO7t87IHoIMv7ceM6VxYhUbwVp0GVz6FcNNjM4tz+1rdG7TFpcziRHIGTcHAisHjIVAnFltX5+Gw97vqrFWQ=="));
            System.out.println("Dữ liệu sau khi giải mã: " + new String(decryptOut));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
