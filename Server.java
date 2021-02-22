import java.io.*;
import java.net.*;

public class WebServer 
{
    public static void main(String[] args)
    {
        int portNumber;
        String inputString;
        if(args.length != 1)
            portNumber = 80;    //default socket to listen
        else
            portNumber = Integer.parseInt(args[0]); //socket number to listen provided by terminal/cmd

        try(ServerSocket serverSocket = new ServerSocket(portNumber); //creates a socket to listen at portnumber
            Socket clientSocket = serverSocket.accept();     //accepts connection from client   
              
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));  // reads input from client
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);)
            {
            System.out.println("Connected");     
            String line;
            
            while((line = in.readLine()) != null);
            {
                inputString = line;
                String[] str = inputString.split(" "); //splits string line based on white space
                File file = new File(str[1]); // str1 element has the document's path/name to be opened
            
                BufferedReader br = new BufferedReader(new FileReader(file));
                String docLine;
                while((docLine = br.readLine()) != null)
                    out.println(docLine);
                br.close();
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
