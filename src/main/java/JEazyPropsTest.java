
import com.prabhu.jeazyprops.bean.KeyValue;
import com.prabhu.jeazyprops.encryption.AES;
import com.prabhu.jeazyprops.props.BaseProps;
import java.io.File;

public class JEazyPropsTest extends BaseProps {

    public enum RefreshInterval {

        ONE, FIVE, TEN, FIFTEEN, THIRTY, FORTY_FIVE, SIXTY
    };
    public KeyValue Active = new KeyValue("com.prabhu.jeazyprops.JEazyPropsTest.Active", "IS Active");
    public KeyValue DataBaseIP = new KeyValue("com.prabhu.jeazyprops.JEazyPropsTest.DataBaseIP", "DataBase &Host Address", new String[]{"DataBase"});
    public KeyValue DataBasePort = new KeyValue("com.prabhu.jeazyprops.JEazyPropsTest.DataBasePort", "DataBase &Port", new String[]{"DataBase"});
    public KeyValue DataBaseUName = new KeyValue("com.prabhu.jeazyprops.JEazyPropsTest.DataBaseUName", "DataBase &User Name", new String[]{"DataBase"});
    public KeyValue DataBasePass = new KeyValue("com.prabhu.jeazyprops.JEazyPropsTest.DataBasePass", "Data&Base Password", true, new String[]{"DataBase"});
    public KeyValue WebServiceIP = new KeyValue("com.prabhu.jeazyprops.JEazyPropsTest.WebServiceIP", "&WebService Host Address", new String[]{"Webservice"});
    public KeyValue WebServicePort = new KeyValue("com.prabhu.jeazyprops.JEazyPropsTest.WebServicePort", "WebS&ervice Port", new String[]{"Webservice"});
    public KeyValue WebServiceUName = new KeyValue("com.prabhu.jeazyprops.JEazyPropsTest.WebServiceUName", "WebSe&rvice User Name", new String[]{"Webservice"});
    public KeyValue WebServicePass = new KeyValue("com.prabhu.jeazyprops.JEazyPropsTest.WebServicePass", "WebServ&ice Password", true, new String[]{"Webservice"});
    public KeyValue QueueIP = new KeyValue("com.prabhu.jeazyprops.JEazyPropsTest.QueueIP", "&Queue Host Address", new String[]{"Queue"});
    public KeyValue QueuePort = new KeyValue("com.prabhu.jeazyprops.JEazyPropsTest.QueuePort", "Que&ue Port", new String[]{"Queue"});
    public KeyValue QueueUName = new KeyValue("com.prabhu.jeazyprops.JEazyPropsTest.QueueUName", "Queue User &Name", new String[]{"Queue"});
    public KeyValue QueuePass = new KeyValue("com.prabhu.jeazyprops.JEazyPropsTest.QueuePass", "Queue Passwor&d", true, new String[]{"Queue"});
    public KeyValue PropertyFile = new KeyValue("com.prabhu.jeazyprops.JEazyPropsTest.PropertyFile", "&Property File Path");
    public KeyValue RefreshInterval = new KeyValue("com.prabhu.jeazyprops.JEazyPropsTest.RefreshInterval", "Refresh In&terval", new String[]{"Queue", "Webservice", "DataBase"});
    public KeyValue Owner = new KeyValue("com.prabhu.jeazyprops.JEazyPropsTest.Owner", null);
    public static JEazyPropsTest lPropsTest;
    private boolean active;
    private String dataBaseIP;
    private int dataBasePort;
    private String dataBaseUName;
    private String dataBasePass;
    private String webServiceIP;
    private int webServicePort;
    private String webServiceUName;
    private String webServicePass;
    private String queueIP;
    private int queuePort;
    private String queueUName;
    private String queuePass;
    private File propertyFile;
    private RefreshInterval refreshInterval;
    private String owner;

    public static JEazyPropsTest getInstance() {
        if (lPropsTest == null) {
            lPropsTest = new JEazyPropsTest();
            lPropsTest.setEncryption(new AES());
            lPropsTest.loadProperties("JEazyPropsTest.properties");
        }
        return lPropsTest;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getDataBaseIP() {
        return dataBaseIP;
    }

    public void setDataBaseIP(String dataBaseIP) {
        this.dataBaseIP = dataBaseIP;
    }

    public int getDataBasePort() {
        return dataBasePort;
    }

    public void setDataBasePort(int dataBasePort) {
        this.dataBasePort = dataBasePort;
    }

    public String getDataBaseUName() {
        return dataBaseUName;
    }

    public void setDataBaseUName(String dataBaseUName) {
        this.dataBaseUName = dataBaseUName;
    }

    public String getDataBasePass() {
        return dataBasePass;
    }

    public void setDataBasePass(String dataBasePass) {
        this.dataBasePass = dataBasePass;
    }

    public String getWebServiceIP() {
        return webServiceIP;
    }

    public void setWebServiceIP(String webServiceIP) {
        this.webServiceIP = webServiceIP;
    }

    public int getWebServicePort() {
        return webServicePort;
    }

    public void setWebServicePort(int webServicePort) {
        this.webServicePort = webServicePort;
    }

    public String getWebServiceUName() {
        return webServiceUName;
    }

    public void setWebServiceUName(String webServiceUName) {
        this.webServiceUName = webServiceUName;
    }

    public String getWebServicePass() {
        return webServicePass;
    }

    public void setWebServicePass(String webServicePass) {
        this.webServicePass = webServicePass;
    }

    public String getQueueIP() {
        return queueIP;
    }

    public void setQueueIP(String queueIP) {
        this.queueIP = queueIP;
    }

    public int getQueuePort() {
        return queuePort;
    }

    public void setQueuePort(int queuePort) {
        this.queuePort = queuePort;
    }

    public String getQueueUName() {
        return queueUName;
    }

    public void setQueueUName(String queueUName) {
        this.queueUName = queueUName;
    }

    public String getQueuePass() {
        return queuePass;
    }

    public void setQueuePass(String queuePass) {
        this.queuePass = queuePass;
    }

    public File getPropertyFile() {
        return propertyFile;
    }

    public void setPropertyFile(File propertyFile) {
        this.propertyFile = propertyFile;
    }

    public RefreshInterval getRefreshInterval() {
        return refreshInterval;
    }

    public void setRefreshInterval(RefreshInterval refreshInterval) {
        this.refreshInterval = refreshInterval;
    }

    public static void main(String[] args) {
        JEazyPropsTest.getInstance().printProperties();
        JEazyPropsTest.getInstance().displaySettings();
    }
}
