package net.petitviolet.scaex;

import net.petitviolet.scaex.func.Action;
import net.petitviolet.scaex.func.Function;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

/**
 * <pre>{@code
 For.<A>x(list).action(item -> { doAction(item); });
 List<B> results = For.<A>x(list).action(item -> { return someFunction(item); });

 For.x(1).to(10).by(1).action(i -> { // action or function like above. }}</pre>
 * @param <A>
 */
public class For<A> {
    private static final String TAG = For.class.getSimpleName();
    private final Iterator<A> mIterator;

    /**
     * hidden constructor. Internal API.
     * @param mIterator
     */
    private For(Iterator<A> mIterator) {
        this.mIterator = mIterator;
    }

    /**
     * constructor with Iterator
     * @param iterator
     * @param <A>
     * @return
     */
    public static <A> For<A> x(Iterator<A> iterator) {
        return new For<>(iterator);
    }

    /**
     * constructor with Iterable. e.g. List.
     * @param iterable
     * @param <A>
     * @return
     */
    public static <A> For<A> x(Iterable<A> iterable) {
        return new For<>(iterable.iterator());
    }

    /**
     * {@code constructor like for(i = 0; i <= n; i++) { // do something. }}
     * @param i
     * @return
     */
    public static ForBuilder x(int i) {
        return new ForBuilder(i);
    }

    /**
     * do something on each item of given Iterable/Iterator
     * can use as Java for-statement.
     * @param action
     */
    public void action(Action.A1<A> action) {
        while (mIterator.hasNext()) {
            A item = mIterator.next();
            action.run(item);
        }
    }

    /**
     * do something and return value using each item of given Iterable/Iterator
     * can use as Scala for-expression.
     * @param function
     * @param <B>
     * @return
     */
    public <B> List<B> apply(Function.F1<A, B> function) {
        List<B> results = new ArrayList<>();
        while (mIterator.hasNext()) {
            A item = mIterator.next();
            results.add(function.apply(item));
        }
        return results;
    }

    /**
     * <pre>{@code
     For.x(int i) creates this Builder class.
     For.x(1).to(10).by(3) //=> 1, 4, 7, 10
     For.x(1).until(10).by(3) //=> 1, 4, 7
     For.x(1).by(2) //=> 1, 3, 5, ..., Integer.MAX_VALUE(2147483647) }</pre>
     */
    public static class ForBuilder {
        // have `long` internal value for suppress Integer Overflow.
        private final long mI;
        private int mLimit = Integer.MAX_VALUE;

        ForBuilder(int i) {
            this.mI = i;
        }

        public ForBuilder to(int to) {
            this.mLimit = to;
            return this;
        }

        public ForBuilder until(int until) {
            this.mLimit = until - 1;
            return this;
        }

        public For<Integer> by(final int step) {
            Iterator<Integer> iterator = new Iterator<Integer>() {
                // have `long` internal value for suppress Integer Overflow.
                private long iCurrent = mI;
                private final int iLimit = mLimit;
                private final int iStep = step;

                @Override
                public boolean hasNext() {
                    return iCurrent <= mLimit && iCurrent <= Integer.MAX_VALUE;
                }

                @Override
                public Integer next() {
                    if (hasNext()) {
                        Integer result = (int) iCurrent;
                        iCurrent += step;
                        return result;
                    }
                    throw new IndexOutOfBoundsException(
                            String.format(Locale.getDefault(),
                                    "current: %d, limit: %d, step: %d", iCurrent, iLimit, iStep));
                }

                @Override
                public void remove() {
                }
            };
            return new For<>(iterator);
        }
    }
}