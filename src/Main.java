import java.util.Scanner;

/* Algorithms for features:
- Hashtbl for search
-  AVL tree for add/remove?
-  Queues for borrow 
-  Branch: graph/tree with A*
-  Budget: ????
*/

public class Main
{
 public static void main(String[] args) 
 {
   Scanner sc = new Scanner(System.in);
   int option = 6;
   System.out.print("Welcome to the library management system.\nFeatures available are as follows:\n1. Book Search\n2. Manage Books\n3. Borrow book/check borrow records\n4. Branch transport\n5. Book purchase calculator\n6. Exit\n");
   while(true){
    System.out.print("Select an option number: ");

    // handle invalid (non-int) input 
    try{
     option = sc.nextInt();
    }
    catch(Exception e) {
     System.out.println("Invalid input. Options available:");
     System.out.print("1. Book Search\n2. Manage Books\n3. Borrow book/check borrow records\n4. Branch transport\n5. Book purchase calculator\n6. Exit\n");
     sc.nextLine(); // consume invalid input
     continue;
    }

    switch(option){
     case 1:
        System.out.println("Book Search");             
        break;
     case 2:
        System.out.println("Manage Books");             
        break;
     case 3:
        System.out.println("Borrow Book/Check Borrow Records");             
        break;
     case 4:
        System.out.println("Book transport from branch");             
        break;
     case 5:
        System.out.println("Calculate optimal purchase from budget");             
        break;
     case 6:
        System.out.println("Have a nice day!");
        sc.close();
        System.exit(0);  
     default: // any other int
        System.out.println("Invalid input. Options available:");
        System.out.print("1. Book Search\n2. Manage Books\n3. Borrow book/check borrow records\n4. Branch transport\n5. Book purchase calculator\n6. Exit\n");
    }
  }
 }
}

