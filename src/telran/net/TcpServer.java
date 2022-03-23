package telran.net;
//IlyaL 
import java.io.IOException;
import java.net.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class TcpServer implements Runnable {
private static final int DEFAULT_THREADS_POOL_CAPACITY = 3;
private int port;
private ApplProtocol protocol;
private ServerSocket serverSocket;
private ExecutorService executor;
private boolean shutdown = false;
private List<TcpClientServer> clientProxies = new LinkedList<>();
private int clientId=1;



public TcpServer(int port, ApplProtocol protocol, int nThreads) throws Exception{
	this.port = port;
	this.protocol = protocol;
	serverSocket = new ServerSocket(port);
	executor = Executors.newFixedThreadPool(nThreads);
	
}
public TcpServer(int port, ApplProtocol protocol) throws Exception{
	this(port, protocol, DEFAULT_THREADS_POOL_CAPACITY);
	
}
	@Override
	public void run() {
		System.out.println("Server is listening on the port " + port);
		while (!shutdown) {
			try {
				Socket socket = serverSocket.accept();
				TcpClientServer client = new TcpClientServer(socket, protocol);
				
				executor.execute(client);
				
					clientProxies.add(client);
				
			} catch (Exception e) {
				
				e.printStackTrace();
				break;
			}
		}
		System.out.println("TcpServer finished!");
	}
	public void shutdown(int timeout) {
		//TODO - solution of a graceful shutdown
		//What is a graceful server shutdown
		//1. No receive new clients
		//2. Running getResponse should be performed
		//3. After SocketTimeoutException exiting from the loop for each client (to trigger SocketTimeoutException
		// you should apply method setSoTimeout for the client sockets with the given timeout)
		Iterator<TcpClientServer> itr=clientProxies.iterator();
				while(itr.hasNext()) {
				itr.next().shutdown();
			
		}
		shutdown = true;
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		executor.shutdownNow();
	}

}
