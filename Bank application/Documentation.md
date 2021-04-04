# Analysis and Design Document

# Requirement analysis

## Assignment Specification
 The application is meant to be used by front desk employees of a bank. The application should have two types of users (a regular user represented by the front desk employee and an administrator user) which have to provide a username and a password in order to use the application. 

## Function requirements

The regular user can perform the following operations:
Add/update/view client information (name, identity card number, personal numerical code, address, etc.).
Create/update/delete/view client account (account information: identification number, type, amount of money, date of creation).
money between accounts.
Process utilities bills (represented as money transfer to some predefined accounts of utility companies - e.g., gas, electricity, internet).

The administrator user can perform the following operations:
CRUD on employees’ information.
Generate reports for a particular period containing the activities performed by an employee.

## Non-functional Requirements
Availability: The application can be used any time of the day, any day of the weeek. 

Performance: The application supports one user online at the same time. The application gets a response when accessing the databse in a relatively short time. Also the users will get reports and answers to their requests in a relatively short time.

Security: The users will log in via an username and a password to ensure the safeness of their accounts.

Testability: The application can be tested. If the input of the test is wrong there will be displayed a specific error message, and if the input is suitable then there will be displayed an output. 

Usability: The application runs on Microsoft Windows operating systems. The graphical user interface is relatively beginner friendly and easy to use, making the application usable by all kinds of users. 

# Use-Case Model

![diagram](admin.png)

![diagram](employee.png)

## Use case 1

    * Use case: Add an user
    * Level: one of: user-goal
    * Primary actor: Administrator
    * Main success scenario: 
	1. The administrator introduces the required data(an username and a password) in the correct fields;
	2. The administrator selects the "add user" option; 
	3. The user is added in the database successfully.
    * Extensions: If the user's username already exists in the database, there will be displayed an error message.

## Use case 2

    * Use case: Create an account
    * Level: one of: user-goal
    * Primary actor:Employee
    * Main success scenario: 
	2. The employee introduces the required data(an id, a client id, id number, type, money amount and creation date) in the correct fields; 
	2. The employee selects the "create account" option;
	3. The account is created and stored in the database successfully.
    * Extensions: If the account id already exists in the database, then there will be displayed an error message.

## Use case 3

    * Use case: Making a money transfer
    * Level: one of: user-goal
    * Primary actor: Employee
    * Main success scenario:
	1. The employee selects the "realise a transfer" option;
	2. He selects the sender and receiver account from the table and introduces the money amount;
	3. The transfer will be a success and the accounts will be updated.
    * Extensions: .

# System Architectural Design

## Architectural Pattern Description
-Presentation Layer: includes the view and controller packages. Their job is to connect the graphical interface with the application and to implement how the application should behave in different contexts;

-Business Layer: includes the dataModel package in which are described the database tables(the objects that the application tracks information about) and their characteristics and attributes;

-Persistence Layer: includes the dataAccess package in which are classes which contain the querrys needed to comunicate and change the database;

-Database Layer: is represented by MySQL.

## Diagrams

Package diagram:

![diagram](PackageDiagram.png)

# UML Sequence Diagrams
The sequence diagram for the delete account operation:
![diagram](sequenceDiagram.png)

# Class Design

## Design Patterns Description
The application is implemented with a Table Data Gateway. The Table Data Gateway works with instances of only one table.

## UML Class Diagram
![diagram](UML.png)

# Data Model
The data model is represented by the classes that represent the objects that the application wants to track data about. Those are the objects which represent the tables in the database. This classes are implemented and contained by the dataModel package. 

![diagram](datamodel.png)

# System Testing
The application has a JUnit test which verifies if an username corresponds to a specified client id. The rest of the application was tested manually. The application validates the inputs against invalid data before savint it in the database.


# Bibliography
- [Online diagram drawing software](https://yuml.me/) ([Samples](https://yuml.me/diagram/scruffy/class/samples))
- [Yet another online diagram drawing software](https://www.draw.io)
