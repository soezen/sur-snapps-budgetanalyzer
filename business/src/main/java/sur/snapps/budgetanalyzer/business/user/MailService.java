package sur.snapps.budgetanalyzer.business.user;

import com.github.sendgrid.SendGrid;
import sur.snapps.budgetanalyzer.domain.user.Token;

/**
 * User: SUR
 * Date: 26/04/14
 * Time: 23:57
 */
public class MailService {

    // TODO css does not work on cloudbees (link includes web)

    public void sendUserInvitationMail(Token token, String toEmail, String inviter) {
        String password = System.getProperty("SENDGRID_PASSWORD");
        String username = System.getProperty("SENDGRID_USERNAME");

        SendGrid sendgrid = new SendGrid(username, password);
        sendgrid.addTo(toEmail);
        sendgrid.setFrom("noreply@budgetanalyzer.snapps.sur");
        sendgrid.setFromName("BudgetAnalyzer");
        sendgrid.setSubject("Invitation to BudgetAnalyzer");
        // TODO use template
        sendgrid.setHtml("<h1>Invitation to BudgetAnalyzer</h1>" +
                "<p>" + inviter + "has invited you to register yourself with the application BudgetAnalyzer</p><p>" + token.getToken() + "</p>");
        System.out.println(sendgrid.send());

    }
}
