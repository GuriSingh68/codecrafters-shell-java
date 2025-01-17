import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static Path cwd = Path.of("").toAbsolutePath();
    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("$ ");
            String input = scanner.nextLine().trim();
            if (input.equals("exit 0")) {
                break;
            } else if (input.startsWith("type")) {
                String[] command = input.split("\\s+", 2);
                String path = getPath(command[1]);
                if (command[1].equals("exit"))
                    System.out.println("exit is a shell builtin");
                else if (command[1].equals("echo"))
                    System.out.println("echo is a shell builtin");
                else if (command[1].equals("type"))
                    System.out.println("type is a shell builtin");
                else if (command[1].equals("pwd")) {
                    System.out.println(command[1] + " is a shell builtin");
                } else if (command[1].equals("cd")) {
                    System.out.println(command[1] + " is a shell builtin");
                } else if (path != null) {
                    System.out.println(command[1] + " is " + path);
                } else {
                    System.out.println(command[1] + ": not found");
                }

            }

            else if (input.startsWith("echo")) {
                String[] inputString=input.trim().split("\\s+");
                String target=inputString[1];
                if(inputString.length<2 || inputString[1].isBlank()){
                   System.out.println("invalid command");
                }
                else if(target.startsWith("'") ){
                   String targetString=input.substring(6,input.length()-1);
                   System.out.println(targetString.replaceAll("'",""));
                }
                else{
                    // System.out.println(inputString.substring(5,inputString.length()-1).replaceAll("\\s"," "));
                    String targeString=input.substring(5, input.length());
                    targeString=targeString.replaceAll("\\s+", " ");
                    System.out.println(targeString);
                }

            } 
            else if(input.startsWith("cat")){
                String[] inputString=input.trim().split("\\s+",2);
                String target=inputString[1];
                if(target.startsWith("'") && target.endsWith("'")){
                    readContent(target);
                }
                else if(inputString[1].length()<2 ||target.isEmpty()){
                    System.out.println("invalid command");
                }
            }
            else if (input.startsWith("pwd")) {
                System.out.println(cwd);
            } else if (input.startsWith("cd")) {
                String[] pathDir = input.trim().split("\\s+");
                if (pathDir.length < 2) {
                    System.out.println("cd: missing argument");
                }
            
                String target = pathDir[1]; 
                Path newPath;
            
                if (target.equals("./")) {
                    newPath = cwd.normalize();
                } else if (target.startsWith("./")) {
                    newPath = cwd.resolve(target).normalize();
                } else if (target.equals("..")) {
                    newPath = cwd.getParent();
                    if (newPath == null) {
                        System.out.println("cd: already at root directory");
                        return;
                    }
                } 
                else if(target.equals("~")){
                    String pathEnv=System.getenv("HOME");
                    newPath=cwd.resolve(pathEnv).normalize();
                }
                else {
                    newPath = cwd.resolve(target).normalize();
                }
            
                if (Files.exists(newPath) && Files.isDirectory(newPath) || target.isEmpty()) {
                    cwd = newPath; 
                } else {
                    System.out.println("cd: " + target + ": No such file or directory");
                }
            
            } else {
                executeCommand(input);
            }
        }
    }

    public static String getPath(String param) {
        String pathEnv = System.getenv("PATH");
        if (pathEnv == null || pathEnv.isEmpty()) {
            return "Path not found ";
        }
        for (String path : pathEnv.split(":")) {
            Path fullPath = Path.of(path, param);
            if (Files.isRegularFile(fullPath) && Files.isExecutable(fullPath)) {
                return fullPath.toString();
            }
        }
        return null;
    }
    public static void readContent(String input){
        Pattern pattern = Pattern.compile("'([^']*)'");
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            try {
                String filePath = matcher.group(1);
                String content=Files.readString(Paths.get(filePath));
                System.out.print(content+ " ");
            } catch (Exception e) {
                // TODO: handle exception
                System.out.println("erro in reading "+ e);
            }
        }
        }

    public static void executeCommand(String input) {
        try {
            String[] command = input.split("\\s+");
            String path = getPath(command[0]);
            if (path == null) {
                System.out.println(input + ": command not found");
            } else {
                ProcessBuilder processBuilder = new ProcessBuilder(command);
                Process process = processBuilder.start();
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }
                }
                process.waitFor();
            }

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("error");
        }
    }
}
