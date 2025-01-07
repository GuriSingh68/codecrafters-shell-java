import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("$ ");
            String input = scanner.nextLine();
            String pathCommand=getPath(input);
            if (input.equals("exit 0")) {
                break;
            }
           else if (input.startsWith("type")) {
               String[] command=input.split("\\s+",2);
               String path=getPath(command[1]);
                    if(command[1].equals("exit"))
                        System.out.println("exit is a shell builtin");
                    else if(command[1].equals("echo"))
                        System.out.println("echo is a shell builtin");
                    else if(command[1].equals("type"))
                        System.out.println("type is a shell builtin");
                    else if(path!=null){
                        System.out.println(command[1]+" is "+path);
                    }
                    else{
                        System.out.println(command[1]+": not found");
                    }
                    
            }
            else if (pathCommand!=null) {
                if(pathCommand!=null){
                    Process p =Runtime.getRuntime().exec(pathCommand);
                    p.waitFor();
                }
            }
           else if (input.startsWith("echo")) {
                input = input.substring(5);
                System.out.println(input);
            } else {
                System.out.println(input + ": command not found");
            }
        }
    }
    public static String getPath(String param){
        String pathEnv = System.getenv("PATH");
        if(pathEnv==null || pathEnv.isEmpty()){
            return "Path not found";
        }
        for(String path: pathEnv.split(":")){
            Path fullPath= Path.of(path,param);
            if(Files.isRegularFile(fullPath) && Files.isExecutable(fullPath)){
                return fullPath.toString();
            }
        }
        return null;
    }
}
