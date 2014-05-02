package sur.snapps.budgetanalyzer.business.mail;

import com.github.sendgrid.SendGrid;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import sur.snapps.budgetanalyzer.domain.mail.TemplateMail;
import sur.snapps.budgetanalyzer.util.Logger;

/**
 * User: SUR
 * Date: 29/04/14
 * Time: 20:07
 */
public class SendGridMailSender {

    @Value("${mail.from_address}")
    private String fromEmail;
    @Value("${mail.from_name}")
    private String fromName;

    private SendGrid sendGrid;

    public SendGridMailSender() {}

    public SendGridMailSender(SendGrid sendGrid) {
        this.sendGrid = sendGrid;
    }

    public void send(TemplateMail mail) {
        String result = sendGrid.addTo(mail.to())
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
                Logger.info("user invitation mail sent to " + mail.to());
            } else {
                JSONArray errors = (JSONArray) json.get("errors");
                StringBuilder builder = new StringBuilder("error sending user invitation mail:\n");
                for (Object error : errors) {
                    builder.append("\t - ").append(error).append("\n");
                }
                Logger.error(builder.toString());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
