package Code;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Main {

	public static void main(String[] args) throws IOException {
		ArrayList<ArrayList<Boolean>> alloutputs=new ArrayList<ArrayList<Boolean>>();
		GetTruthTable t = new GetTruthTable();

		Scanner sc = new Scanner(System.in);
		System.out.println("Please Enter number of expressions:");
		String n = sc.nextLine();
		for (int h = 0; h < Integer.parseInt(n); h++) {
			System.out.println("Please Enter Equation (Use '||' for OR ,'&&' for AND and '!' for NOT):");
			String equation = sc.nextLine();
			equation = equation.toUpperCase();
			ArrayList<Character> variables = t.EvaluateVariables(equation);
			boolean[][] table = t.constructTruthTable(variables.size());
			int m = (int) Math.pow(2.0, (double) variables.size());
			ArrayList<Boolean> output = t.getOutput(table, variables, equation);
			java.util.Collections.sort(variables);
			for (int k = 0; k < variables.size(); k++) {
				System.out.print("|  " + variables.get(k)+"  ");
			}
			System.out.print("|output");
			System.out.println();
			for (int i = 0; i < m; i++) {
				for (int j = 0; j < variables.size(); j++) {
					System.out.print("|" + table[i][j]);
				}
				try {
				System.out.print("|" + output.get(i));
				System.out.println();
				} catch (Exception e) {
					System.out.println("|Invalid Syntax");
				}
				
			}
			t.save(table, output, variables);
			alloutputs.add(output);
		}
		t.close();
		System.out.println("Enter the operation you want: tautology(T), contradoction(C) or equivalence(E)");
		char oper = sc.next().charAt(0);
		int n1, n2;
		switch(oper){
		case ('T'):
			n1=sc.nextInt();
			System.out.println(t.isTautology(alloutputs.get(n1-1)));
			break;
		case ('t'):
			n1=sc.nextInt();
			System.out.println(t.isTautology(alloutputs.get(n1-1)));
			break;
		case ('C'):
			n1=sc.nextInt();
			System.out.println(t.isContradiction(alloutputs.get(n1-1)));
			break;
		case ('c'):
			n1=sc.nextInt();
			System.out.println(t.isContradiction(alloutputs.get(n1-1)));
			break;
		case ('E'):
			n1=sc.nextInt();
			n2=sc.nextInt();
			System.out.println(t.checkEquality(alloutputs.get(n1-1), alloutputs.get(n2-1)));
			break;
		case ('e'):
			n1=sc.nextInt();
			n2=sc.nextInt();
			System.out.println(t.checkEquality(alloutputs.get(n1-1), alloutputs.get(n2-1)));
			break;
		default: System.out.println("Invalid Operation");
		}
//		System.out.println("Enter number of expression to check tautology ");
//		System.out.println("Enter number of expression to check contradiction ");
//		System.out.println("Enter numbers of expressions to check equivalence:");
	}

}
