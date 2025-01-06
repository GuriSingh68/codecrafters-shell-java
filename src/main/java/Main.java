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
            if(input.startsWith("type")){
                input=input.substring(5);
                if(input.equals("echo")) 
                    System.out.println("echo is a shell builtin");
                else if(input.equals("exit"))
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
