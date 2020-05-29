
import com.prabhu.jeazyprops.bean.JeazyProps;
import com.prabhu.jeazyprops.bean.PropsElement;
import com.prabhu.jeazyprops.interfaces.PropertiesEntity;
import com.prabhu.jeazyprops.props.PropertyDisplayer;
import com.prabhu.jeazyprops.utils.encryption.AES;
import java.io.File;
import java.util.Locale;

@JeazyProps
public class TestPropertiesFile implements PropertiesEntity {

    public enum RefreshInterval {

        ONE, FIVE, TEN, FIFTEEN, THIRTY, FORTY_FIVE, SIXTY
    };

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

    public boolean getActive() {
        return active;
    }

    @PropsElement(keyName = "com.prabhu.jeazyprops.JEazyPropsTest.Active", displayName = "IS &Active")
    public void setActive(boolean active) {
        this.active = active;
    }

    public String getOwner() {
        return owner;
    }

    @PropsElement(keyName = "com.prabhu.jeazyprops.JEazyPropsTest.Owner", displayName = "")
    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getDataBaseIP() {
        return dataBaseIP;
    }

    @PropsElement(keyName = "com.prabhu.jeazyprops.JEazyPropsTest.DataBaseIP", displayName = "DataBase &Host Address", groups = "Database")
    public void setDataBaseIP(String dataBaseIP) {
        this.dataBaseIP = dataBaseIP;
    }

    public int getDataBasePort() {
        return dataBasePort;
    }

    @PropsElement(keyName = "com.prabhu.jeazyprops.JEazyPropsTest.DataBaseIP", displayName = "DataBase H&ost Port", groups = "Database")
    public void setDataBasePort(int dataBasePort) {
        this.dataBasePort = dataBasePort;
    }

    public String getDataBaseUName() {
        return dataBaseUName;
    }

    @PropsElement(keyName = "com.prabhu.jeazyprops.JEazyPropsTest.DataBaseUName", displayName = "DataBase &User Name", groups = "Database")
    public void setDataBaseUName(String dataBaseUName) {
        this.dataBaseUName = dataBaseUName;
    }

    public String getDataBasePass() {
        return dataBasePass;
    }

    @PropsElement(keyName = "com.prabhu.jeazyprops.JEazyPropsTest.DataBasePass", displayName = "Data&Base Password", isEncrypted = true, groups = "Database")
    public void setDataBasePass(String dataBasePass) {
        this.dataBasePass = dataBasePass;
    }

    public String getWebServiceIP() {
        return webServiceIP;
    }

    @PropsElement(keyName = "com.prabhu.jeazyprops.JEazyPropsTest.WebServiceIP", displayName = "&WebService Host Address", groups = "WebService")
    public void setWebServiceIP(String webServiceIP) {
        this.webServiceIP = webServiceIP;
    }

    public int getWebServicePort() {
        return webServicePort;
    }

    @PropsElement(keyName = "com.prabhu.jeazyprops.JEazyPropsTest.WebServicePort", displayName = "WebS&ervice Port", groups = "WebService")
    public void setWebServicePort(int webServicePort) {
        this.webServicePort = webServicePort;
    }

    public String getWebServiceUName() {
        return webServiceUName;
    }

    @PropsElement(keyName = "com.prabhu.jeazyprops.JEazyPropsTest.WebServiceUName", displayName = "WebSe&rvice User Name", groups = "WebService")
    public void setWebServiceUName(String webServiceUName) {
        this.webServiceUName = webServiceUName;
    }

    public String getWebServicePass() {
        return webServicePass;
    }

    @PropsElement(keyName = "com.prabhu.jeazyprops.JEazyPropsTest.WebServicePass", displayName = "WebServ&ice Password", groups = "WebService", isEncrypted = true)
    public void setWebServicePass(String webServicePass) {
        this.webServicePass = webServicePass;
    }

    public String getQueueIP() {
        return queueIP;
    }

    @PropsElement(keyName = "com.prabhu.jeazyprops.JEazyPropsTest.QueueIP", displayName = "&Queue Host Address", groups = "Queue")
    public void setQueueIP(String queueIP) {
        this.queueIP = queueIP;
    }

    public int getQueuePort() {
        return queuePort;
    }

    @PropsElement(keyName = "com.prabhu.jeazyprops.JEazyPropsTest.QueuePort", displayName = "Que&ue Port", groups = "Queue")
    public void setQueuePort(int queuePort) {
        this.queuePort = queuePort;
    }

    public String getQueueUName() {
        return queueUName;
    }

    @PropsElement(keyName = "com.prabhu.jeazyprops.JEazyPropsTest.QueueUName", displayName = "Queue User &Name", groups = "Queue")
    public void setQueueUName(String queueUName) {
        this.queueUName = queueUName;
    }

    public String getQueuePass() {
        return queuePass;
    }

    @PropsElement(keyName = "com.prabhu.jeazyprops.JEazyPropsTest.QueuePass", displayName = "Queue Passwor&d", isEncrypted = true, groups = "Queue")
    public void setQueuePass(String queuePass) {
        this.queuePass = queuePass;
    }

    public File getPropertyFile() {
        return propertyFile;
    }

    @PropsElement(keyName = "com.prabhu.jeazyprops.JEazyPropsTest.PropertyFile", displayName = "&Property File Path")
    public void setPropertyFile(File propertyFile) {
        this.propertyFile = propertyFile;
    }

    public RefreshInterval getRefreshInterval() {
        return refreshInterval;
    }

    @PropsElement(keyName = "com.prabhu.jeazyprops.JEazyPropsTest.RefreshInterval", displayName = "Refresh In&terval", groups = {"Queue", "WebService", "Database"})
    public void setRefreshInterval(RefreshInterval refreshInterval) {
        this.refreshInterval = refreshInterval;
    }

    public static void main(String[] args) {
        Locale.setDefault(new Locale("hi", "IN"));
        PropertiesEntity lPropsTest = new TestPropertiesFile();

        PropertyDisplayer lDisplayer = new PropertyDisplayer(lPropsTest);
        lDisplayer.setEncryption(new AES());
        lDisplayer.loadProperties("samples/Props2Test.xml");
        lDisplayer.displaySettings();

    }
}
