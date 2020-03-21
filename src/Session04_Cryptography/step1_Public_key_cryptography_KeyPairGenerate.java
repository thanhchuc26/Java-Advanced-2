/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Session04_Cryptography;

import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

/**
 *
 * @author June
 */
public class step1_Public_key_cryptography_KeyPairGenerate {

    public static void main(String[] args) {
        try {
            SecureRandom sr = new SecureRandom();
            //Thuật toán phát sinh khóa - Rivest Shamir Adleman (RSA)
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(2048, sr);

            //Phát sinh cặp khóa
            KeyPair kp = kpg.genKeyPair();
            //PublicKey
            PublicKey pubKey = kp.getPublic();
            //PrivateKey
            PrivateKey priKey = kp.getPrivate();

            //Lưu Public Key
            FileOutputStream fos = new FileOutputStream("pubKey.bin");
            fos.write(pubKey.getEncoded());
            fos.close();

            //Lưu Private Key
            fos = new FileOutputStream("priKey.bin");
            fos.write(priKey.getEncoded());
            fos.close();

            System.out.println("Generate key successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
