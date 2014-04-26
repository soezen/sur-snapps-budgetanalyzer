package sur.snapps.budgetanalyzer.business.user;

import com.github.sendgrid.SendGrid;
import sur.snapps.budgetanalyzer.domain.user.Token;

/**
 * User: SUR
 * Date: 26/04/14
 * Time: 23:57
 */
public class MailService {

    public void sendUserInvitationMail(Token token, String toEmail) {
        String password = System.getProperty("SENDGRID_PASSWORD");
        String username = System.getProperty("SENDGRID_USERNAME");

        System.out.println("user = " + username);
        System.out.println("pass = " + password);
        SendGrid sendgrid = new SendGrid(username, password);
        sendgrid.addTo(toEmail);
        sendgrid.setFrom("noreply@budgetanalyzer.snapps.sur");
        sendgrid.setFromName("BudgetAnalyzer");
        sendgrid.setSubject("Invitation to BudgetAnalyzer");
        sendgrid.setHtml("<h1>Invitation to BudgetAnalyzer</h1>" +
                "<p>??? has invited you to register yourself with the application BudgetAnalyzer</p><p>" + token.getToken() + "</p>");
        System.out.println(sendgrid.send());

    }
}
