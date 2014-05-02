package sur.snapps.budgetanalyzer.domain.mail;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

/**
 * User: SUR
 * Date: 29/04/14
 * Time: 21:46
 */
public abstract class TemplateMail<C extends TemplateMail> {

    protected abstract ST getTemplate(String name);
    public abstract C to(String toEmail);

    private String template;
    private String subject;
    protected String toEmail;

    protected TemplateMail(String template, String subject) {
        this.template = template;
        this.subject = subject;
    }

    public ST template() {
        return getTemplate(template);
    }

    public String subject() {
        return subject;
    }

    public String to() {
        return toEmail;
    }

    protected ST getTemplateFromFile(String templateName) {
        STGroup templateGroup = new STGroupFile("mail/templates.stg");
        return templateGroup.getInstanceOf(templateName);
    }
}
