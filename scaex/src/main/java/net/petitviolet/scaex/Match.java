package net.petitviolet.scaex;

import net.petitviolet.scaex.func.Function;

/**
 * <pre>{@code
 Match.<A>x((B) obj)
  .Case(condition1).then(result1)//.eval()
  .Case(condition2).then(result2)//.eval()
  .Case(condition3).then(result3)
  .eval() #=> A result }</pre>
 */
public class Match<A, B> {
    private static final String TAG = Match.class.getSimpleName();
    private boolean mThenCalled = false;
    private boolean mIsTrue = false;
    private boolean mIsDefined = false;
    private final B mTarget;
    private A mResult;

    /**
     * hidden constructor
     * @param target match-case target
     */
    private Match(B target) {
        mTarget = target;
    }

    /**
     * Factory method
     * @param target match-case target
     * @param <A> Result type
     * @param <B> match-case Target type
     * @return Match-Case object
     */
    public static <A, B> Match<A, B> x(B target) {
        return new Match<>(target);
    }

    /**
     * evaluation of this Match-Case expression
     * if Match-Case never matched, returns null
     * @return result of this Match-Case set by `then` method
     */
    public A eval() {
        if (!mThenCalled) {
            throw new UnsupportedOperationException("You must call `Case` and `then` method before `eval`");
        }
        return mIsDefined ? mResult : null;
    }

    /**
     * evaluation of this Match-Case expression
     * if you give default value and Match-Case never matched, returns default value
     * @param defaultValue default value of this Match-Case
     * @return result of this Match-Case set by `then` method or defaultValue
     */
    public A eval(A defaultValue) {
        return mIsDefined ? mResult : defaultValue;
    }

    /**
     * judge given condition is True of False.
     * if true, this Match-Case result will be defined.
     * once defined, cannot change the flag.
     * @param condition this case is satisfied or not
     * @return this
     */
    public Match<A, B> Case(boolean condition) {
        if (!mIsDefined) {
            mIsTrue = condition;
        }
        return this;
    }

    /**
     * judge given value is equals to the target
     * @param condition some value whose Class is correspond to the target
     * @return this
     */
    public Match<A, B> Case(B condition) {
        if (!mIsDefined) {
            return Case(mTarget.equals(condition));
        }
        return this;
    }

    /**
     * judge given class object is correspond to the target
     * @param condition some class object
     * @return this
     */
    public Match<A, B> Case(Class condition) {
        if (!mIsDefined) {
            return Case(mTarget.getClass().equals(condition));
        }
        return this;
    }

    /**
     * give the target to given function and judge its result is true or false
     * @param condition a function get single B class argument and returns Boolean
     * @return this
     */
    public Match<A, B> Case(Function.F1<B, Boolean> condition) {
        if (!mIsDefined) {
            return Case(condition.apply(mTarget));
        }
        return this;
    }

    /**
     * set a result value when returns by this Match-Case only if just before Case condition is true
     * @param result result value
     * @return this
     */
    public Match<A, B> then(A result) {
        mThenCalled = true;
        if (!mIsDefined && mIsTrue) {
            mResult = result;
            mIsDefined = true;
        }
        return this;
    }


    /**
     * set a result function takes no argument
     * @param result result function takes no argument
     * @return this
     */
    public Match<A, B> then(Function.F0<A> result) {
        if (!mIsDefined && mIsTrue) {
            return then(result.apply());
        }
        return this;
    }

    /**
     * set a result function takes the target
     * @param result result function takes the target
     * @return this
     */
    public Match<A, B> then(Function.F1<B, A> result) {
        if (!mIsDefined && mIsTrue) {
            return then(result.apply(mTarget));
        }
        return this;
    }
}