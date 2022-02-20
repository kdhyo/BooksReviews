package args.first;

import java.text.ParseException;

public class Application {

    public static void main(String[] args) {
        if (args == null || args.length == 0) {
            args = new String[]{"-p", "123", "-d", "성공", "-l"};
        }

        try {
            Args arg = new Args("l,p#,d*", args);
            boolean logging = arg.getBoolean('l');
            int port = arg.getInt('p');
            String directory = arg.getString('d');
            executeApplication(logging, port, directory);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static void executeApplication(boolean logging, int port, String directory) {
        System.out.println("logging : " + logging);
        System.out.println("port : " + port);
        System.out.println("directory : " + directory);
    }

}
