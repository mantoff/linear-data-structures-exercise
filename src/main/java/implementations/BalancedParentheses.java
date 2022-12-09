package implementations;

import interfaces.Solvable;

public class BalancedParentheses implements Solvable {
    private String parentheses;

    public BalancedParentheses(String parentheses) {
        this.parentheses = parentheses;
    }

    @Override
    public Boolean solve() {
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder reversedSb = new StringBuilder();
        char[] charArr = this.parentheses.toCharArray();
        for (int i = 0; i < charArr.length / 2; i++) {
            stringBuilder.append(charArr[i]);
        }
        for (int i = charArr.length - 1; i >= charArr.length / 2 ; i--) {
            reversedSb.append(charArr[i]);
        }
        reversedSb.reverse();

        return stringBuilder.toString().equals(reversedSb.toString());
    }
}
