/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Session05_DigitalSignatures;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 *
 * @author June
 */
public class Digital_Signature_Example1 {

    private static void taokey() {
        try {
            SecureRandom sr = new SecureRandom();
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("DSA");
            kpg.initialize(1024, sr);
            KeyPair keys = kpg.generateKeyPair();

            // Save private key
            PrivateKey privateKey = keys.getPrivate();
            FileOutputStream fos = new FileOutputStream("priKey.bin");
            fos.write(privateKey.getEncoded());
            fos.close();

            // Save public key
            PublicKey publicKey = keys.getPublic();
            fos = new FileOutputStream("pubKey.bin");
            fos.write(publicKey.getEncoded());
            fos.close();

// JOptionPane.showMessageDialog(this, "Register key successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void ky() {
        try {
            // Nạp private key từ file
            FileInputStream fis = new FileInputStream("priKey.bin");
            byte[] b = new byte[fis.available()];
            fis.read(b);
            fis.close();

            // Tạo private key
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(b);
            KeyFactory factory = KeyFactory.getInstance("DSA");
            PrivateKey priKey = factory.generatePrivate(spec);
            //********************************

            //Ký số (Sign)***************************
            // Tạo đối tượng signer
            Signature signer = Signature.getInstance("DSA");
            signer.initSign(priKey, new SecureRandom());

            // Chọn file để thực hiện ký số 
            String filename = "Doc1.docx";
            fis = new FileInputStream(filename);
            byte byteFile[] = new byte[fis.available()];

            // Chèn message vào đối tượng signer
            signer.update(byteFile);
            byte[] bsign = signer.sign();

            // Lưu chữ ký số
            FileOutputStream fos = new FileOutputStream("dsa");
            fos.write(bsign);
            //*******************************

            System.out.println("Sign document successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void kiemtra() throws FileNotFoundException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException, SignatureException {

        // Nạp public key từ file
        FileInputStream fis = new FileInputStream("pubKey.bin");
        byte[] b = new byte[fis.available()];
        fis.read(b);
        fis.close();

        // Tạo public key
        X509EncodedKeySpec spec = new X509EncodedKeySpec(b);
        KeyFactory factory = KeyFactory.getInstance("DSA");
        PublicKey pubKey = factory.generatePublic(spec);

        // Khởi tạo đối tượng Signature
        Signature s = Signature.getInstance("DSA");
        s.initVerify(pubKey);

        // Chọn file để kiểm chứng 
        String filename = "Doc1.docx";

        fis = new FileInputStream(filename);
        byte byteFile[] = new byte[fis.available()];
        fis.close();

        // Nạp message vào đối tượng Signuture
        s.update(byteFile);

        // Kiểm chứng chữ ký trên Message
        // Nạp chữ ký signature từ file
        fis = new FileInputStream("dsa");
        byte[] bsign = new byte[fis.available()];
        fis.read(bsign);
        fis.close();

        // Kết quả kiểm chứng
        boolean result = s.verify(bsign);
        if (result == true) {
            System.out.println("Message is verified");
        } else {
            System.out.println("Message isn't verified");

        }
    }

    public static void main(String[] args) throws IOException, FileNotFoundException, InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, SignatureException {
        taokey();
        ky();
        kiemtra();
    }
}
