import Util.LoadAgent;
import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

import java.util.ArrayList;
import java.util.List;
import Util.Config;
public class Main {

    public static void main(String[] args){
        Config.applyCmdArgs(args);
        if(Config.list_vm){
            List<VirtualMachineDescriptor> vmList = list();
            for(VirtualMachineDescriptor vmName : vmList){
                System.out.println(vmName.toString());
            }
        }else if(!Config.targetName.equals("null")){
            loadAgent();
        }
    }

    public static List list(){
        List<VirtualMachineDescriptor> list = new ArrayList<VirtualMachineDescriptor>();
        try {
            list = VirtualMachine.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public static void loadAgent(){
        LoadAgent la = new LoadAgent(Config.targetName,Config.jarName);
        la.loadAgent2JVM();
    }
}
