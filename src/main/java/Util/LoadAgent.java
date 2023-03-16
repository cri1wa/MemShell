package Util;


import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.sun.tools.attach.VirtualMachineDescriptor;


public class LoadAgent {
    public String id;
    public String path;



    public LoadAgent(String id,String agentPath) {
        this.id = id;
        this.path = agentPath;
    }

    public void loadAgent2JVM(){
        try{
            Class MyVirtualMachine = Class.forName("com.sun.tools.attach.VirtualMachine");
            Class MyVirtualMachineDescriptor = Class.forName("com.sun.tools.attach.VirtualMachineDescriptor");
            List<VirtualMachineDescriptor> listVD = list();
            System.out.println("Running JVM list ...");

            for (int i = 0; i < listVD.size(); i++) {
                Object o = listVD.get(i);
                java.lang.reflect.Method getId = MyVirtualMachineDescriptor.getDeclaredMethod("id", null);
                java.lang.String id = (java.lang.String) getId.invoke(o, null);
                if(id.equals(this.id)) {
                    System.out.println("id >>> " + id);
                    java.lang.reflect.Method attach = MyVirtualMachine.getDeclaredMethod("attach", new Class[]{java.lang.String.class});
                    java.lang.Object vm = attach.invoke(o, new String[]{id});
                    java.lang.reflect.Method loadAgent = MyVirtualMachine.getDeclaredMethod("loadAgent", new Class[]{java.lang.String.class});
                    loadAgent.invoke(vm, new String[]{path});
                    java.lang.reflect.Method detach = MyVirtualMachine.getDeclaredMethod("detach", null);
                    detach.invoke(vm, null);
                    System.out.println("Hack Success!");
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List list(){
        List<VirtualMachineDescriptor> listVD = new ArrayList<VirtualMachineDescriptor>();
        try {
            Class MyVirtualMachine = Class.forName("com.sun.tools.attach.VirtualMachine");
            Method list = MyVirtualMachine.getDeclaredMethod("list");
            listVD = (List<VirtualMachineDescriptor>) list.invoke(MyVirtualMachine,null);
        }catch (Exception e){
            e.printStackTrace();
        }
        return listVD;
    }
}
