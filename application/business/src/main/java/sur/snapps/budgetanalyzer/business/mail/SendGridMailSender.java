package sur.snapps.budgetanalyzer.business.mail;

import com.github.sendgrid.SendGrid;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import sur.snapps.budgetanalyzer.business.Logger;
import sur.snapps.budgetanalyzer.domain.mail.TemplateMail;

/**
 * User: SUR
 * Date: 29/04/14
 * Time: 20:07
 */
public class SendGridMailSender implements MailSender {

    @Value("${mail.from_address}")
    private String fromEmail;
    @Value("${mail.from_name}")
    private String fromName;

    private SendGrid sendGrid;

    public SendGridMailSender() {}

    public SendGridMailSender(SendGrid sendGrid) {
        this.sendGrid = sendGrid;
    }

    @Override
    public void send(TemplateMail mail) {
        String to = mail.to().address();
        String result = sendGrid.addTo(to)
                .setFrom(fromEmail)
                .setFromName(fromName)
                .setSubject(mail.subject())
                .setHtml(mail.template().render())
                .send();

        try {
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(result);
            String message = (String) json.get("message");
            if ("success".equals(message)) {
                Logger.info("user invitation mail sent to " + to);
            } else {
                JSONArray errors = (JSONArray) json.get("errors");
                StringBuilder builder = new StringBuilder("error sending invitation mail to " + to + ":\n");
                if (errors != null) {
                    for (Object error : errors) {
                        builder.append("\t - ").append(error).append("\n");
                    }
                }
                Logger.error(builder.toString());
            }
        } catch (ParseException e) {
            Logger.error("invalid json message: " + result);
        }
    }
}
