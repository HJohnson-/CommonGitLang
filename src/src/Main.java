package src;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String gitName;
        switch(args.length) {
            case 0:
                //ask user for a name
                Scanner user_input = new Scanner( System.in );
                gitName = user_input.next( );
                break;
            case 1:
                gitName = args[0];
                break;
            default:
                System.out.println("Please give 1 or 0 arguments");
                return;
        }
        //AnalyseJSON(gitName);
    }
}
