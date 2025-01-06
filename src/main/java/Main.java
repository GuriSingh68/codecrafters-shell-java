import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);
        boolean ip= true;
        while(ip){
            System.out.print("$ ");
            String input = scanner.nextLine();
            System.out.println(input+": command not found");
            ip=false;
        }
        if(!ip){
            System.out.println("$ "+"exit 0");
        }
    }
}
