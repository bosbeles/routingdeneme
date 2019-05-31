package treetable.test;

public class Balanced {


    public static void main(String[] args) {
        String sample = "(((())))()()(())";
        char[] chars = sample.toCharArray();
        int opening = 0;
        int closing = 0;
        for (int i = 0; i < chars.length; i++) {
            char ch = chars[i];
            if(ch == '(') {
                opening++;
            } else if (ch == ')') {
                closing++;
            }
            if(closing > opening) {
                System.out.println("Unbalanced at " + i);
                break;
            }
        }
        if(opening > closing) {
            System.out.println("Missing closing paranthesises. Opening = " + opening + " Closing = " + closing + " Diff = " + (opening - closing));
        } else {
            System.out.println("Balanced.");
        }
    }
}
