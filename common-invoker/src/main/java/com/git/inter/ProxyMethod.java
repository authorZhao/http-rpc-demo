package com.git.inter;

/**
 * 三元函数,默认是jdk的代理方法
 * @author admin
 * @param <A>
 * @param <B>
 * @param <C>
 * @param <D>
 */
@FunctionalInterface
public interface ProxyMethod<A,B,C,D> {
    /**
     *
     * @param a
     * @param b
     * @param c
     * @return
     */
    D apply(A a, B b, C c);
}
