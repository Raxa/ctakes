Patient Module Backend

The project is a Maven project so the pom.xml files denotes all the libraries required by the project as dependencies. Other cTAKES resources required by the different annotators models are required to be downloaded manually though. We provide those with our project since we require only a subset of the resources.

1.pom.xml

Dependencies 
  ctakes-drug-ner : For the ctakes library (only the drug extraction pipeline)
  jersey-core, jersey - server : For providing REST API to our ctakes - java based backend
  jersey - servlet : For holding the servlets on the server.
  jersey - json : To handle the JSON format required to format the output.
  gson: For conversion to JSON format
  mysql-connector-java: Driver to connect to MySQL database.

2.External Resources

The folder SentenceDetection, Chunker, POSTagger and DictionaryLookup contains the external resources required by the ctakes server. The file LookupDesc_DrugNER.xml contains important configuration required to be filled before running the NER pipeline.

Gtanslate API (for language conversion): https://code.google.com/p/java-google-translate-text-to-speech/

The folder Abbreviations has three files drugForm, drugRoute and drugFrequency which we also provide/use to store the frequently used acronyms in the medical prescriptions.

3.Java Sources
  Package org.raxa contains the main project Java file while org.raxa.rest contains the sources related to providing the api.

  a. Abbreviation.java 
    Helper class which provides abbreviation object to normalise the input text. Contains three parameters acronym,     acronymText, and acronymType. acronym is the short form, acronymText is the full form while acronymType is the category of acronym (drugForm, drugFrequency, drugRoute). As of now there are three categories as mentioned we divide the more frequently used acronyms into these three categories and others so that it may also help to extract important parameters missed by the ctakes extraction. These acronyms are read/inflated from the files separately provided in the Abbreviations folder.

  b. Drug.java
    Helper class which creates the Drug object for the drug extracted from the input. Contains the parametes drugName, drugFrequency, drugFrequencyUnit, drugStrength, drugStrengthUnit, drugRoute, drugForm, drugDuration and drugDosage.

  c. NaturalLanguageGenerator.java
    This class provides the method to convert to drug extracted in form of parameters mentioned above to free form natural text. As of now contains only one method getNaturalText() which takes Drug object as input and converts and gives the layman natural text as output.

  d. CtakesService.java
    Is the main class which perform the core of the functions. It performs three main functions of initializing the ctakes Analysis Engine and abbnList, Normalizing the input received and extracting the drug parameters using the initialised analysis engine and creating the Drug object. 

  e. InformationExtraction.java
    This class provides the method to search for more information about the drug using Generic drug name. The information obtained are Indications, Contraindications, Precautions and Side effects.


4.How to run?

Note : Will require Internet Connection to install as well as run the server

Plugins/Softwares Required 
  a. Apache Maven 
  b. Apache Tomcat Server

Method 1.
  a.Download the Project and change working directory to the Patient folder.
  b.run mvn clean install 
  c.This will generate a war file inside target directory.
  d.Place the war directory under the webapps folder of your tomcat installation.(/var/libs/tomcat7/webapps/)
  e.Use POSTMAN (Google Chrome app) to test working. The url is     http://localhost:8080/ctakes/rest/ctakes/hello?text=Prescription&language=language

Current language supported are English, Hindi and Urdu, Kannada, Telugu, Tamil, Bengali. 
Also please make sure acronyms in the prescriptions should be in caps.

Method 2. To run with eclipse
  a.Import the project.
  b.Make sure you have maven eclipse plugin and web development environment installed on your eclipse installation.
  c.Next First Right click on the project and run as maven install. This will download all the libraries required to run the project. This operation may take some time depending upon internet connection.
  d.Second Right click on the project and select run on server. This will prompt you to select which server you wish to run the project on. Please select Apache Tomcat. This will start the project on the server.
  e.Use POSTMAN (Google Chrome app) to test working. The url is http://localhost:8080/ctakes/rest/ctakes/hello?text=FirstPrescription&language=language

Doctor module backend

1.Dataset
doctorModule.sql: Contains the SQL dump of the data required for the Doctor module. The database has three columns Id, Text and Type. Id is the sequential id, Text is the name of the drug/diagnosis etc while Type indicates whether it is a drug or diagnosis or any other medical term.

2.Java Sources
  a. GetInformation.java
    Main class which connects to the backend server to get suggestions based on the input.
  b. doctorService.java
    Servlet class
    
3.How to run is same as Patient Backend
4.pom.xml
    jersey-core, jersey - server : For providing REST API to our ctakes - java based backend
    jersey - servlet : For holding the servlets on the server.
    jersey - json : To handle the JSON format required to format the output.
    gson: For conversion to JSON format
    mysql-connector-java: Driver to connect to MySQL database.

Servers hosted on Amazon web service
a. Patient: http://ec2-54-186-181-202.us-west-2.compute.amazonaws.com:8080/ctakes2/rest/ctakes/hello?text=paracetamol BID AC 10d PO, aspirin BD&language=english (example query)
b. Doctor:  http://ec2-54-186-181-202.us-west-2.compute.amazonaws.com:8080/Doctor-Backend/rest/doctor/hello?text=hep (example query)
