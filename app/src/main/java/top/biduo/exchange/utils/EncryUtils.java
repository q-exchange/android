package top.biduo.exchange.utils;


import android.os.Build;
import android.security.KeyPairGeneratorSpec;
import android.util.Base64;

import top.biduo.exchange.app.MyApplication;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Calendar;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.security.auth.x500.X500Principal;

/**
 * Created by xiongyu on 2016/12/1.
 * 使用ksyStore加密工具类
 */

public class EncryUtils {
    static EncryUtils encryUtilsInstance;
    KeyStore keyStore;

    public static EncryUtils getInstance() {
        synchronized (EncryUtils.class) {
            if (null == encryUtilsInstance) {
                encryUtilsInstance = new EncryUtils();
            }
        }
        return encryUtilsInstance;
    }

    public EncryUtils() {
//        initKeyStore();
    }

    private void initKeyStore(String alias) {
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            createNewKeys(alias);
        }
    }

    private void createNewKeys(String alias) {
        if (!"".equals(alias)) {
            try {
                // Create new key if needed
                if (!keyStore.containsAlias(alias)) {
                    Calendar start = Calendar.getInstance();
                    Calendar end = Calendar.getInstance();
                    end.add(Calendar.YEAR, 1);
                    KeyPairGeneratorSpec spec = null;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                        spec = new KeyPairGeneratorSpec.Builder(MyApplication.getApp())
                                .setAlias(alias)
                                .setSubject(new X500Principal("CN=Sample Name, O=Android Authority"))
                                .setSerialNumber(BigInteger.ONE)
                                .setStartDate(start.getTime())
                                .setEndDate(end.getTime())
                                .build();
                    }
                    KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "AndroidKeyStore");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                        generator.initialize(spec);
                    }

                    KeyPair keyPair = generator.generateKeyPair();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }


    /**
     * 加密方法
     *
     * @param needEncryptWord 　需要加密的字符串
     * @param alias           　加密秘钥
     * @return
     */
    public String encryptString(String needEncryptWord, String alias) {
        if (!"".equals(alias) && !"".equals(needEncryptWord)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                initKeyStore(alias);
            }
            String encryptStr = "";
            byte[] vals = null;
            try {
                PublicKey publicKey;
                if (Build.VERSION.SDK_INT >= 28) {
                    publicKey = keyStore.getCertificate(alias).getPublicKey();
                } else {
                    KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(alias, null);
                    publicKey = privateKeyEntry.getCertificate().getPublicKey();
                }
                if (needEncryptWord.isEmpty()) {
                    return encryptStr;
                }
                Cipher inCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
                inCipher.init(Cipher.ENCRYPT_MODE, publicKey);

                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                CipherOutputStream cipherOutputStream = new CipherOutputStream(
                        outputStream, inCipher);
                cipherOutputStream.write(needEncryptWord.getBytes("UTF-8"));
                cipherOutputStream.close();
                vals = outputStream.toByteArray();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return Base64.encodeToString(vals, Base64.DEFAULT);
        }
        return "";
    }

    public String decryptString(String needDecryptWord, String alias) {
        if (!"".equals(alias) && !"".equals(needDecryptWord)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                initKeyStore(alias);
            }
            String decryptStr = "";
            try {
                PrivateKey privateKey;
                if (Build.VERSION.SDK_INT >= 28) {
                    privateKey= (PrivateKey) keyStore.getKey(alias, null);
                } else {
                    KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(alias, null);
                    privateKey =privateKeyEntry.getPrivateKey();
                }
                Cipher output = Cipher.getInstance("RSA/ECB/PKCS1Padding");
                output.init(Cipher.DECRYPT_MODE,privateKey);
                CipherInputStream cipherInputStream = new CipherInputStream(
                        new ByteArrayInputStream(Base64.decode(needDecryptWord, Base64.DEFAULT)), output);
                ArrayList<Byte> values = new ArrayList<>();
                int nextByte;
                while ((nextByte = cipherInputStream.read()) != -1) {
                    values.add((byte) nextByte);
                }

                byte[] bytes = new byte[values.size()];
                for (int i = 0; i < bytes.length; i++) {
                    bytes[i] = values.get(i).byteValue();
                }

                decryptStr = new String(bytes, 0, bytes.length, "UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return decryptStr;
        }
        return "";
    }
}
