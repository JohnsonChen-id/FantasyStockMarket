# Fantasy Stock Market

## Created by Johnson Chen for "CPSC 210, Software Construction"

An **idealized** stock market game using Java and IntelliJ IDEA, where user could buy in & sell different stocks, try to become the next millionaire,
well, on *this* PC only. This is created as a mini game all can play and enjoy the bid of luck!

This project is out of specific interest to me as stock, just like the bank, and many other systems we human created for 
the purpose of capital-raising from the public to where capital is needed the most, and the rate of return 
balanced with the risk. By building the game from ground up (which is definitely not simulating the reality), 
this could earn me more insight into the world of chances and challenges.



Where a given stock's price could vary according to：
- A random given percentage of increase or decrease when time goes by 
- An assigned value of reputation 


## User Stories:
- As a user, I want to be able to add a new stock to the whole stockList
- As a user, I want to be able to buy an amount of a stock 
- As a user, I want to be able to sell an amount of a stock
- As a user, I want to be able to see the list of stock I hold
- As a user, I want to be able to check the price of the stocks
- As a user, I want to be able to save my stockList purchase and account balance
- As a user, I want to be able to load my stockList purchase and account balance from file
- As a user, I want to be able to use GUI for visualizing the game



## Sidenote
The model classes, which consists Account, StockList and Stock class has a triangular relationship, with Account having 
a hashMap consisting a set of stocks, and StockList also holds a list of stocks, while Account as the overlord, have an 
aggression relationship with StockList. 

If I had more time to work on this project, one thing I would refactor is to introduce a new class called HoldStock 
manager to hold the stocks for a given user and call it inside the main account so that the Account class does not have
to contain this function, which although somehow fits in the scope of Account but can be split out to make the design 
higher cohesive. 
