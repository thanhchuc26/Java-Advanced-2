/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Session05_DigitalSignatures;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignedObject;
import java.util.Vector;

/**
 *
 * @author June
 */
public class Digital_Signature_Example3 {

    /*phuong thuc sign_bengoi danh cho nguoi goi :
        - Vector signobject : thong diep can duoc goi.
        - String tenthuattoan : thuat toan danh cho digital signature
        - PrivateKey privateKey : được dùng để mã hóa Signature.
       Phương thức này trả về mảng byte đó là Digital signature, được gởi đến người nhận
     */
    public static SignedObject sign_bengoi(Vector signobject, String tenthuattoan,
            PrivateKey privateKey) {
        try {
            Signature signature = Signature.getInstance(tenthuattoan);
            SignedObject so = new SignedObject(signobject, privateKey, signature);
            return so;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    /*phuong thuc verify_bennhan danh cho nguoi nhan :
        - SignedObject so : thong diep nhan duoc tu nguoi goi.
        - String tenthuattoan : thuat toan danh cho digital signature phu hop voi thuat toan ben goi
        - PublicKey publicKey : được dùng để giải mã Signature.
        Nếu thành công phương thức này hiển thị "kiểm tra thành công", còn ngược lại "kiểm tra thất bại"
     */
    public static void verify_bennhan(SignedObject so, PublicKey publicKey,
            String tenthuattoan) {
        try {
            Signature signature = Signature.getInstance(tenthuattoan);
            boolean b = so.verify(publicKey, signature);
            if (b) {
                System.out.println("nhan object thanh cong");
            } else {
                System.out.println("nhan object that bai");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            //thong diep can goi
            String thongdiep1 = "Happy new year 2014";
            String thongdiep2 = "Happy birthday";
            long sobimat = 1332895473;

            Vector v = new Vector();
            v.add(thongdiep1);
            v.add(thongdiep2);
            v.add(sobimat);

            //cung cap public-key va private-key cho thuat toan Signature
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();

            //ten thuat toan danh cho Signature
            String tenthuattoan = "SHA1withRSA";

            //nguoi goi
            SignedObject bSignature = sign_bengoi(v, tenthuattoan, privateKey);

            //nguoi nhan
            verify_bennhan(bSignature, publicKey, tenthuattoan);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
