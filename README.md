# assessment
# Working File is C:\Folders\Workspaces\IntelliJ\assessment\src\main\resources\QnA_Template.csv
#Command To Execute this JAR
java -jar jar-file arg1, arg2

arg1 -> File Absolute Path
arg2 -> File Name

Refer Resources folder for CSV File Format.

Steps:
1. QnA Template
    1. Open Excel sheet file of QnA Template Version X (Tab 1).
    2. Create One more CSV file and copy paste(Select Values Paste Option) from Excel Sheet.
    3. Replace all , to some unique character(ex: $) which is not available in Sheet.
    4. Run the Jar and Pass this file(Absolute Path) as argument.
    5. Open JSON in any editor and Replace all Unique Character(Step 2) to , . i.e. Reverse of Step 2.
    6. Copy and update Document Section attribute.

2. Maturity Level
    1. Open Excel sheet file of Maturity Levels(Tab 2).
    2. Create One more CSV file and copy paste(Select Values Paste Option) from Excel Sheet.
    3. Run the Jar and Pass this file(Absolute Path) as argument.
    4. Copy and update Document maturityLevels attribute.