import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    static ArrayList<Character> nonTerminators=new ArrayList<>(Arrays.asList('E','T','F'));
    static ArrayList<Character> Terminators=new ArrayList<>(Arrays.asList('+','-','*','/','n','(',')','$'));
    static ArrayList<String> Exp=new ArrayList<>(Arrays.asList("S→E","E→E+T","E→E-T","E→T","T→T*F","T→T/F","T→F","F→(E)","F→n"));
    static String[][] Action=new String[16][8];
    static int[][] Goto=new int[16][3];
    static Stack<Character> buffer=new Stack<>();
    static Stack<Character> stack=new Stack<>();
    static Stack<Integer> states=new Stack<>();
    public static void main(String[] args) {
        SetAction();
        SetGoto();
        DealInput();
        DealPredict();
    }
    public static void DealPredict(){
        int count=1;
        stack.push('$');
        states.push(0);
        while (true){
            int i=states.peek();
            int j=Terminators.indexOf(buffer.peek());
            String s=Action[i][j];
            if(s.contains("S")){
                states.push(Integer.parseInt(s.substring(1)));
                stack.push(buffer.pop());
            }else if(s.contains("R")){
                int n=Integer.parseInt(s.substring(1))-1;
                String exp= Exp.get(n);
                System.out.println(count+". "+exp);
                popStack(exp.length()-2);
                stack.push(exp.charAt(0));
                states.push(Goto[states.peek()][nonTerminators.indexOf(stack.peek())]);
                count++;
            }else if(s.equals("acc")){
                System.out.println(count+". "+Exp.get(0));
                System.out.println("success analyse");
                break;
            }
            else {
                System.out.println("wrong word");
                break;
            }

        }
    }
    static void popStack(int n){
        for(int i=0;i<n;i++){
            states.pop();
            stack.pop();
        }
    }
    public static void pushStringIntoStack(String s){
        for (int i=s.length()-1;i>=0;i--)stack.push(s.charAt(i));
    }
    public static void DealInput(){
        System.out.println("please input the string to be analyse");
        Scanner cin=new Scanner(System.in);
        String s=cin.nextLine();
        Pattern pattern=Pattern.compile("\\d+\\.*\\d*");
        Matcher matcher=pattern.matcher(s);
        String n=matcher.replaceAll("n");
        buffer.push('$');
        for(int i=n.length()-1;i>=0;i--)buffer.push(n.charAt(i));
    }
    public static void SetAction(){
        Action[0]= new String[]{null, null, null, null, "S4", "S5", null, null};
        Action[1]=new String[]{"S6","S7",null,null,null,null,null,"acc"};
        Action[2]=new String[]{"R4","R4","S8","S9",null,null,"R4","R4"};
        Action[3]=new String[]{"R7","R7","R7","R7",null,null,"R7","R7"};
        Action[4]=new String[]{"R9","R9","R9","R9",null,null,"R9","R9"};
        Action[5]=new String[]{null,null,null,null,"S4","S5",null,null};
        Action[6]=new String[]{null,null,null,null,"S4","S5",null,null};
        Action[7]=new String[]{null,null,null,null,"S4","S5",null,null};
        Action[8]=new String[]{null,null,null,null,"S4","S5",null,null};
        Action[9]=new String[]{null,null,null,null,"S4","S5",null,null};
        Action[10]=new String[]{"S6","S7",null,null,null,null,"S15",null};
        Action[11]=new String[]{"R2","R2","S8","S9",null,null,"R2","R2"};
        Action[12]=new String[]{"R3","R3","S8","S9",null,null,"R3","R3"};
        Action[13]=new String[]{"R5","R5","R5","R5",null,null,"R5","R5"};
        Action[14]=new String[]{"R6","R6","R6","R6",null,null,"R6","R6"};
        Action[15]=new String[]{"R8","R8","R8","R8",null,null,"R8","R8"};
    }
    public static void SetGoto(){
        Goto[0]=new int[]{1,2,3};
        Goto[5]=new int[]{10,2,3};
        Goto[6]=new int[]{0,11,3};
        Goto[7]=new int[]{0,12,3};
        Goto[8]=new int[]{0,0,13};
        Goto[9]=new int[]{0,0,14};
    }

}