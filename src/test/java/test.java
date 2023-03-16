import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class test {
    public static void main(String[] args){
        Constructor[] Constructor = org.eclipse.jetty.servlet.FilterHolder.class.getConstructors();
        for(Constructor constructor : Constructor){
            if(constructor.toString().contains("javax.servlet.Filter")){
                System.out.println(constructor.toString());
            }
        }
    }
}
