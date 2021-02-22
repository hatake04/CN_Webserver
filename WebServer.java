import java.io.*;
import java.net.*;

public class WebServer 
{
    public static void main(String[] args)
    {
        int portNumber;
        if(args.length != 1)
            portNumber = 80;    //default socket to listen
        else
            portNumber = Integer.parseInt(args[0]); //socket number to listen provided by terminal/cmd

        try(ServerSocket serverSocket = new ServerSocket(portNumber); //creates a socket to listen at portnumber
            Socket clientSocket = serverSocket.accept();     //accepts connection from client   
              
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));  // reads input from client
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);)
        {
                out.println("Connection established..."); 
                String line;

                while((line = in.readLine()) != null); //reads from user's keyboard until end of input character (CTRL-C)
                {
                    String[] str = line.split(" "); //splits string line based on white space --bug in this line--
                    BufferedReader br = new BufferedReader(new FileReader(str[1]));  //element 1 holds the name of the file
                    String docLine;
                    while((docLine = br.readLine()) != null) //while loop reads the content of the document
                        out.println(docLine); //sends the contents line by line to client
                }
        } 
        catch(IOException e)
        {
            System.out.println("Exception caught when trying to listen on port " + portNumber
            + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}
