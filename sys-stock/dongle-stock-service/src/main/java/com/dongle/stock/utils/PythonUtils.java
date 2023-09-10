package com.dongle.stock.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class PythonUtils {

    public static final String STOCK_GRAB_PYTHON = "D:\\Project\\Source\\Github\\dongle\\dongle-project\\sys-stock\\grab-stock-python\\stock.py";

    /**
     * 执行python文件
     * @param file
     * @return
     */
    public static boolean execPython(String file){
        if (file == null) throw new RuntimeException("python file not is empty!");
        String[] args = new String[]{"python",file};
        return execPython(args);
    }

    public static boolean execPython(String[] args){
        if (args == null) throw new RuntimeException("exec python invalid!");
        if (!args[0].equals("python")){
            String[] tmp = new String[args.length + 1];
            tmp[0] = "python";
            System.arraycopy(args, 0, tmp, 1, args.length);
            args=tmp;
        }
        if (args.length <2) throw new RuntimeException("exec python loss params!");
        Process process;
        BufferedReader in = null;
        String line = null;
        try{
            process = Runtime.getRuntime().exec(args);
            in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            process.waitFor();
            System.out.println("exec python successful!");
            return true;
        }catch (Exception exception){
            System.out.println("exec python is error!");
            exception.printStackTrace();
        }finally {
            if (in!=null) try{in.close();}catch (Exception ignore){}
        }
        return false;
    }

    public static void main(String[] args) {
        execPython(new String[]{STOCK_GRAB_PYTHON,"method=new","code=sz.002236","day=2023-06-06"});
    }
}
