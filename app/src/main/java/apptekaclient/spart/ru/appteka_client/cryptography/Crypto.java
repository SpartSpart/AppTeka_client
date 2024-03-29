package apptekaclient.spart.ru.appteka_client.cryptography;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Crypto {

    private static String key = null;
    private static String initVector = null;


    public static void setKeys(String secretKey, String secretVector) {
        String specialKey = "aesEncryptionKey";
        String spaecialInitVector = "encryptionIntVec";

        key = secretKey + specialKey;
        initVector = secretVector + spaecialInitVector;

        key = key.substring(0, 16);
        initVector = initVector.substring(0, 16);
    }


    public static String encryptString(String value) throws Exception {

        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());
            return Base64.encodeToString(encrypted, 0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


    public static String decryptString(String encrypted) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] original = cipher.doFinal(Base64.decode(encrypted, 0));

            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
}