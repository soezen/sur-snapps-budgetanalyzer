package sur.snapps.budgetanalyzer.business.mail;

import sur.snapps.budgetanalyzer.domain.mail.TemplateMail;

/**
 * User: SUR
 * Date: 3/05/14
 * Time: 14:41
 */
public interface MailSender {

    void send(TemplateMail mail);
}
