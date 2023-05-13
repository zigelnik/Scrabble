package model;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Observable;

public class Model extends Observable {
    Socket fg=null;
    PrintWriter out2fg= null;
    HashMap<String, String> properties;
    public Model(String propertiesFileName)
    {
        properties = new HashMap<>();
//        BufferedReader in = null;
//        try {
//            in = new BufferedReader(new FileReader(propertiesFileName));
//            String line;
//
//            while ((line=in.readLine())!=null)
//            {
//                    String sp[] = line.split(",");
//                    properties.put(sp[0],sp[1]);
//            }
//            in.close();
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        int port = Integer.parseInt(properties.get("port"));
//        try {
//                fg = new Socket(properties.get("ip"), port);
//                out2fg = new PrintWriter(fg.getOutputStream());
//        } catch (UnknownHostException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }


}
