/*
 * File: proj05DimitrovEllmerWenYu.Controller.java
 * Names: Anton Dimitrov, Ian Ellmer, Muqing Wen, Alex Yu
 * Class: CS361
 * Project 5
 * Date: 3/7/21
 */
package proj05DimitrovEllmerWenYu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CompilingRunning{
    public static Process currProcess = null;

    public void compile(String filePath){
        if (CompilingRunning.currProcess== null){
            CompileOnly compiler = new CompileOnly(filePath);
            compiler.start();
        }

    }

    public void compileAndRun(String filePath){
        if (CompilingRunning.currProcess == null){
            CompileAndRun compileRun = new CompileAndRun(filePath);
            compileRun.start();
        }
    }

    public void stop() {
        //TODO: Fix this method, it isn't working the way that it should
        if (CompilingRunning.currProcess != null){
            CompilingRunning.currProcess.destroy();
            CompilingRunning.currProcess = null;
        }
    }

    private class CompileOnly extends Thread{
        private String filePath;

        public CompileOnly(String filePath){
            this.filePath = filePath;
        }

        public void run(){
            try {
                ProcessBuilder compilationProcess = new ProcessBuilder();
                compilationProcess.command("javac" , filePath);
                Process process = compilationProcess.start();
                CompilingRunning.currProcess = process;
                //TODO: Add printing to the console (perhaps pass in the console as an argument)
                System.out.println(process.getInputStream().readAllBytes());
                CompilingRunning.currProcess = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private class CompileAndRun extends Thread{
        private String filePath;


        public CompileAndRun(String filePath){
            this.filePath = filePath;
        }

        public void run(){
            try {
                ProcessBuilder compilationProcess = new ProcessBuilder("javac" , filePath);
                Process compProcess = compilationProcess.start();
                CompilingRunning.currProcess = compProcess;
                //TODO: Add printing to the console (perhaps pass in the console as an argument)
                ProcessBuilder runningProcess = new ProcessBuilder("java", filePath);
                runningProcess.redirectErrorStream(true);
                Process runProcess = runningProcess.start();
                CompilingRunning.currProcess = runProcess;
                BufferedReader reader =
                    new BufferedReader(new InputStreamReader(runProcess.getInputStream()));

                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }

                int exitCode = runProcess.waitFor();
                System.out.println("\nExited with error code : " + exitCode);
                CompilingRunning.currProcess = null;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }

    }
    
    public static void main(String[] args){
        CompilingRunning test = new CompilingRunning();
        test.compileAndRun("/Users/ianellmer/Desktop/TestingJava/A.java");
    }
}

