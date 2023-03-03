package Util;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.UnixStyleUsageFormatter;

public class Config {
    @Parameter(names = {"-l", "--list"}, help = true, description = "list VirtualMachine",order = 1)
    public static boolean list_vm = false;

    @Parameter(names = {"-t", "--target"}, help = true, description = "target process name",order = 2)
    public static String targetName = "null";

    @Parameter(names = {"-h", "--help"}, help = true, description = "Show this help",order = 3)
    private static boolean help = false;

    public static String jarName = "MemShell-1.0-SNAPSHOT.jar";
    public static void applyCmdArgs(String[] args) {
        //process cmd args
        JCommander jc = JCommander.newBuilder()
                .addObject(new Config())
                .build();
        try {
            jc.parse(args);
            if(args.length == 0){
                throw new Exception();
            }
        } catch (Exception e) {
            jc.usage();
            System.exit(0);
        }

        jc.setProgramName(jarName);
        jc.setUsageFormatter(new UnixStyleUsageFormatter(jc));

        if(help) {
            jc.usage(); //if -h specified, show help and exit
            System.exit(0);
        }
    }
}
