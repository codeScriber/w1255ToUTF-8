package org.codeScriber;

import java.io.*;
import java.nio.charset.Charset;

public class Main {

    public static void main(String[] args) {
        if( args.length < 1){
            System.err.println("prog  [text file to be converted]");
        }else{
            File textFile = new File(args[0]);
            if( ! textFile.exists() ){
                System.err.println(args[1] + " does not exist, cannot read it!");
            }else if( ! textFile.canRead() ){
                System.err.println(args[1] + " is not readable. Abort!");
            }else{
                try {
                    FileInputStream in = new FileInputStream(textFile);
                    long size = textFile.length();
                    byte[] buffer = new byte[(int)size];
                    in.read(buffer);
                    in.close();
                    String internal = new String(buffer, Charset.forName("cp1255"));
                    byte[] utf8 = internal.getBytes(Charset.forName("utf-8"));
                    FileOutputStream out = new FileOutputStream(getOutputFile(textFile));
                    out.write(utf8);
                    out.flush();
                    out.close();
                }catch(FileNotFoundException ignore){

                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static File getOutputFile(File in){
        String name = in.getName();
        String[] arr = name.split("\\.");
        String resultName = "";
        String suffix = "";
        if( arr.length > 1 ){
            StringBuilder builder = new StringBuilder(name.length());
            for(int i = 0; i < arr.length - 1; i++ ){
                builder.append(arr[i]).append(".");
            }
            resultName = builder.toString();
            suffix = arr[arr.length - 1];
        }else{
            resultName = arr[0] + ".";
        }
        return new File(in.getParentFile(), resultName + "utf8." + suffix);
    }
}
