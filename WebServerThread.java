import java.io.*;
import java.net.*;

public class WebServerThread extends Thread {

    private Socket socket;

    //HTTP status codes
    private final String BAD_REQUEST = "HTTP/1.0 404 Not Found\r\n";
    private final String OK = "HTTP/1.0 200 OK\r\n";

    /**
     * WebServerThread Constructor
     * The constructor instantiates a WebServerThread object every time a connection to a new client is made. It gives the server the feature to listen to multiples clients at the same time.
     * @param socket is the Socket object created at the specified port number in the server which listens to incoming client connections 
     */
    public WebServerThread(Socket socket)
    {
        super("WebServerThread"); 
        this.socket = socket; 
    }
    
    /**
     * run Method
     * It reads the request sent to the server's socket by the client. This request is then parsed to obtain the relevant data such as the document's name whose client wants to access or view. After
     * obtaining the document's name, it searches for such document if the document is found the status code 200 is sent to the client along whith the contents of such document which was
     * read and stored in a String variable called doc. Otherwise, if the document is not found the status code 400 is sent to the client
     */
    public void run()
    {
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));  // reads input from client
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true); //output stream of the server's socket
        
            System.out.println(in.readLine()); // prints connection established
            String line;
        
            while((line = in.readLine()) != null) //reads from user's keyboard until end of input character (CTRL-C)
            {
                String[] str = line.split(" "); //splits string line based on white space 
                String docLine, doc = OK;
                System.out.println("Preparing to send document...");
                try
                {
                    BufferedReader br = new BufferedReader(new FileReader(str[1]));  //element 1 holds the name of the file
                    while((docLine = br.readLine()) != null) //while loop reads the content of the document
                        doc += docLine; //Stores the whole document
                    out.println((doc += "\r\n")); //sends the contents line by line to client
                    System.out.println("Document sent...");
                    br.close();
                }
                catch(IOException e)
                {
                    System.out.println("Document not found or Invalid sintax...");
                    out.println(BAD_REQUEST);
                    System.exit(1);
                }
            } 
            System.out.println("Closing Connection...");
        }
        catch(IOException e)
        {System.out.println(e.getMessage());}

    }
}
