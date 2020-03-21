/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Session04_Cryptography;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.applet.Main;

/**
 *
 * @author June
 */
public class MD5EncryptExample {
    public String encryptPass(String pass) {

        String passEncrypt;

        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            
        }
        md5.update(pass.getBytes());
        BigInteger dis = new BigInteger(1, md5.digest());
        passEncrypt = dis.toString();
        return passEncrypt;

    }

    public String encryptPass2(String pass) throws UnsupportedEncodingException {

        String passEncrypt;

        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
//            Logger.getLogger(CreateAccout.class.getName()).log(Level.SEVERE, null, ex);
        }
        md5.update(pass.getBytes());
        String dis = new String(md5.digest(), 10);
        passEncrypt = dis.toString();

        return passEncrypt;

    }
    
    public static void main(String[] args) {
        try {
            MD5EncryptExample m = new MD5EncryptExample();
            System.out.print("Nhap vao chuoi can ma hoa : ");
            Scanner scn = new Scanner(System.in);
            String input = scn.nextLine();
            String ouput = m.encryptPass(input);
            System.out.println("Chuoi :" + input + " da duoc ma hoa kieu so : " + ouput);
            String ouput2 = m.encryptPass2(input);
            System.out.println("Chuoi :" + input + " da duoc ma hoa kieu @_@ troi dat =))  : " + ouput2);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
