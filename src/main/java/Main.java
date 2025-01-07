import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("$ ");
            String input = scanner.nextLine();
            String params="";
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
            else if(!params.equals("")){
                executeCommand(input);
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

 public static void executeCommand(String input) {
        String pathCommand = getPath(input.split("\\s+")[0]);
        if (pathCommand == null) {
            System.out.println(input + ": command not found");
            return;
        }

        try {
            Process p = Runtime.getRuntime().exec(input);
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            p.waitFor();
        } catch (IOException | InterruptedException e) {
            System.out.println("Error executing command: " + e.getMessage());
        }
    }
}
