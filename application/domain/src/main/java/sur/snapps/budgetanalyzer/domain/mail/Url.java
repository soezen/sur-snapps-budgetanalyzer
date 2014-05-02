package sur.snapps.budgetanalyzer.domain.mail;

/**
 * User: SUR
 * Date: 1/05/14
 * Time: 17:58
 */
public class Url {
    private String serverName;
    private int serverPort;
    private String contextPath;

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public String getContextPath() {
        return contextPath;
    }
}
