package net.petitviolet.scaex;

import net.petitviolet.scaex.func.Function;

/**
 * <pre>{@code
 A result = IF.x(condition).then(x)//.eval()
  .ElseIf(condition2).then(y)//.eval()
  .else(z)}</pre>
 * @param <A>
 */
public class IF<A> {
    private static final String TAG = IF.class.getSimpleName();
    private boolean mIsTrue;
    private boolean mIsDefined = false;
    private A mValue;

    @Override
    public String toString() {
        return "IF{" +
                "mIsTrue=" + mIsTrue +
                ", mIsDefined=" + mIsDefined +
                ", mValue=" + mValue +
                '}';
    }

    private IF(boolean isTrue) {
        mIsTrue = isTrue;
    }

    public static <A> IF<A> x(boolean isTrue) {
        return new IF<>(isTrue);
    }

    public IF<A> then(A result) {
        if (!mIsDefined && mIsTrue) {
            mValue = result;
            mIsDefined = true;
        }
        return this;
    }

    public IF<A> then(Function.F0<A> result) {
        if (!mIsDefined && mIsTrue) {
            mValue = result.apply();
            mIsDefined = true;
        }
        return this;
    }

    public A eval() {
        return mIsDefined && mIsTrue ? mValue : null;
    }

    public IF<A> ElseIf(Boolean isTrue) {
        if (!mIsDefined) {
            mIsTrue = isTrue;
        }
        return this;
    }

    public IF<A> ElseIf(Function.F0<Boolean> condition) {
        if (!mIsDefined) {
            return ElseIf(condition.apply());
        }
        return this;
    }

    public A Else(A result) {
        return mIsDefined ? mValue : result;
    }
    public A Else(Function.F0<A> result) {
        return mIsDefined ? mValue : result.apply();
    }
}
