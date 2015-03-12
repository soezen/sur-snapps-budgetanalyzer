package sur.snapps.budgetanalyzer.business.user;

import sur.snapps.budgetanalyzer.domain.user.Token;
import sur.snapps.budgetanalyzer.domain.user.User;

import java.io.Serializable;

/**
 * User: SUR
 * Date: 27/06/14
 * Time: 13:15
 */
public class EditUserView implements Serializable {

    private String username;
    private String name;
    private String email;
    private String newPassword;
    private String confirmPassword;

    private String tokenValue;

    public EditUserView() { }

    public EditUserView(Token token) {
        this.tokenValue = token.value();
        this.email = token.getEmail().address();
    }

    public EditUserView(User user) {
        this.username = user.username();
        this.name = user.name();
        this.email = user.email().address();
    }

    public String getTokenValue() {
        return tokenValue;
    }

    public void setTokenValue(String tokenValue) {
        this.tokenValue = tokenValue;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
