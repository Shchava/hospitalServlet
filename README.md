# Hospital servlet
## Project description:
This is a website for diagnose control in hospitals. It allows doctors to create diagnoses, prescribe medicine, schedule procedures and surgeries to the created diagnose. Nurses can prescribe medicine or procedures to patients’ diagnoses. Patients can see their diagnoses, prescribed medicine, scheduled procedures and surgeries.
## Getting started
### Prerequisites
To run that project you need to have maven, java 8, mySQL server.
### Instalation
Install maven http://www.apache-maven.ru/install.html   
Install tomcat https://tomcat.apache.org/download-90.cgi  
Create mySQL database from schema or dump  
Configure tomcat [CATALINA_HOME]/conf/tomcat-users.xml, to create user with role manager-script  
Configure maven [MAVEN_HOME]/conf/sttings.xml and add server with created user  
Run tomcat  
Open project directory in terminal and run "mvn tomcat7:deploy"    
### Resources
date base schema: https://drive.google.com/open?id=1A2dkN_b-7GIGzyVQvRl2miys96RaLPL8  
data base dump: https://drive.google.com/file/d/1LD0qlmuS1I_aH9TprDBE7x0y_TUmd_DO/view?usp=sharing  
## Running tests
To execute all project test run test suite "HospitalTestSuite"  
