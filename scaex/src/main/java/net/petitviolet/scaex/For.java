package net.petitviolet.scaex;

import net.petitviolet.scaex.func.Action;
import net.petitviolet.scaex.func.Function;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class For<A> {
    private static final String TAG = For.class.getSimpleName();
    private Iterator<A> mIterator;

    private For(Iterator<A> mIterator) {
        this.mIterator = mIterator;
    }

    public static <A> For<A> x(Iterator<A> iterator) {
        return new For<>(iterator);
    }

    public static <A> For<A> x(Iterable<A> iterable) {
        return new For<>(iterable.iterator());
    }

    public static ForBuilder x(int i) {
        return new ForBuilder(i);
    }

    public void apply(Action.A1<A> action) {
        while (mIterator.hasNext()) {
            A item = mIterator.next();
            action.run(item);
        }
    }

    public <B> List<B> apply(Function.F1<A, B> function) {
        List<B> results = new ArrayList<>();
        while (mIterator.hasNext()) {
            A item = mIterator.next();
            results.add(function.apply(item));
        }
        return results;
    }

    public static class ForBuilder {
        private final int mI;
        private int mLimit;

        public ForBuilder(int i) {
            this.mI = i;
            this.mLimit = i;
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
                private int iCurrent = 0;
                private final int iLimit = mLimit;
                private final int iStep = step;
                @Override
                public boolean hasNext() {
                    return iCurrent + step <= mLimit;
                }

                @Override
                public Integer next() {
                    if (hasNext()) {
                        iCurrent += step;
                        return iCurrent;
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