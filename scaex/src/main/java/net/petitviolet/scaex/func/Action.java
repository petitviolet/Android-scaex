package net.petitviolet.scaex.func;

public interface Action {
    interface A1<A> extends Action {
        void run(A a);
    }
    interface A2<A, B> extends Action {
        void run(A a, B b);
    }
    interface A3<A, B, C> extends Action {
        void run(A a, B b, C c);
    }
}


