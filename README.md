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
2.  