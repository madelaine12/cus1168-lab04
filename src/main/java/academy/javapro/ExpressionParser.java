package academy.javapro;

class ExpressionParser {
    private final String input;
    private int position;

    public ExpressionParser(String input) {
        this.input = input;
        this.position = 0;
    }

    // expr → expr + term
    public double parseExpression() {
        double result = parseTerm();
        while (position < input.length() && input.charAt(position) == '+') {
            position++; // Skip '+'
            result += parseTerm();
        }
        return result;
    }

    // term → term * factor
    private double parseTerm() {
        double result = parseFactor();
        while (position < input.length() && input.charAt(position) == '*') {
            position++; // Skip '*'
            result *= parseFactor();
        }
        return result;
    }

    // factor → ( expr )
    private double parseFactor() {
        if (position < input.length() && input.charAt(position) == '(') {
            position++; // Skip '('
            double result = parseExpression();
            if (position < input.length() && input.charAt(position) == ')') {
                position++; // Skip ')'
            } else {
                throw new IllegalArgumentException("Mismatched parentheses at position " + position);
            }
            return result;
        }
        return parseNumber();
    }

    // Parse a numeric value
    private double parseNumber() {
        StringBuilder sb = new StringBuilder();
        while (position < input.length() && (Character.isDigit(input.charAt(position)) || input.charAt(position) == '.')) {
            sb.append(input.charAt(position));
            position++;
        }
        if (sb.length() == 0) {
            throw new IllegalArgumentException("Unexpected character at position " + position);
        }
        return Double.parseDouble(sb.toString());
    }

    public static void main(String[] args) {
        // Test cases
        String[] testCases = {
                "2 + 3 * (4 + 5)",    // Complex expression with parentheses
                "2 + 3 * 4",          // Basic arithmetic with precedence
                "(2 + 3) * 4",        // Parentheses changing precedence
                "2 * (3 + 4) * (5 + 6)", // Multiple parentheses
                "1.5 + 2.5 * 3"       // Decimal numbers
        };

        // Process each test case
        for (String expression : testCases) {
            System.out.println("\nTest Case: " + expression);
            try {
                ExpressionParser parser = new ExpressionParser(expression.replaceAll("\\s+", ""));
                double result = parser.parseExpression();
                System.out.println("Result: " + result);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}