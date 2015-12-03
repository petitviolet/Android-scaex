package net.petitviolet.scaex;

import net.petitviolet.scaex.func.Function;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class MatchTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testOnlyEval() throws Exception {
        thrown.expect(UnsupportedOperationException.class);
        thrown.expectMessage("You must call `Case` and `then` method before `eval`");

        String result = Match.<String, Boolean>x(false).eval();
    }

    @Test
    public void testPrimitiveCase() throws Exception {
        int target = 100;
        String result = Match.<String, Integer>x(target)
                .Case(target == 100).then("nice")
                .eval();
        assert result.equals("nice") == true;
    }

    @Test
    public void testPrimitiveCase2() throws Exception {
        int target = 100;
        String result = Match.<String, Integer>x(target)
                .Case(target < 100).then("down")
                .Case(target > 100).then("up")
                .Case(target == 100).then("draw")
                .eval();
        assert result.equals("draw") == true;
    }

    @Test
    public void testDefaultEval() throws Exception {
        String result = Match.<String, Boolean>x(false).eval("nice");
        assert result.equals("nice") == true;
    }

    @Test
    public void testValueCase() throws Exception {
        String result = Match.<String, Integer>x(100)
                .Case(100).then("nice")
                .eval();
        assert result.equals("nice") == true;
    }

    @Test
    public void testMissValueCase() throws Exception {
        String result = Match.<String, Integer>x(100)
                .Case(999).then("nice")
                .eval();
        assert result == null;
    }

    @Test
    public void testCaseClass() throws Exception {
        String result = Match.<String, Integer>x(100)
                .Case(Integer.class).then("nice")
                .eval();
        assert result.equals("nice") == true;
    }

    @Test
    public void testCaseMissClass() throws Exception {
        String result = Match.<String, Integer>x(100)
                .Case(String.class).then("nice")
                .eval();
        assert result == null;
    }

    @Test
    public void testCaseFunction() throws Exception {
        String result = Match.<String, Integer>x(100)
                .Case(new Function.F1<Integer, Boolean>() {
                    @Override
                    public Boolean apply(Integer integer) {
                        return integer == 100;
                    }
                }).then("nice")
                .eval();
        assert result.equals("nice") == true;
    }

    @Test
    public void testCaseMissFunction() throws Exception {
        String result = Match.<String, Integer>x(100)
                .Case(new Function.F1<Integer, Boolean>() {
                    @Override
                    public Boolean apply(Integer integer) {
                        return integer == 999;
                    }
                }).then("nice")
                .eval();
        assert result == null;
    }

    @Test
    public void testCombinedCaseClass() throws Exception {
        int result = Match.<Integer, Object>x(100)
                .Case(String.class).then(1)
                .Case(Boolean.class).then(2)
                .Case(Integer.class).then(3)
                .eval();
        assert result == 3;
    }

    @Test
    public void testCombinedMultiPatternCase() throws Exception {
        int result = Match.<Integer, Object>x(100)
                .Case(String.class).then(1)
                .Case(Boolean.class).then(2)
                .Case(new Function.F1<Object, Boolean>() {
                    @Override
                    public Boolean apply(Object integer) {
                        return integer.equals(999);
                    }
                }).then(3)
                .Case(new Function.F1<Object, Boolean>() {
                    @Override
                    public Boolean apply(Object integer) {
                        return integer.equals(100);
                    }
                }).then(4)
                .Case(Integer.class).then(5)
                .eval();
        assert result == 4;
    }

    @Test
    public void testThenFunc0() throws Exception {
        String result = Match.<String, Integer>x(100)
                .Case(Integer.class).then(new Function.F0<String>() {
                    @Override
                    public String apply() {
                        return "nice";
                    }
                })
                .eval();
        assert result.equals("nice");
    }

    @Test
    public void testThenFunc1() throws Exception {
        String result = Match.<String, Integer>x(100)
                .Case(Integer.class).then(new Function.F1<Integer, String>() {
                    @Override
                    public String apply(Integer integer) {
                        return integer.toString();
                    }
                })
                .eval();
        assert result.equals("100");
    }
}