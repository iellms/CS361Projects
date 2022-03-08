/*
 * File: proj05DimitrovEllmerWenYu.Controller.java
 * Names: Anton Dimitrov, Ian Ellmer, Muqing Wen, Alex Yu
 * Class: CS361
 * Project 5
 * Date: 3/7/21
 */
package proj05DimitrovEllmerWenYu;

import java.io.*;
import org.fxmisc.richtext.StyleClassedTextArea;

import javafx.application.Platform;

public class CompilingRunning{
    public static Thread currThread = null;
    private Process currProcess;
    // printstream for console
    private PrintStream ps;

    public CompilingRunning(StyleClassedTextArea console){
        Console newConsole = new Console(console);
        this.ps = new PrintStream(newConsole,true);
        System.setOut(ps);
        System.setErr(ps);
    }


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
            this.currProcess = compileRun.getProcess();
            compileRun.start();
        }
    }

    public Process getProcess() {return this.currProcess;}

    public void stop() {
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
                compilationProcess.redirectError(ProcessBuilder.Redirect.INHERIT);
                compilationProcess.redirectInput(ProcessBuilder.Redirect.INHERIT);
                compilationProcess.redirectOutput(ProcessBuilder.Redirect.INHERIT);
                Process process = compilationProcess.start();
                int exitcode = process.waitFor();
                System.out.println("Compilation completed with exit code: " + exitcode);
                CompilingRunning.currThread = null;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e){
                System.out.println("Interrupted");

            }
        }

    }
    private class CompileAndRun extends Thread{
        private String filePath;
        private Process compProcess;
        private Process runProcess;


        public CompileAndRun(String filePath){
            this.filePath = filePath;

        }

        public void run(){
            try {
                ProcessBuilder compilationProcess = new ProcessBuilder("javac" , filePath);
                compilationProcess.redirectError(ProcessBuilder.Redirect.INHERIT);
                compilationProcess.redirectInput(ProcessBuilder.Redirect.INHERIT);
                compilationProcess.redirectOutput(ProcessBuilder.Redirect.INHERIT);
                this.compProcess = compilationProcess.start();
                int exitcode = this.compProcess.waitFor();
                System.out.println("Compilation completed with exit code: " + exitcode);
                ProcessBuilder runningProcess = new ProcessBuilder("java", filePath);
                runningProcess.redirectError(ProcessBuilder.Redirect.INHERIT);
                runningProcess.redirectInput(ProcessBuilder.Redirect.INHERIT);
                runningProcess.redirectOutput(ProcessBuilder.Redirect.INHERIT);
                this.runProcess = runningProcess.start();
                int exitCode = this.runProcess.waitFor(); //GETS STUCK HERE
                System.out.println("\nRunning completed with exit code : " + exitCode);
                CompilingRunning.currThread = null;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e){
                System.out.println("Interrupted");
                this.compProcess.destroy();
                this.runProcess.destroy();
            }
        }

        public Process getProcess() {return this.runProcess;}

    }
    /**
     * Console subclass for directing output to JavaFX's console
     */
    public static class Console extends OutputStream{
        private StyleClassedTextArea output;

        public Console(StyleClassedTextArea ta){
            this.output = ta;
        }

        @Override
        public void write(int i){
            Platform.runLater(new Runnable() {
                @Override public void run() {
                    output.appendText(String.valueOf((char)i));
                }
            });
        }


    }

    
    // public static void main(String[] args){
    //     CompilingRunning test = new CompilingRunning();
    //     test.compileAndRun(
    //             "/Users/hsyu98/Downloads/JavaScannerExample.java");
    //     try{
    //     Thread.sleep(10000);
    //     }
    //     catch(InterruptedException e){
    //         System.out.println("here");
    //     }
    //     test.stop();
    // }
}

