package com.example.excise;

import java.io.PrintStream;
import java.util.Scanner;

public class ScannerConsoleView implements ConsoleView {
    private final Scanner input;
    private final PrintStream output;

    public ScannerConsoleView(Scanner input, PrintStream output) {
        this.input = input;
        this.output = output;
    }

    @Override
    public void print(String message) {
        output.println(message);
    }

    @Override
    public String readLine() {
       return input.nextLine();
    }

    @Override
    public int readInt() {
        return Integer.parseInt(readLine());
    }
}