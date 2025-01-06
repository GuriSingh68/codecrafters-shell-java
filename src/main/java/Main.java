import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);
        while(true){
            System.out.print("$ ");
            String input = scanner.nextLine();
            if(input.equals("exit 0")){
               break;
            }
            if(input.equals("type")){
                System.out.println("echo is a shell builtin");
            }
            if(input.equals("exit")){
                System.out.println("exit is a shell builtin");
            }
             if(input.startsWith("echo")){
                input=input.substring(5);
                System.out.println(input);
            }
            else{
                System.out.println(input+": command not found");
            }
        }
    }
}
