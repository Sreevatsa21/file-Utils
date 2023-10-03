/**
 *	This is the game called yahtzee
 *
 *	@author	Sreevatsa Pervela
 *	@since	9/28/23
 */
import java.util.Scanner;
public class Yahtzee
{
    YahtzeePlayer player1;
    YahtzeePlayer player2;
    boolean player1Turn;
    
    public Yahtzee() {
        this.player1 = new YahtzeePlayer();
        this.player2 = new YahtzeePlayer();
    }
    
    public static void main(final String[] array) {
        new Yahtzee().run();
    }
    
    public void run() {
        this.printHeader();
        this.enterNames();
        this.player1Turn = this.findStart();
        this.printCard();
        for (int i = 1; i < 14; ++i) {
            System.out.printf("\nRound %d of 13 rounds.\n\n", i);
            if (this.player1Turn) {
                this.takeTurn(this.player1);
                this.takeTurn(this.player2);
            }
            else {
                this.takeTurn(this.player2);
                this.takeTurn(this.player1);
            }
        }
        this.printResults();
    }
    
    public void printHeader() {
        System.out.println("\n");
        System.out.println("+------------------------------------------------------------------------------------+");
        System.out.println("| WELCOME TO MONTA VISTA YAHTZEE!                                                    |");
        System.out.println("|                                                                                    |");
        System.out.println("| There are 13 rounds in a game of Yahtzee. In each turn, a player can roll his/her  |");
        System.out.println("| dice up to 3 times in order to get the desired combination. On the first roll, the |");
        System.out.println("| player rolls all five of the dice at once. On the second and third rolls, the      |");
        System.out.println("| player can roll any number of dice he/she wants to, including none or all of them, |");
        System.out.println("| trying to get a good combination.                                                  |");
        System.out.println("| The player can choose whether he/she wants to roll once, twice or three times in   |");
        System.out.println("| each turn. After the three rolls in a turn, the player must put his/her score down |");
        System.out.println("| on the scorecard, under any one of the thirteen categories. The score that the     |");
        System.out.println("| player finally gets for that turn depends on the category/box that he/she chooses  |");
        System.out.println("| and the combination that he/she got by rolling the dice. But once a box is chosen  |");
        System.out.println("| on the score card, it can't be chosen again.                                       |");
        System.out.println("|                                                                                    |");
        System.out.println("| LET'S PLAY SOME YAHTZEE!                                                           |");
        System.out.println("+------------------------------------------------------------------------------------+");
        System.out.println("\n\n");
    }
    
    public void enterNames() {
        final Scanner scanner = new Scanner(System.in);
        System.out.print("Player 1, please enter your first name : ");
        this.player1.setName(scanner.nextLine());
        System.out.print("\nPlayer 2, please enter your first name : ");
        this.player2.setName(scanner.nextLine());
    }
    
    public boolean findStart() {
        int i = 0;
        int total = 0;
        int total2 = 0;
        final DiceGroup diceGroup = new DiceGroup();
        final Scanner scanner = new Scanner(System.in);
        while (i == 0) {
            System.out.printf("\nLet's see who will go first. %s, please hit enter to roll the dice : ", this.player1.getName());
            scanner.nextLine();
            diceGroup.rollDice();
            diceGroup.printDice();
            total = diceGroup.getTotal();
            System.out.printf("\n%s, it's your turn. Please hit enter to roll the dice : ", this.player2.getName());
            scanner.nextLine();
            diceGroup.rollDice();
            diceGroup.printDice();
            total2 = diceGroup.getTotal();
            if (total == total2) {
                System.out.printf("Whoops, we have a tie (both rolled %d). Looks like we'll have to try that again . . .\n", total);
            }
            else {
                i = 1;
            }
        }
        System.out.printf("\n%s, you rolled a sum of %d, and %s, you rolled a sum of %d.\n", this.player1.getName(), total, this.player2.getName(), total2);
        if (total > total2) {
            System.out.print(this.player1.getName());
        }
        else {
            System.out.print(this.player2.getName());
        }
        System.out.println(", since your sum was higher, you'll roll first.");
        return total > total2;
    }
    
    public void printCard() {
        System.out.println("\n");
        System.out.printf("\t\t\t\t\t       3of  4of  Fll Smll Lrg\n", new Object[0]);
        System.out.printf("  NAME\t\t  1    2    3    4    5    6   Knd  Knd  Hse Strt Strt Chnc Ytz!\n", new Object[0]);
        System.out.printf("+-------------------------------------------------------------------------------+\n", new Object[0]);
        final YahtzeeScoreCard scoreCard = this.player1.getScoreCard();
        System.out.printf("| %-12s |", this.player1.getName());
        for (int i = 1; i < 14; ++i) {
            if (scoreCard.getScore(i) > -1) {
                System.out.printf(" %2d |", scoreCard.getScore(i));
            }
            else {
                System.out.printf("    |", new Object[0]);
            }
        }
        System.out.println();
        System.out.printf("+-------------------------------------------------------------------------------+\n", new Object[0]);
        final YahtzeeScoreCard scoreCard2 = this.player2.getScoreCard();
        System.out.printf("| %-12s |", this.player2.getName());
        for (int j = 1; j < 14; ++j) {
            if (scoreCard2.getScore(j) > -1) {
                System.out.printf(" %2d |", scoreCard2.getScore(j));
            }
            else {
                System.out.printf("    |", new Object[0]);
            }
        }
        System.out.println();
        System.out.printf("+-------------------------------------------------------------------------------+\n", new Object[0]);
    }
    
    public void takeTurn(final YahtzeePlayer yahtzeePlayer) {
        final Scanner scanner = new Scanner(System.in);
        final DiceGroup diceGroup = new DiceGroup();
        System.out.printf("\n%s, it's your turn to play. Please hit enter to roll the dice : ", yahtzeePlayer.getName());
        scanner.nextLine();
        diceGroup.rollDice();
        diceGroup.printDice();
        System.out.println("\nWhich di(c)e would you like to keep?  Enter the values you'd like to 'hold' without");
        System.out.println("spaces.  For examples, if you'd like to 'hold' die 1, 2, and 5, enter 125");
        System.out.print("(enter -1 if you'd like to end the turn) : ");
        final String nextLine = scanner.nextLine();
        if (!nextLine.equals("-1")) {
            diceGroup.rollDice(nextLine);
            diceGroup.printDice();
            System.out.println("\nWhich di(c)e would you like to keep?  Enter the values you'd like to 'hold' without");
            System.out.println("spaces.  For examples, if you'd like to 'hold' die 1, 2, and 5, enter 125");
            System.out.print("(enter -1 if you'd like to end the turn) : ");
            final String nextLine2 = scanner.nextLine();
            if (!nextLine2.equals("-1")) {
                diceGroup.rollDice(nextLine2);
                diceGroup.printDice();
            }
        }
        this.printCard();
        System.out.println("\t\t  1    2    3    4    5    6    7    8    9   10   11   12   13\n");
        System.out.printf("%s, now you need to make a choice. Pick a valid integer from the list above : ", yahtzeePlayer.getName());
        boolean b = false;
        int nextInt;
        do {
            nextInt = scanner.nextInt();
            if (nextInt > 0 && nextInt < 14) {
                if (yahtzeePlayer.getScoreCard().getScore(nextInt) > -1) {
                    System.out.print("Pick a valid integer from the list above : ");
                }
                else {
                    b = true;
                }
            }
            else {
                System.out.print("Pick a valid integer from the list above : ");
            }
        } while (!b);
        yahtzeePlayer.getScoreCard().changeScore(nextInt, diceGroup);
        this.printCard();
    }
    
    public void printResults() {
        System.out.println("\n");
        final int total = this.player1.getScoreCard().getTotal();
        final int total2 = this.player2.getScoreCard().getTotal();
        System.out.printf("%-14s had a score of %d\n", this.player1.getName(), total);
        System.out.printf("%-14s had a score of %d\n", this.player2.getName(), total2);
        System.out.println();
    }
}
