import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);
        while(true){
            String input = scanner.nextLine();
            if(input=="exit 0"){
               break;
            }
            System.out.println(input+": command not found");
        }
    }
}
