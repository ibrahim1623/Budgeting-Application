# My Personal Project

## Budgeting Application

The application will be be used by students, working individuals and just about anyone.
It will allow the user to add purchases they make into the application, which will then keep track
and calculate their monthly expenditure and patterns. This project is of interest to me because
budgeting is a big part of being a college student. An easy-to-use budgeting app would be game changer.

User Stories:
- As a user, I want to be able to add a new purchase to my budgeting record.
- As a user, I want to be able to view all the purchases I've recorded.
- As a user, I want to be able to view details of each purchase I recorded.
- As a user, I want to be able to edit a purchase I may have incorrectly added.
- As a user, I want to be able to save my transaction list to a file.
- As a user, I want to be able to load my transaction list from a file.

# Instructions for Grader

- You can generate the first required action related to the user story "adding multiple Xs to a Y" by clicking the Add 
  Transaction button, then filling out the name and amount fields. 
- You can generate the second required action related to the user story "adding multiple Xs to a Y" by clicking the
  Calculate Average button which shows the average cost of the transactions.
- You can locate my visual component by pressing the Show Image button.
- You can save the state of my application by pressing the Save button and selecting a location and name for the file.
- You can reload the state of my application by pressing the Load button and selecting the file to reload from.
- You can view all the transaction by clicking the View All Transactions Button.

# Phase 4 : Task 2

- Fri Dec 01 09:29:19 PST 2023  
- Added a transaction to transaction record.
- Fri Dec 01 09:29:41 PST 2023
- Added a transaction to transaction record.
- Fri Dec 01 09:29:51 PST 2023
- Average calculated is 23.5

# Phase 4 : Task 3

I would refactor the BudgetingGUI class into multiple classes to handle different operations 
rather than throw all methods in one class. The reason for it would be the fact that 
my BudgetingGUI class is very messy and has a lot going on in it. It would make the code 
much easier to read. This would mean I use the single responsibility principle and add more classes 
and move methods for different components into them. 
