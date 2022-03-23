package telran.net;
//IlyaL 
import java.io.*;
import java.net.*;

import telran.net.dto.*;
public class TcpClientServer implements Runnable {
Socket socket;
ObjectInputStream reader;
ObjectOutputStream writer;
ApplProtocol protocol;
boolean shutdown = false;
int clientId;

public TcpClientServer(Socket socket, ApplProtocol protocol) throws Exception{
	this.socket = socket;
	
	//socket.setSoTimeout(1000); //if socket is in the idle mode after 1 sec.
	//there will be SocketTimeoutException
	reader = new ObjectInputStream(socket.getInputStream());
	writer = new ObjectOutputStream(socket.getOutputStream());
	this.protocol = protocol;
}
	@Override
	public void run() {
		try {
			while(true) {
				Request request = (Request) reader.readObject();
				Response response = protocol.getResponse(request);
				writer.writeObject(response);
			}
		} catch(EOFException e) {
			try {
				socket.close();
			} catch (IOException e1) {
				
				e1.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public void shutdown() {
		shutdown = true;
	}

}
