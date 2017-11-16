import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ParserTest {

    @Test
    public void binaryAddition() throws Exception {
        assertEquals(8, new Parser(new Tokenizer("6 + 2")).expression().evaluate(null));
    }

    @Test
    public void binarySubtraction() throws Exception {
        assertEquals(4, new Parser(new Tokenizer("6 - 2")).expression().evaluate(null));
    }

    @Test
    public void binaryMultiplication() throws Exception {
        assertEquals(12, new Parser(new Tokenizer("6 * 2")).expression().evaluate(null));
    }

    @Test
    public void binaryDivision() throws Exception {
        assertEquals(3, new Parser(new Tokenizer("6 / 2")).expression().evaluate(null));
    }

    @Test
    public void simpleParentheses() throws Exception {
        assertEquals(32, new Parser(new Tokenizer("(6 + 2) * 4")).expression().evaluate(null));
    }

    @Test
    public void allBasicOperations() throws Exception {
        assertEquals(9, new Parser(new Tokenizer("6 + 2 * 3 / 2")).expression().evaluate(null));
    }

    @Test
    public void nestedParentheses() throws Exception {
        assertEquals(112, new Parser(new Tokenizer("((12 + 2) * 4) * 2")).expression().evaluate(null));
    }

    @Test
    public void leadingNegative() throws Exception {
        assertEquals(-4, new Parser(new Tokenizer("-6 + 2")).expression().evaluate(null));
    }

    @Test
    public void basicPower() throws Exception {
        assertEquals(32, new Parser(new Tokenizer("2 ^ 5")).expression().evaluate(null));
        assertEquals(64, new Parser(new Tokenizer("2 ^ 6")).expression().evaluate(null));
        assertEquals(27, new Parser(new Tokenizer("3 ^ 3")).expression().evaluate(null));
    }

    @Test
    public void nestedPowers() throws Exception {
        assertEquals(16, new Parser(new Tokenizer("2 ^ (2 ^ 2)")).expression().evaluate(null));
        assertEquals(65536, new Parser(new Tokenizer("2 ^ (2 ^ (2 ^ 2))")).expression().evaluate(null));
    }

    @Test
    public void otherOperationsInPowers() throws Exception {
        assertEquals(16, new Parser(new Tokenizer("2 ^ (12 - 12 + 2 * 2 / 2 * 4 / 2)")).expression().evaluate(null));
    }

    @Test
    public void simpleVariableTest() throws Exception {
        Map<String, Integer> variableMap = new HashMap<>();
        variableMap.put("x", 1);
        assertEquals(1, new Parser(new Tokenizer("x")).expression().evaluate(variableMap));
    }

    @Test
    public void operationsWithVariable() throws Exception {
        Map<String, Integer> variableMap = new HashMap<>();
        variableMap.put("x", 2);
        assertEquals(11, new Parser(new Tokenizer("2 * x + x + 2 ^ x + x / 2")).expression().evaluate(variableMap));
    }
}