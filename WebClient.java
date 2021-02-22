import java.io.*;
import java.net.*;

public class WebClient {
    
    public static void main(String args[]) throws IOException
    {
        if(args.length != 2)
        {
            System.err.println("Use <host name>  <port number> as arguments");
            System.exit(1);
        }
        //Hostname and portnumber from application argument
        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        try(Socket clientSocket = new Socket(hostName, portNumber); //creates socket with hostname and portnumber provided
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));//reads from keyboard client
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));)
            System.out.println(in.readLine());
        {
            String userInput;
            while((userInput = stdIn.readLine()) != null)
            {
                String[] str = userInput.split(" ");

                if(str[0].equals("GET")){
                    out.println(userInput); // sends input to server
                    System.out.println(in.readLine()); //Server response back
                }
                    
                else{
                    System.err.println("HTTP 'GET' request method not provided");
                    System.exit(1);
                }
            }
        }
        catch(UnknownHostException e){
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostName);
            System.exit(1);
        }
    }
}
