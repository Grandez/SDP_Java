package com.jgg.sdp;

import javax.jms.*;
import javax.jms.Connection;

import org.apache.activemq.ActiveMQConnectionFactory;

import com.jgg.sdp.common.config.Configuration;
import com.jgg.sdp.domain.services.cfg.DBConfiguration;
import com.jgg.sdp.logger.LoggerListener;
import com.jgg.sdp.logger.LoggerParms;
import com.jgg.sdp.logger.jms.TestBroker;
import com.jgg.sp.logger.tcp.LoggerClient;
import com.jgg.sp.logger.tcp.TCPListener;

public class LoggerServer {

    private Configuration cfg = DBConfiguration.getInstance();

    private int port = 12345;
    
	public static void main(String[] args) {
		LoggerServer server = new LoggerServer();
		
		server.start(args);

	}

	private void start(String[] args) {
		String action = "start";
		args = cfg.processCommandLine(LoggerParms.parms, args);
		if (args.length > 1) {
			System.out.println("ERROR");
			System.exit(4);
		}
		if (args.length != 0) action = args[0];
		
		TestBroker test = new TestBroker();
		if (!test.isBrokerActive()) {
			System.err.println("No hay un Servidor JMS activo");
			System.exit(16);;
		}

		switch(parseOption(action)) {
		      case 0: processStart(); break;
		      case 1: processStop();  break;
		      case 2: processLog();   break;
		      case 3: processNoLog(); break;
		      case 4: processTest();  break;
		      default: 		
		    	System.out.println("ERROR");
				System.exit(4);

		}
	}
	
	private void processStart() {
		if (processTest()) {
			System.out.println("Esta activo");
			return;
		}
	
		startTCP();
		startJMS();
	}
	
	private void startTCP() {
		TCPListener tcpServer = new TCPListener(port);
		tcpServer.start();
	}
	
	private void startJMS() {
		try {
//            BrokerService broker = BrokerFactory.createBroker(new URI("broker:(tcp://localhost:61616)"));
//            broker.start();
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue("SDP.LOG");
            MessageConsumer consumer = session.createConsumer(queue);
            consumer.setMessageListener(new LoggerListener("SDP.LOG"));
            connection.start();
            Thread.sleep(1000);
            session.close();
//            broker.stop();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(16);
		}
	}
	
	private void processStop() {
		
	}
	private void processLog() {
		
	}
	private void processNoLog() {
		
	}
	private boolean processTest() {
		LoggerClient client = new LoggerClient();
		return client.loggerActive(port);
	}
	
	private int parseOption(String option) {
		if (option == null) return 0;
		if (option.compareTo("start") == 0) return 0;
		if (option.compareTo("stop")  == 0) return 1;
		if (option.compareTo("log")   == 0) return 2;
		if (option.compareTo("nolog") == 0) return 3;
        return -1;
	}

	
}
