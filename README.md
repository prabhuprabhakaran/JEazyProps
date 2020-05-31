# **JEazyProps**

**JeazyProps** is a java framework, that used to load the properties dynamically into a POJO.

**Properties** is a subclass of **Hashtable**. It is used to maintain lists of values in which the key is a **String** and the value is also a **String**. The **Properties** class is used by many other Java classes. For example, it is the type of object returned by **System.getProperties()** when obtaining environmental values.

To load an external resource in Java, several options immediately come to mind: files, classpath resources, and URLs.

> - **load()-** This method reads a property list (key and element pairs) from the input stream.
> - **getProperty() –** This method searches for the property with the specified key in the properties file.
> - **store()** – This methods writes the properties list to the output steam.

Here I have listed some classic example for loading and saving the properties by using java.

> - http://www.roseindia.net/tutorial/java/core/files/filereadproperties.html
> - http://www.java2s.com/Tutorial/Java/0140__Collections/Usestoretosavetheproperties.htm

By using these snippets we can able to load manually, i.e, the mapping of the properties to the POJO bean should always code by us. There should be no option to change the properties using any GUI. If we want a UI then we have to build that separately to change the properties.

But in case of JeazyProps, It Automatically draws the GUI to save the properties to the file, either as XML or as .properties file. It reduces the effort for loading the properties from file, assigning to the bean and writing the properties to the file.

### Features

1. Reduces complexity and LOC of codes to load number of properties.
2. Supports various data types such as String, Boolean, Integer and Double.
3. Supports Encryption such as Base64, TripleDes and AES by default.
4. Supports both XML and .properties file.
5. Supports both class path file and external file.
6. Supports different GUI components dynamically as per the data type.
7. Supports Validation of data.
8. Supports user defined Encryption Algorithms.
9. Zero lines code to Update and Save the properties on run time.
10. Well Designed and completely documented using javadoc.
11. Additional functionality of displaying properties in command window.
12. Supports property file packed inside the jar to read.
13. On saving, Overrides the property inside jar by using an classpath property file.
14. File and Path selection option using filechooser With validation.
15. Integrable with the main application, and display as separate window.
16. Dual Mode Display (Read Mode and Read-Write Mode)
17. Supports the main application’s Look and feel.

### Steps to use JeazyPorps

1. Add the the jeazyprops dependency using maven

   ```xml
   <dependency>
     <groupId>com.github.prabhuprabhakaran</groupId>
     <artifactId>jeazyprops</artifactId>
     <version>1.0.0</version>
   </dependency>
   ```

   

2.  Create a your POJO class with the properties names and annotations as below

   ```java
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
    }
   ```

3. Invoke in your main class as below

   ```
       public static void main(String[] args) {
           PropertiesEntity lPropsTest = new TestPropertiesFile();
   
           PropertyDisplayer lDisplayer = new PropertyDisplayer(lPropsTest);
           lDisplayer.setEncryption(new AES());
           lDisplayer.loadProperties("samples/Props2Test.xml");
           lDisplayer.displaySettings();
       }
   ```

4. now you can able to view and edit your property files

![2020-05-31_10-59-29](C:\codes\work\Open\JEazyProps\images\2020-05-31_10-59-29.png)

![2020-05-31_10-59-48](C:\codes\work\Open\JEazyProps\images\2020-05-31_10-59-48.png)

![2020-05-31_11-00-11](C:\codes\work\Open\JEazyProps\images\2020-05-31_11-00-11.png)

Thanks :) Happy Coding...