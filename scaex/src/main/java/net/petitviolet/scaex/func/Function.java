package net.petitviolet.scaex.func;

public interface Function {
    interface F<A> extends Function {
        void apply(A a);
    }

    interface F0<A> extends Function {
        A apply();
    }

    interface F1<A, B> extends Function {
        B apply(A a);
    }

    interface F2<A, B, C> extends Function {
        C apply(A a, B b);
    }
}


