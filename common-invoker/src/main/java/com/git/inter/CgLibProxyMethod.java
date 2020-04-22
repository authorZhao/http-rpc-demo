package com.git.inter;

/**
 * 四元函数
 * @author admin
 * @date 2019-12-31
 * @param <A>
 * @param <B>
 * @param <C>
 * @param <D>
 * @param <E>
 */
@FunctionalInterface
public interface CgLibProxyMethod<A,B,C,D,E> extends ProxyMethod {
    E apply(A a, B b, C c, D d);

    @Override
    default Object apply(Object o, Object o2, Object o3) {
        return apply((A)o,(B)o2,(C)o3,null);
    }
}
