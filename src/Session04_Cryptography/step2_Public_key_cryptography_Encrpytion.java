/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Session04_Cryptography;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.io.FileInputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

/**
 *
 * @author June
 */
public class step2_Public_key_cryptography_Encrpytion {

    public static void main(String[] args) {
        try {
            // Đọc file chứa public key
            FileInputStream fis = new FileInputStream("pubKey.bin");
            byte[] b = new byte[fis.available()];
            fis.read(b);
            fis.close();

            // Tạo public key
            X509EncodedKeySpec spec = new X509EncodedKeySpec(b);
            KeyFactory factory = KeyFactory.getInstance("RSA");
            PublicKey pubKey = factory.generatePublic(spec);

            // Mã hoá dữ liệu
            Cipher c = Cipher.getInstance("RSA");
            c.init(Cipher.ENCRYPT_MODE, pubKey);
            String msg = "Softech.vn";
            byte encryptOut[] = c.doFinal(msg.getBytes());
            String strEncrypt = Base64.encode(encryptOut);
            System.out.println("Chuỗi sau khi mã hoá: " + strEncrypt);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
