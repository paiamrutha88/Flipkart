# Flipkart
Problem Statement :Launch Flipkart.com (No Login),Search for “samsung mobiles“,Apply Filter on price with range “Min” to “10000” ,RAM as 2GB,PROCESSOR BRAND as Snapdragon
store the listed Phone Name and respective Current Price in csv/Json file.

Solution :
- Framework created in standard Maven format
- Input data is read from excel sheet
- Output is stored in Json file
- Extent report generated at the end of execution
- Tests can be run directly using testNG file RegressionSuite.xml. They can also be run through command prompt using Maven Surefire plugin
- Path to excel file, reports  and Json file are specified in properties file
- All paths referenced using project's root directory

