package org.apache.axis2.transport.mail.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.axis2.engine.AxisFault;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * @author Chamil Thanthrimudalige
 */

public class POP3Server extends Thread {
    protected static Log log = LogFactory.getLog(POP3Server.class.getName());
    private ServerSocket serverSocket;
    private Storage st = null;

    
    public POP3Server(Storage st,int port) throws AxisFault {
    	this.st = st;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started on port " + port);
        } catch(IOException e) {
            throw new AxisFault(e);
        }
    }

    public void run() {
        while(true) {
            try {
                Socket socket = serverSocket.accept();
                POP3Worker thread = new POP3Worker(socket, st);
                thread.start();
            } catch(Exception e) {
            	log.error(e);
            }
        }
    }
}