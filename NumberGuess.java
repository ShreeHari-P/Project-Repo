import java.util.*;

class Guess{
    public static void guessingNumberGame(Scanner scan){
        
        int num = 1 + (int)(100 * Math.random());

        int k = 5;

        System.out.println("A number is guessed!!!");
        System.out.println("You have " + k + " chances to find the number");

        for(int i = 0; i < k; i++){
            System.out.println();
            System.out.print("Enter a number: " );
            int guess = scan.nextInt();

            if(guess == num){
                System.out.println("Congradulations you have found the number");
                return;
            }else if(guess < num){
                System.out.println("The number is great than: " + guess);
            }else{
                System.out.println("The number is less than: " + guess);
            }
        }

        System.out.println("You are out of moves...");
        System.out.println("The correct number is: " + num);
    }
}

public class NumberGuess{
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        
        String resume;

        do{
            Guess.guessingNumberGame(scan);

            System.out.println("Do you want to play again? (yes/no)");
            resume = scan.nextLine().toLowerCase();
        }while(resume.equals("yes"));

        System.out.println("Thanks for playing...");
        scan.close();
    }
}