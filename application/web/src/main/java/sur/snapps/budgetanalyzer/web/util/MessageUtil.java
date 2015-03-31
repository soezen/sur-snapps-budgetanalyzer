package sur.snapps.budgetanalyzer.web.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import static org.springframework.context.i18n.LocaleContextHolder.getLocale;

/**
 * @author sur
 * @since 23/03/2015
 */
@Component
public class MessageUtil {

    @Autowired
    private ReloadableResourceBundleMessageSource messageSource;


    public String translate(String key) {
        return messageSource.getMessage(key, new Object[0], getLocale());
    }

    public String translate(String key, Object...params) {
        return messageSource.getMessage(key, params, getLocale());
    }

}
