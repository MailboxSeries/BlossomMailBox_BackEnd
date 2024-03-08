package org.mailbox.blossom.utility;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class CryptUtil implements InitializingBean {
    @Value("${crypt.key}")
    private String secretKey;
    private Cipher encodingCipher;
    private Cipher decodingCipher;

    @Override
    public void afterPropertiesSet() throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(this.secretKey.getBytes(), "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(this.secretKey.substring(0, 16).getBytes());
        this.encodingCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        this.decodingCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        encodingCipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
        decodingCipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
    }

    public String encrypt(String text) throws IllegalBlockSizeException, BadPaddingException {
        byte[] encryptedBytes = encodingCipher.doFinal(text.getBytes(StandardCharsets.UTF_8));

        return Base64.getUrlEncoder().encodeToString(encryptedBytes);
    }

    public String decrypt(String text) throws IllegalBlockSizeException, BadPaddingException {
        byte[] byteString = Base64.getUrlDecoder().decode(text);
        byte[] decryptedBytes = decodingCipher.doFinal(byteString);

        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }
}
