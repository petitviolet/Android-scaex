package net.petitviolet.scaex;

import net.petitviolet.scaex.func.Function;

/**
 * Match.<A>x((B) obj)
 * .Case(condition1).then(result1)//.eval()
 * .Case(condition2).then(result2)//.eval()
 * .Case(condition3).then(result3)
 * .eval() #=> A result
 */
public class Match<A, B> {
    private static final String TAG = Match.class.getSimpleName();
    private boolean mThenCalled = false;
    private boolean mIsTrue = false;
    private boolean mIsDefined = false;
    private final B mTarget;
    private A mResult;

    private Match(B target) {
        mTarget = target;
    }

    public static <A, B> Match<A, B> x(B target) {
        return new Match<>(target);
    }

    public A eval() {
        if (!mThenCalled) {
            throw new UnsupportedOperationException("You must call `Case` and `then` method before `eval`");
        }
        return mIsDefined ? mResult : null;
    }

    public A eval(A defaultValue) {
        return mIsDefined ? mResult : defaultValue;
    }

    private Match<A, B> Case(boolean condition) {
        if (!mIsDefined) {
            mIsTrue = condition;
        }
        return this;
    }

    public Match<A, B> Case(B condition) {
        if (!mIsDefined) {
            return Case(mTarget.equals(condition));
        }
        return this;
    }

    public <C> Match<A, B> Case(Class condition) {
        if (!mIsDefined) {
            return Case(mTarget.getClass().equals(condition));
        }
        return this;
    }

    public Match<A, B> Case(Function.F1<B, Boolean> condition) {
        if (!mIsDefined) {
            return Case(condition.apply(mTarget));
        }
        return this;
    }

    public Match<A, B> then(A result) {
        mThenCalled = true;
        if (!mIsDefined && mIsTrue) {
            mResult = result;
            mIsDefined = true;
        }
        return this;
    }

    public Match<A, B> then(Function.F0<A> result) {
        if (!mIsDefined && mIsTrue) {
            return then(result.apply());
        }
        return this;
    }

    public Match<A, B> then(Function.F1<B, A> result) {
        if (!mIsDefined && mIsTrue) {
            return then(result.apply(mTarget));
        }
        return this;
    }
}