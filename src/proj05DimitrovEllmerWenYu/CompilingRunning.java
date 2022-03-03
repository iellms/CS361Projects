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

    public void compile(String filePath) throws IOException, InterruptedException {
        CompileOnly compiler = new CompileOnly(filePath);
        compiler.start();
    }

    public void compileAndRun(String filePath) throws IOException, InterruptedException {
        CompileAndRun compileAndRun = new CompileAndRun(filePath);
        compileAndRun.start();
    }

    public void stop() {
        
    }

    private class CompileOnly extends Thread{
        private String filePath;

        public CompileOnly(String filePath){
            this.filePath = filePath;
        }

        public void run(){
            System.out.println(this.filePath);
        }
    
    }
    private class CompileAndRun extends Thread{
        private String filePath;


        public CompileAndRun(String filePath){
            this.filePath = filePath;
        }

        public void run(){
            System.out.println(this.filePath);
        }

    }
    
}

