package Code;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.sun.org.apache.xpath.internal.operations.Variable;


public class GetTruthTable {
	private FileWriter out;

	public GetTruthTable () throws IOException {
	         out = new FileWriter("output.txt", false);
	         out.write("Truth Tables");
	         out.append("\n");
	      
	}
	
	public ArrayList<Character> EvaluateVariables(String equation) {
		ArrayList<Character> equationVariables = new ArrayList<>();
		equation = equation.replaceAll("\\s*", "");
		for (int i = 0; i < equation.length(); i++) {
			if (equation.charAt(i) >= 'A' && equation.charAt(i) <= 'Z') {
				boolean alreadyExists = false;
				char temp = equation.charAt(i);
				for (char v : equationVariables) {
					if (v == temp) {
						alreadyExists = true;
						temp = v;
					}
				}
				if (!alreadyExists) {
					equationVariables.add(temp);
				}
			} 
		}
		return equationVariables;
	}

	public boolean[][] constructTruthTable(int colmns) {
		int rows = (int) Math.pow(2.0, (double) colmns);
		boolean table[][] = new boolean[rows][colmns];
		int count = 0;
		int repeating = 1;
		for (int i = colmns - 1; i >= 0; i--) {
			count = 0;
			while (count < rows) {
				int repeatingIndex = 0;
				while (repeatingIndex < repeating) {
					table[count][i] = false;
					count++;
					repeatingIndex++;
				}
				repeatingIndex = 0;
				while (repeatingIndex < repeating) {
					table[count][i] = true;
					count++;
					repeatingIndex++;
				}
			}
			repeating *= 2;
		}
		return table;
	}

	public ArrayList<Boolean> getOutput(boolean[][] tb, ArrayList<Character> exp, String equationArray) {
		System.out.println(equationArray);
		ArrayList<Boolean> output = new ArrayList<>();
		java.util.Collections.sort(exp);
		for (int i = 0; i < tb.length; i++) {
			String evalExp = equationArray.toString();
			for (int j = 0; j < tb[0].length; j++) {
				evalExp = evalExp.replaceAll(""+exp.get(j)+"", ""+tb[i][j]+"");
			}
			ScriptEngineManager manager = new ScriptEngineManager();
		    ScriptEngine engine = manager.getEngineByName("js");
		    Object result;
			try {
				result = engine.eval(evalExp);
			    //System.out.println(Boolean.TRUE.equals(result));
				output.add(Boolean.TRUE.equals(result));
			} catch (ScriptException e) {
				System.out.println("Invalid Syntax");
			}
		}
		return output;
	}
	
		//Map<Character, Boolean> varsAndvalues = new HashMap<>();
		//boolean table[][] = constructTruthTable(equationVariables.size());
//		int n = equationVariables.size();
//		int m = (int) Math.pow(2.0, (double) equationVariables.size());
//		String expression = null;
//		boolean result;
//		for (int i = 0; i < m; i++) {
//			ArrayList<Object> temp = new ArrayList<>(equationArray);
//			for (int j = 0; j < n; j++) {
//				varsAndvalues.put(equationVariables.get(j), table[i][j]);
//			}
//			for (int k = 0; k < temp.size(); k++) {
//				if (varsAndvalues.containsKey(temp.get(k))) {
//					expression = expression + String.valueOf(varsAndvalues.get(temp.get(k)));
//				} else {
//					expression = expression + String.valueOf(temp.get(k));
//				}
//			}
//			for (int s = 0; s < expression.length(); s++) {
//				if (expression.charAt(s) == '!' && expression.charAt(s + 1) == '0') {
//					expression = expression.replaceAll("!0", "1");
//				} else if (expression.charAt(s) == '!' && expression.charAt(s + 1) == '1') {
//					expression = expression.replaceAll("!1", "0");
//				}
//			}
//			expression = expression.replaceAll("null", "");
//			//result = h.evaluate(h.infixToPostfix(expression));
//			if (result == false) {
//				output.add(false);
//			} else {
//				output.add(true);
//			}
//			varsAndvalues.clear();
//			expression = null;
//		}
	

	public boolean isTautology(ArrayList<Boolean> output) {
		boolean flag = output.get(0);
		for (int i = 1; i < output.size(); i++) {
			flag = flag & output.get(i);
		}
		if (flag == false) {
			return false;
		}
		return true;
	}

	public boolean isContradiction(ArrayList<Boolean> output) {
		boolean flag = output.get(0);
		for (int i = 1; i < output.size(); i++) {
			flag = flag | output.get(i);
		}
		if (flag == false) {
			return true;
		}
		return false;
	}

	public boolean checkEquality(ArrayList<Boolean> a, ArrayList<Boolean> b) {
		return a.equals(b);
	}

	public void save(boolean[][] table, ArrayList<Boolean> output, ArrayList<Character> variables) throws IOException {
		StringBuffer str = new StringBuffer();
		str.append(variables.get(0));
		for (int k = 1; k < variables.size(); k++) {
			str.append(" | " + variables.get(k));
		}
        out.append("\n");
		out.append(str);
        out.append("\n");
		str = new StringBuffer();

		for (int i = 0; i < table.length; i++) {
			str = new StringBuffer();
			str.append(table[i][0]);
			for (int j = 1; j < table[0].length; j++) {
				str.append(" | "+table[i][j]);
			}
			str.append(" | " + output.get(i));
		    out.append(str);
	        out.append("\n");
		}
		
	}
	public void close () throws IOException {
		out.close();
	}
}
