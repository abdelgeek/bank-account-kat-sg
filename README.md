

# Bank account Kata

Think of your personal bank account experience When in doubt,
 go for the simplest solution.  

## Requirements

* Deposit and Withdrawal
* Account statement (date, amount, balance)
* Statement printing


### Architecture

The architecture used is a layered architecture that separates the written code into 4 layers:
   
    - domain : encapsulates all the objects specific to our information system
    - service : represents the layer where all business processes are performed
    - dto : encapsulates the transversal data that are used in all the other layers
    - repository : this layer allows to simulate an account storage.

### Technologies used

* Java 17
* Junit 
* Mockito
* Maven
