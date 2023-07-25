# Trade Matcher - Order Matching

### This is a console based implementation, but can be extended to API based or file based version very easily.

### **Prerequisites**
- JDK
- Maven

### Build
This project is maven project so can be build using following maven command

    mvn clean install

This would build the project and run all the tests along with it.

### Run
- Run [TradeMatcherApplication](src/main/java/com/pranitpatil/TradeMatcherApplication.java) from your IDE
  OR
- Run following command after maven package

        java -jar target/TradeMatcher-1.0-SNAPSHOT.jar

### How to provide input
- Start the application and enter the order details one by one in "order-id, side, price, quantity" Format.
- Enter all the orders one by one.
- Leave the last line blank once all the orders are entered.
- Application performs the matching for every order entered and decides if the trade is to be executed.
- The application maintains order book and trade book.
- After the last order the applications prints the order book, we can change to code to print the trade book as well.

## Extensibility
### Providing input and output in different ways
- This service is extensible to take input and provide output in different ways by implementing [IOService.java](src/main/java/com/pranitpatil/service/IOService.java)
- Currently standard input and output version is implemented.

### Changing storage
- This application is extensible to store order and trade book.
- This can be done by implementing [OrderBookService.java](src/main/java/com/pranitpatil/service/OrderBookService.java) and [TradeBookService.java](src/main/java/com/pranitpatil/service/TradeBookService.java) respectively.
- Current implementation stores the data in memory.
- This can be switched to a persistent storage.

### Changing console to API 
- The code is extensible to switch from console based to another way.

### Future Improvements
- Concurrent trades
- Different types of orders

### **Author**
##### **Pranit Patil**