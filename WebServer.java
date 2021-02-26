import java.io.*;
import java.net.*;

public class WebServer 
{
    public static void main(String[] args)
    {
        int portNumber; //port number where server will listen for client requests
        
        //System.out.println("Working Directory = " + System.getProperty("user.dir")); prints your current path directory (put document here)

        if(args.length != 1)
            portNumber = 80;    //default port number to listen if client doesn't give one
        else
            portNumber = Integer.parseInt(args[0]); //port number to listen provided int the terminal/cmd 

            //creates a socket to listen at port number
        try(ServerSocket serverSocket = new ServerSocket(portNumber);) 
        {
            //infinite loop to listen for incoming client requests. it accepts multiples connections by creating the necessary threads
            while(true)
                new WebServerThread(serverSocket.accept()).start(); //the statement is not executed until a connection is made with a client
        }
        catch(IOException e)
        {
            System.out.println("Exception caught when trying to listen on port " + portNumber
            + " or listening for a connection");
        }
    }
}
