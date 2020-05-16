package CloudAppi.CodeChallengeBack.service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public abstract class AES {
    private static final String SECRET_KEY_1 = "ssdkF$HUy2A#D%kd";
    private static final String SECRET_KEY_2 = "weJiSEvR5yAC5ftB";

    protected final IvParameterSpec ivParameterSpec  = new IvParameterSpec(SECRET_KEY_1.getBytes("UTF-8"));;
    protected final SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET_KEY_2.getBytes("UTF-8"), "AES");;
    protected Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");

    protected AES() throws UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException {
    }
}
