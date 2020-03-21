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

/**
 *
 * @author June
 */
public class Digital_Signature_Example2 {

    /*phuong thuc sign_nguoigoi danh cho nguoi goi :
        - String thongdiep : thong diep can duoc goi.
        - String tenthuattoan : thuat toan danh cho digital signature
        - PrivateKey privateKey : được dùng để mã hóa Signature.
       Phương thức này trả về mảng byte đó là Digital signature, được gởi đến người nhận
     */
    public static byte[] sign_nguoigoi(String thongdiep,
            String tenthuattoan, PrivateKey privateKey) {
        byte[] bSignature = null;
        try {
            Signature signature = Signature.getInstance(tenthuattoan);
            signature.initSign(privateKey);

            signature.update(thongdiep.getBytes());

            bSignature = signature.sign();

            System.out.println("Dang ky thanh cong !");
            return bSignature;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return bSignature;
    }

    /*phuong thuc verify_nguoinhan danh cho nguoi nhan :
        - String thongdiep : thong diep nhan duoc tu nguoi goi.
        - String tenthuattoan : thuat toan danh cho digital signature phu hop voi thuat toan ben goi
        - PublicKey publicKey : được dùng để giải mã Signature.
        Nếu thành công phương thức này hiển thị "kiểm tra thành công", 
         còn ngược lại "kiểm tra thất bại"
     */
    public static void verify_nguoinhan(String thongdiep, String tenthuattoan, PublicKey publicKey, byte[] sign) {
        try {
            Signature signature = Signature.getInstance(tenthuattoan);
            signature.initVerify(publicKey);
            signature.update(thongdiep.getBytes());

            boolean b = signature.verify(sign);
            if (b) {
                System.out.println("kiem tra sign nhan duoc thanh cong, thong diep chinh xac !");
            } else {
                System.out.println("kiem tra sign nhan duoc that bai, thong diep khong dung !");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            //thong diep can goi
            String thongdiep = "Happy new year 2014";

            //cung cap public-key va private-key cho thuat toan Signature
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DSA");
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();

            //ten thuat toan danh cho Signature
            String tenthuattoan = "SHA1withDSA";

            //nguoi goi
            byte[] bSignature = sign_nguoigoi(thongdiep, tenthuattoan, privateKey);

            //nguoi nhan
            verify_nguoinhan(thongdiep, tenthuattoan, publicKey, bSignature);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
