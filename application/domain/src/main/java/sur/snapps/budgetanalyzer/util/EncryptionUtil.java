package sur.snapps.budgetanalyzer.util;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

/**
 * @author sur
 * @since 10/03/2015
 */
public final class EncryptionUtil {

    private EncryptionUtil() {}


    public static String encrypt(String password) {
        Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
        return passwordEncoder.encodePassword(password, "BUDGET-ANALYZER");
    }
}
