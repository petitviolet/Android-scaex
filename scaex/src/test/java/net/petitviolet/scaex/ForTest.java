package net.petitviolet.scaex;

import android.util.Log;

import net.petitviolet.scaex.func.Action;
import net.petitviolet.scaex.func.Function;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ForTest {
    @Test
    public void forTestMap() throws Exception {
        List<String> result = For.x(Arrays.asList("a", "b", "c")).apply(new Function.F1<String, String>() {
            @Override
            public String apply(String s) {
                return "x" + s;
            }
        });

        assert result.size() == 3;
        assert result.get(0).equals("xa");
        assert result.get(1).equals("xb");
        assert result.get(2).equals("xc");
    }

    @Test
    public void forTestForeach() throws Exception {
        List<Integer> inputs = Arrays.asList(1, 2, 3);
        final List<Integer> result = new ArrayList<>();
        For.x(inputs).action(new Action.A1<Integer>() {
            @Override
            public void run(Integer integer) {
                result.add(integer * 2);
            }
        });

        assert result.size() == 3;
        assert result.get(0) == 2;
        assert result.get(1) == 4;
        assert result.get(2) == 6;
    }


    @Test
    public void forTestStepTo() throws Exception {
        List<Integer> result = For.x(0).to(4).by(2).apply(new Function.F1<Integer, Integer>() {
            @Override
            public Integer apply(Integer integer) {
                return integer;
            }
        });

        assert result.size() == 3;
        assert result.get(0) == 0;
        assert result.get(1) == 2;
        assert result.get(2) == 4;
    }

    @Test
    public void forTestStepUntil() throws Exception {
        List<Integer> result = For.x(0).until(4).by(2).apply(new Function.F1<Integer, Integer>() {
            @Override
            public Integer apply(Integer integer) {
                return integer;
            }
        });

        assert result.size() == 2;
        assert result.get(0) == 0;
        assert result.get(1) == 2;
    }
    @Test
    public void forTestStepMax() throws Exception {
        // Integer.MAX_VALUE is odd.
        int step = (Integer.MAX_VALUE - 1) / 2;

        List<Integer> result = For.x(0).by(step).apply(new Function.F1<Integer, Integer>() {
            @Override
            public Integer apply(Integer integer) {
                return integer;
            }
        });

        assert result.size() == 3;
        assert result.get(0) == 0;
        assert result.get(1) == step;
        assert result.get(2) == Integer.MAX_VALUE - 1;
    }
}