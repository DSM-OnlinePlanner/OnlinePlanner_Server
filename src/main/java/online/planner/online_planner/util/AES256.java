package online.planner.online_planner.util;

import lombok.SneakyThrows;
import online.planner.online_planner.error.exceptions.DecodeFailedException;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Component
public class AES256 {

    static String iv;

    SecretKeySpec keySpec;

    @SneakyThrows
    public AES256() {
        String secretKey = System.getenv("AES_KEY");
        iv = secretKey.substring(0, 16);

        byte[] keyBytes = new byte[16];
        byte[] b = secretKey.getBytes(StandardCharsets.UTF_8);
        int len = b.length;
        if(len > keyBytes.length)
            len = keyBytes.length;
        System.arraycopy(b, 0, keyBytes, 0, len);
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");

        this.keySpec = keySpec;
    }

    // 암호화
    @SneakyThrows
    public String AES_Encode(String str) {
        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        c.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));

        byte[] encrypted = c.doFinal(str.getBytes(StandardCharsets.UTF_8));

        return new String(Base64.encodeBase64(encrypted));
    }

    @SneakyThrows
    public String AES_Decode(String str) {
        try {
            Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
            c.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8)));

            byte[] byteStr = Base64.decodeBase64(str.getBytes());

            return new String(c.doFinal(byteStr), StandardCharsets.UTF_8);
        }catch (Exception e) {
            throw new DecodeFailedException();
        }
    }
}
