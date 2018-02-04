import java.util.Scanner;
public class Budget {
	public static void main(String [] args) {	
		BudgetList A = new BudgetList();
		System.out.println();
		System.out.println("==============================================================");
       	System.out.println("Hello, Welcome to Budgetor! \n" 
       						+"Here we will look at your spending habits \n"
       						+"and give you your running total of how much you spend \n"
       						+"per day, week, month, or year! \n"
       						+"Just enter how much you spend for every purchase \n"
       						+"and we'll keep track of your history.");
       	System.out.println("==============================================================");
       	System.out.println("\nGot a new entry? Just type: \"new\"");
       	System.out.println("\nWant to delete an Entry? Just type: \"delete\"");
       	System.out.println("\nPrint history? Type: \"history\"");
       	System.out.println("\nChange entry? Type \"change\"");
       	System.out.println("\nWant to exit? Type: \"quit\"");


       	Scanner in = new Scanner(System.in);
       	boolean exit = false;
       	while(!exit) {
       		String user = in.next();
       		if(user.compareTo("new") == 0) {
       			System.out.print("type the amount: $");
       			double value = in.nextDouble();
       			A.add(value);
       			continue;
       		}
       		else if(user.compareTo("delete") == 0) {
       			if(A.isEmpty()) {
       				System.out.println("you have nothing to delete");
       				continue;
       			}
       			else{
       				System.out.println("which entry do you wish to delete?");

       			}
       		}
       		else if(user.compareTo("change") == 0) {
       			if(A.isEmpty()) {
       				System.out.println("Nothing to change here");
       				continue;
       			}
       			System.out.println("Which entry would you like to change?");
       			int number = in.nextInt();
       			if(number > A.length()) {
       				System.out.println("entry is out of bounds");
       				continue;
       			}
       			else{
       				System.out.println("Okay, how much?");
       				double value = in.nextDouble(); 
       				A.changeEntry(number, value);
       			}

       		}
       		else if(user.compareTo("quit") == 0) {
       			System.out.println("Thank you for using Budgetor!");
       			exit = true;
       		}
       		else if(user.compareTo("history") == 0) {
       			if(A.isEmpty()) {
       				System.out.println("Nothing is logged");
       				continue;
       			}
       			else {
       				System.out.println(printHistory(A));
       				continue;
       			}
       		}	
       		else {
       			System.out.println("That is not a valid option, please try again.");
       			continue;
       		}
		}       
	}

	static String printHistory(BudgetList client) {
		String sb = "";
		for(client.moveFront(); client.index()!= -1; client.moveNext()) {
        	sb+= "\n" + ((client.index() + 1) + ": $" + client.get());
        }
        sb+= "\n" + "Total Amount: " + client.total();
        return sb;
	}
}