import View.StartScreen;

import javax.swing.*;
import java.io.*;
import java.net.Socket;


/**
 * Created by Michael on 2016-11-15W.
 * <p>
 * Note: As I did not know how to implement a hexagon
 * coordinate system I followed the guide provided at:
 * http://www.redblobgames.com/grids/hexagons/implementation.html
 */
class Main {

    public static void main(String[] args) {


        try {
            network2();

        } catch (NullPointerException e) {
            System.out.println("Server Error: " + e);
        }

        SwingUtilities.invokeLater(Main::createAndShowGUI);
        //SwingUtilities.invokeLater(Main::test);
    }


    /**
     * TODO:
     * Currently network2 connect to the server (when its active) and downloads all available JSON files and
     * place them into a txt file called serverDump. Currently that is all that occurs with the server. In the
     * future the file will be parsed through to get all individual JSON robots, and updates to the server will
     * be pushed at the end of the game.
     */

    /**
     * Server Protocal for getting JSON files from the server.
     * Dependant on if the server is up or not
     */
    private static void network2() throws NullPointerException {
        Socket MyClient = null;
        DataOutputStream os = null;
        DataInputStream is = null;
        String responseLine;
        String json = "{ \"list-request\" :\n" +
                "\t{ \"data\" : \"full\"\n" +
                "\t}\n" +
                "}\n";


        try {
            MyClient = new Socket("gpu0.usask.ca", 20001);
            System.out.println("Connection: " + MyClient.isConnected());
        } catch (IOException e) {
            System.out.println(e);
        }
        BufferedReader br = null;
        try {
            os = new DataOutputStream(MyClient.getOutputStream());  //output stream
            is = new DataInputStream(MyClient.getInputStream());    //input stream
            InputStreamReader ir = new InputStreamReader(is);
            br = new BufferedReader(ir);
        } catch (IOException e) {
            System.out.println(e);
        }

        if (MyClient != null && os != null && is != null) {
            try {
                os.writeBytes(json);
            } catch (IOException e) {
                e.printStackTrace();
            }
            PrintWriter writer = null;
            try {
                writer = new PrintWriter("serverDump.txt", "UTF-8");
                while ((responseLine = br.readLine()) != null) {

                    writer.println(responseLine);
                    writer.println("The second line");


                    System.out.println("Server: " + responseLine);
                    if (responseLine.indexOf("Ok") != -1) {


                    }
                }
            } catch (IOException e) {

                e.printStackTrace();
            }
            writer.close();
            try {
                MyClient.close();   //We need this or we are going to swamp the server
                os.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private static void network() {
        String hostName = "gpu0.usask.ca";
        int portNumber = 20001;

        try {
            Socket echoSocket = new Socket(hostName, portNumber);
            PrintWriter out =
                    new PrintWriter(echoSocket.getOutputStream(), true);
            BufferedReader in =
                    new BufferedReader(
                            new InputStreamReader(echoSocket.getInputStream()));
            BufferedReader stdIn =
                    new BufferedReader(
                            new InputStreamReader(System.in));
            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput);
                System.out.println("Server says: " + in.readLine());
            }
            System.out.println("Successfully connected to the server!");
        } catch (Exception e) {
            System.err.println("Connection FAILED");
        }

    }


    /**
     * Creates the start screen
     */
    private static void createAndShowGUI() {

        // Instantiate the start screen
        JFrame frame = new JFrame("StartScreen");
        frame.setContentPane(new StartScreen(frame).start);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private static void test() {
        new Test();
    }


}
