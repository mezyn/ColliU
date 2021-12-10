package miscellaneous;

import java.util.Scanner;

/** Methods to allow the user to input information.
 *
 */
public class UserInput {

  public static Scanner input = new Scanner(System.in);


  public static String readInput() {
    String sentence = input.nextLine();
    return sentence;
  }
  /** Method for the user to input doubles.
   *
   */
  public static double readDouble(String message) throws Exception {
    System.out.print(message);
    double value = input.nextDouble();
    input.nextLine();
    return value;
  }

  /** Method for the user to input integers.
   *
   */
  public static int readInt(String message) {
    System.out.print(message);
    int amount = input.nextInt();
    input.nextLine();
    return amount;
  }

  /** Method for the user to input Strings.
   *
   */
  public static String readLine(String message) {
    System.out.print(message);
    String sentence = input.nextLine();
    return sentence;
  }

}
