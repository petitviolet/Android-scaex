package net.petitviolet.scaex;

import net.petitviolet.scaex.func.Function;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class IFTest {
    @Test
    public void ifxTestFail() throws Exception {
        String result = IF.<String>x(false).then("hoge")
                .ElseIf(false).then("foo").eval();

        assert result == null;
    }
    @Test
    public void ifxTestEval1() throws Exception {
        String result = IF.<String>x(true).then("hoge")
                .ElseIf(false).then("foo").eval();

        assert result == "hoge";
    }

    @Test
    public void ifxTestEval2() throws Exception {
        String result = IF.<String>x(false).then("hoge")
                .ElseIf(true).then("foo").eval();

        assert result == "foo";
    }
    @Test
    public void ifxTest1() throws Exception {
        String result = IF.<String>x(true).then("hoge")
                .ElseIf(false).then("foo")
                .Else("bar");

        assert result == "hoge";
    }

    @Test
    public void ifxTest2() throws Exception {
        String result = IF.<String>x(false).then("hoge")
                .ElseIf(true).then("foo")
                .Else("bar");

        assert result == "foo";
    }

    @Test
    public void ifxTest3() throws Exception {
        String result = IF.<String>x(false).then("hoge")
                .ElseIf(false).then("foo")
                .Else("bar");

        assert result == "bar";
    }

    @Test
    public void ifxTest1x() throws Exception {
        String result = IF.<String>x(true).then(new Function.F0<String>() {
            @Override
            public String apply() {
                return "hoge";
            }
        }).ElseIf(false).then(new Function.F0<String>() {
            @Override
            public String apply() {
                return "foo";
            }
        }).Else(new Function.F0<String>() {
            @Override
            public String apply() {
                return "bar";
            }
        });

        assert result == "hoge";
    }

    @Test
    public void ifxTest2x() throws Exception {
        String result = IF.<String>x(false).then(new Function.F0<String>() {
            @Override
            public String apply() {
                return "hoge";
            }
        }).ElseIf(true).then(new Function.F0<String>() {
            @Override
            public String apply() {
                return "foo";
            }
        }).Else(new Function.F0<String>() {
            @Override
            public String apply() {
                return "bar";
            }
        });

        assert result == "foo";
    }

    @Test
    public void ifxTest3x() throws Exception {
        String result = IF.<String>x(false).then(new Function.F0<String>() {
            @Override
            public String apply() {
                return "hoge";
            }
        }).ElseIf(false).then(new Function.F0<String>() {
            @Override
            public String apply() {
                return "foo";
            }
        }).Else(new Function.F0<String>() {
            @Override
            public String apply() {
                return "bar";
            }
        });
        assert result == "bar";
    }
}