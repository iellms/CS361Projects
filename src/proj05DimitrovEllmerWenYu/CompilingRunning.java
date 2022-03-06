/*
 * File: proj05DimitrovEllmerWenYu.Controller.java
 * Names: Anton Dimitrov, Ian Ellmer, Muqing Wen, Alex Yu
 * Class: CS361
 * Project 5
 * Date: 3/7/21
 */
package proj05DimitrovEllmerWenYu;

import java.io.IOException;

public class CompilingRunning{
    public static Thread currThread = null;

    public void compile(String filePath){
        if (CompilingRunning.currThread == null){
            CompileOnly compiler = new CompileOnly(filePath);
            CompilingRunning.currThread = compiler;
            compiler.start();
        }

    }

    public void compileAndRun(String filePath){
        if (CompilingRunning.currThread == null){
            CompileAndRun compileRun = new CompileAndRun(filePath);
            CompilingRunning.currThread = compileRun;
            compileRun.start();
        }
    }

    public void stop() {
        //TODO: Fix this method, it isn't working the way that it should
        if (CompilingRunning.currThread != null){
            CompilingRunning.currThread.interrupt();
            CompilingRunning.currThread = null;
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
                //TODO: Add printing to the console (perhaps pass in the console as an argument)
                System.out.println(process.getInputStream().readAllBytes());
                CompilingRunning.currThread = null;
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
                //TODO: Add printing to the console (perhaps pass in the console as an argument)
                ProcessBuilder runningProcess = new ProcessBuilder("java", filePath);
                runningProcess.redirectErrorStream(true);
                runningProcess.redirectInput(ProcessBuilder.Redirect.INHERIT);
                runningProcess.redirectOutput(ProcessBuilder.Redirect.INHERIT);
                Process runProcess = runningProcess.start();
                int exitCode = runProcess.waitFor(); //GETS STUCK HERE
                System.out.println("\nExited with error code : " + exitCode);
                CompilingRunning.currThread = null;
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
        try{
        Thread.sleep(1000);
        }
        catch(InterruptedException e){
            System.out.println("here");
        }
        test.stop();
    }
}

