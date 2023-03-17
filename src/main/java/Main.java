import Util.LoadAgent;
import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import Util.Config;
public class Main {

    public static void main(String[] args){
        Config.applyCmdArgs(args);
        if(Config.list_vm){
            List<VirtualMachineDescriptor> vmList = LoadAgent.list();
            for(VirtualMachineDescriptor vmName : vmList){
                System.out.println(vmName.toString());
            }
        }else if(!Config.targetID.equals("null")){
            loadAgent();
        }
        System.out.println(Config.jarName);
    }

    public static void loadAgent(){
        LoadAgent la = new LoadAgent(Config.targetID,Config.jarName);
        la.loadAgent2JVM();
    }
}
