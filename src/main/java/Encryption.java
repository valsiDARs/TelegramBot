import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;

public class Encryption {
    private TextEncryptor encryptor = Encryptors.text(Constant.AES_KEY, Constant.HEX_KEY);
    public String cipher(String text){
        return encryptor.encrypt(text);
    }
    public String decrypted(String text){
        return  encryptor.decrypt(text);
    }
}
