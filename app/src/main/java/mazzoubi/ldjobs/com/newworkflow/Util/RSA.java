package mazzoubi.ldjobs.com.newworkflow.Util;

import android.util.Base64;

import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

public class RSA {
    public static final String publ = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjFlkwRN0t3gxeoXaFvQUjg+2I6rAxdFbtz4ki55+ivJqdXjjdx7ouM/VcZXGpulkNXQBM2NzaP8NgwtoRk6S0Tky7HXSIkuddo5P8hOHa5465Du1IwbrotQusJjeXo6+bSJuGrY0H5wcIZvx3MgA6j8u+8hhhEU6avY0MJ5D5wrVhYr8zDzUd/UgOCFmEBlMclBc2UAtZStMXk3F0VEEyYIIQFZOxUiRyoUWqDbnJcDcMou5N0bYVQ+50PlXb/4CwgYPkzffTr4Gm8qbdHc14d41aBoH1Mhs1ioC4rH0Qtd5z0nhzx6XHoqYcIVMrlgPGEOAv7Hqqa8clybhhqGCYQIDAQAB";
    public static final String priv = "MIIEowIBAAKCAQEAjFlkwRN0t3gxeoXaFvQUjg+2I6rAxdFbtz4ki55+ivJqdXjjdx7ouM/VcZXGpulkNXQBM2NzaP8NgwtoRk6S0Tky7HXSIkuddo5P8hOHa5465Du1IwbrotQusJjeXo6+bSJuGrY0H5wcIZvx3MgA6j8u+8hhhEU6avY0MJ5D5wrVhYr8zDzUd/UgOCFmEBlMclBc2UAtZStMXk3F0VEEyYIIQFZOxUiRyoUWqDbnJcDcMou5N0bYVQ+50PlXb/4CwgYPkzffTr4Gm8qbdHc14d41aBoH1Mhs1ioC4rH0Qtd5z0nhzx6XHoqYcIVMrlgPGEOAv7Hqqa8clybhhqGCYQIDAQABAoIBAFG37ylYebol+rE7ZIu+3IUG8ud7kbjcuMlOHptI0qd2lLQHinIehiStprr3GW6wME/LVrOkMVuuPiDSMInXUDcQVe1GgO7FFk8kd7oc0tm/jMy9Uy+s5OCvKOxcZgWqmYgT5wYYlurcy6hqE36Y2aeK2IFz4AvS/zuuVR8F18WbMkyTqMTK1plkd06l38BYGlNF+pxZYgfkjTNVxoX3R2e8eihScr+rnGAb2ERYwOJK/qferwZ7LNxxJbhbHqyZ+HcmPEHQZYduOS6TTP06wH5fDKzHSMzQFwaLSIYdfAY2Vs36aYq7cFpfg4BX31aPHpvIt/G4xirVxXGKwhzTIH0CgYEA4RupWQ0Ld6rR/EEMXD7iGbDlBWKXCHFXuscdCU+yPFEgfpKB3dpizUrvhY4gCVCau1WHM7Q1y45HhZ4NH3qKbvnyJz7Tj4QmbefhZ8CWwDVKQwBjcYVGlJEDiJCpyLpQnjV9twSad6daf+XLOFVefTzt38zwyNTZGjIGrADXo68CgYEAn5wLDPGt0HWACfKiD41Buf7BbDRwQFzVveH1tPKA3Aa1WDCUaX8y/6bIOOMSD77c+6Tc65yV3ixYmH+VvgkTtxbJfboZYQ3gSCwKqxF4/EV5UBFDp8JqcGFrQyvyN8/5Bw7dN6TkuNDg2krL5m4Jj/V2hFUX7lStwT55qNDN7u8CgYBSUMgrbS69UrS4tpyrer85P8tvna63AqNfBIKkrgTyM4RbZH2iv8ITagyOXPtwDI6YZF0tVf+58MxMmxAXa7sFe9bF7R51w5A97mYph25Y2OPP/mtezqqRlzmgja3YXfEu+UaAT2mgyYy5ajFW5zjHOSmPBiUie1rsTlDMQv6XtwKBgDZbHwxOdiJLEcrRfc2MDrk4AElIZn4U35nmDRaNi8Li6flZugFfl0+nTgLJmgoQtxNgJvh6gDOEqLtun+FzF+q/cOlHaoM6BYqurQdrUOehBVp2NrM4K6WQF8ZtffTx994mesILfvVN9XWCCYS1Lr5/GtigXiEFZ5MrIqWSTr+zAoGBAKF2me3AjcIsHBhBuft9Bf7bch4EAidklrwRAyppGX+ASr5yo+Rfk4YMQk7GA7koFEK36fhM2UxsBvgei2EwHakA5p261epubq8zN5QlsQtxjYQsDNf6IbE7eA8z8VT2Rpfd3bEfg/3tXJIb+f02y5xW6e9J8kdTD21AAJ5mmNyf";
    public static final String KEY_ALGORITHM = "RSA";
    public static final String SIGNATURE_ALGORITHM = "SHA1withRSA";

    private static final String PUBLIC_KEY = "RSAPublicKey";
    private static final String PRIVATE_KEY = "RSAPrivateKey";
    private static final String TAG = "";
    private static Map<String, Object> keyMap;

    public static byte[] decryptBASE64(String key) throws Exception {
        return Base64.decode(key, Base64.DEFAULT);
    }

    public static String encryptBASE64(byte[] key) throws Exception {
        return Base64.encodeToString(key, Base64.DEFAULT);
    }

    public static String sign(String message, PrivateKey privateKey) throws SignatureException {
        try {
            Signature sign = Signature.getInstance(SIGNATURE_ALGORITHM);
            sign.initSign(privateKey);
            sign.update(message.getBytes("UTF-8"));
            byte[] signature = sign.sign();
            return Base64.encodeToString(signature, Base64.DEFAULT);
        } catch (Exception ex) {
            throw new SignatureException(ex);
        }
    }

    public static String sign2(byte[] data, String privateKey) throws Exception {

        byte[] keyBytes = decryptBASE64(privateKey);

        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);

        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);

        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(priKey);
        signature.update(data);

        return encryptBASE64(signature.sign());
    }

    public static boolean verify(String message, PublicKey publicKey, String signature) throws SignatureException{
        try {
            Signature sign = Signature.getInstance(SIGNATURE_ALGORITHM);
            sign.initVerify(publicKey);
            sign.update(message.getBytes("UTF-8"));
            byte[] signatureBytes = Base64.decode(signature, Base64.DEFAULT);
            return sign.verify(signatureBytes);
        } catch (Exception ex) {
            throw new SignatureException(ex);
        }
    }

    public static boolean verify2(byte[] data, String publicKey, String sign)
            throws Exception {

        byte[] keyBytes = decryptBASE64(publicKey);

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);

        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

        PublicKey pubKey = keyFactory.generatePublic(keySpec);

        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(pubKey);
        signature.update(data);

        return signature.verify(decryptBASE64(sign));
    }

    public static byte[] decryptByPrivateKey(byte[] data, String key)
            throws Exception {

        byte[] keyBytes = decryptBASE64(key);

        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        return cipher.doFinal(data);
    }

    public static byte[] decryptByPublicKey(byte[] data, String key)
            throws Exception {

        byte[] keyBytes = decryptBASE64(key);

        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);

        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);

        return cipher.doFinal(data);
    }

    public static byte[] encryptByPublicKey(byte[] data, String key)
            throws Exception {

        byte[] keyBytes = decryptBASE64(key);

        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);

        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        return cipher.doFinal(data);
    }

    public static byte[] encryptByPrivateKey(byte[] data, String key)
            throws Exception {

        byte[] keyBytes = decryptBASE64(key);

        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);

        return cipher.doFinal(data);
    }

    public static String getPrivateKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);

        return encryptBASE64(key.getEncoded());
    }

    public static String getPublicKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);

        return encryptBASE64(key.getEncoded());
    }

    public static Key getPublicKey2(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);

        return key;
    }

    public static Map<String, Object> initKey() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator
                .getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(4096);

        KeyPair keyPair = keyPairGen.generateKeyPair();

        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        keyMap = new HashMap<String, Object>(2);

        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    public static void setKey(RSAPublicKey pub, PrivateKey pri) throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator
                .getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(4096);

        keyMap = new HashMap<String, Object>(2);

        keyMap.put(PUBLIC_KEY, pub);
        keyMap.put(PRIVATE_KEY, pri);

    }

    public static Map<String, Object> GetMap(){
        return keyMap;
    }

    public static PublicKey getPublicKey(String MODULUS, String EXPONENT) throws Exception {
        byte[] modulusBytes = Base64.decode(MODULUS,0);
        byte[] exponentBytes = Base64.decode(EXPONENT,0);

        BigInteger modulus = new BigInteger(1, (modulusBytes) );
        BigInteger exponent = new BigInteger(1, (exponentBytes));

        RSAPublicKeySpec spec = new RSAPublicKeySpec(modulus, exponent);
        KeyFactory kf = KeyFactory.getInstance(RSA.KEY_ALGORITHM);
        return kf.generatePublic(spec);
    }

    public static byte[] encrypt(Key publicKey, String s) throws Exception {
        byte[] byteData = s.getBytes();
        Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedData = cipher.doFinal(byteData);

        return encryptedData;
    }

}