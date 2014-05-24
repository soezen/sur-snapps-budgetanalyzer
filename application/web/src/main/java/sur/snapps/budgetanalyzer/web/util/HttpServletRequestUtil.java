package sur.snapps.budgetanalyzer.web.util;

import sur.snapps.budgetanalyzer.domain.mail.Url;

import javax.servlet.http.HttpServletRequest;

/**
 * User: SUR
 * Date: 24/05/14
 * Time: 10:17
 */
public class HttpServletRequestUtil {

    public static final Url createUrl(HttpServletRequest request) {
        Url url = new Url();
        url.setServerName(request.getServerName());
        url.setServerPort(request.getServerPort());
        url.setContextPath(request.getContextPath());
        return url;
    }
}
