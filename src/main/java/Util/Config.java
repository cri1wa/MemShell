package Util;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.UnixStyleUsageFormatter;

import java.io.File;

public class Config {
    @Parameter(names = {"-l", "--list"}, help = true, description = "output jvm process",order = 1)
    public static boolean list_vm = false;

    @Parameter(names = {"-t", "--targetId"}, help = true, description = "target process id",order = 2)
    public static String targetID = "null";

    @Parameter(names = {"-h", "--help"}, help = true, description = "Show this help",order = 3)
    private static boolean help = false;

    //public static String jarName = System.getProperty("java.class.path");
    public static String jarName = System.getProperty("user.dir")+ File.separator+"MemShell-1.0-SNAPSHOT-jar-with-dependencies.jar";
    public static void applyCmdArgs(String[] args) {
        //process cmd args
        JCommander jc = JCommander.newBuilder()
                .addObject(new Config())
                .build();

        jc.setProgramName(jarName);
        jc.setUsageFormatter(new UnixStyleUsageFormatter(jc));

        try {
            jc.parse(args);
            if(args.length == 0){
                throw new Exception();
            }
        } catch (Exception e) {
            jc.usage();
            System.exit(0);
        }


        if(help) {
            jc.usage(); //if -h specified, show help and exit
            System.exit(0);
        }
    }
}
