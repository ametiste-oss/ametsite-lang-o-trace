package org.amtetiste.util.object.trace;

import org.aopalliance.intercept.MethodInterceptor;
import org.objenesis.ObjenesisStd;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.target.EmptyTargetSource;
import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.Factory;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.List;

class ScanInterceptor implements MethodInterceptor,
        org.springframework.cglib.proxy.MethodInterceptor {

    private final Class<?> targetType;

    private final List<MethodCallListener> listeners;

    private final ScanDepthStrategy scanDepthStrategy;

    private final static ObjenesisStd OBJENESIS = new ObjenesisStd();

    public ScanInterceptor(Class<?> targetType, List<MethodCallListener> listeners,
                           ScanDepthStrategy scanDepthStrategy) {
        this.targetType = targetType;
        this.listeners = listeners;
        this.scanDepthStrategy = scanDepthStrategy;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) {

        if (!targetType.equals(method.getDeclaringClass()) && !Object.class.equals(method.getDeclaringClass())) {
            throw new IllegalStateException("Out of trace method: " +
                    method.getDeclaringClass().getName() + "#" + method.getName()
                    + ". Please use only traced class methods.");
        }

        listeners.forEach(
                (l) -> l.methodCalled(new MethodCallEvent(method, args))
        );

        if (Object.class.equals(method.getDeclaringClass())) {
            return ReflectionUtils.invokeMethod(method, obj, args);
        }

        return scanDepthStrategy.deeperScanFor(method, method.getReturnType());

    }

    @Override
    public Object invoke(org.aopalliance.intercept.MethodInvocation invocation) throws Throwable {
        return intercept(invocation.getThis(), invocation.getMethod(), invocation.getArguments(), null);
    }

    public static <T> T createProxy(Class<T> type, List<MethodCallListener> listeners,
                                    ScanDepthStrategy scanDepthStrategy) {

        final ScanInterceptor scanInterceptor = new ScanInterceptor(type, listeners, scanDepthStrategy);

        if (type.isInterface()) {
            ProxyFactory factory = new ProxyFactory(EmptyTargetSource.INSTANCE);
            factory.addInterface(type);
            factory.addAdvice(scanInterceptor);
            return (T) factory.getProxy();
        }

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(type);
        enhancer.setCallbackType(org.springframework.cglib.proxy.MethodInterceptor.class);
        enhancer.setClassLoader(type.getClassLoader());

        Factory factory = (Factory) OBJENESIS.newInstance(enhancer.createClass());
        factory.setCallbacks(new Callback[]{
                scanInterceptor
        });

        return (T) factory;
    }

}