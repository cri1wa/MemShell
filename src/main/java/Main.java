import Util.LoadAgent;
import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.UnixStyleUsageFormatter;
import java.util.List;
import Util.Config;
public class Main {

    public static void main(String[] args){
        Config.applyCmdArgs(args);
        if(Config.list_vm){
            list();
        }else if(!Config.targetName.equals("null")){
            loadAgent();
        }
    }

    public static List list(){
        Class/*<?>*/ MyVirtualMachine = VirtualMachine.class.getClass();
        Class/*<?>*/ MyVirtualMachineDescriptor = VirtualMachineDescriptor.class.getClass();
        List<VirtualMachineDescriptor> list = VirtualMachine.list();
        return list;
    }

    public static void loadAgent(){
        LoadAgent la = new LoadAgent(Config.targetName,Config.jarName);
        la.loadAgent2JVM();
    }
}
