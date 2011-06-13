package belajar.jpos;

import java.io.IOException;

import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOPackager;
import org.jpos.iso.ISORequestListener;
import org.jpos.iso.ISOServer;
import org.jpos.iso.ISOSource;
import org.jpos.iso.channel.ASCIIChannel;
import org.jpos.iso.packager.ISO87APackager;
import org.jpos.util.Log4JListener;

public class Penerima {
	// packager untuk format message
	static ISOPackager packager = new ISO87APackager();
	
	// port untuk menerima
	static Integer port = 12345;
	
	public static void main(String[] args) throws IOException {
		ASCIIChannel channel = new ASCIIChannel(packager);
		org.jpos.util.Logger jposLogger = new org.jpos.util.Logger();
		Log4JListener log4JListener = new Log4JListener();
		log4JListener.setLevel("info");
		jposLogger.addListener(log4JListener);
		channel.setLogger(jposLogger, "server-channel");
		ISOServer server = new ISOServer(port, channel, null);
		server.setLogger(jposLogger, "iso-server");
		
		server.addISORequestListener(new ISORequestListener() {
			
			@Override
			public boolean process(ISOSource src, ISOMsg msg) {
				try {
					System.out.println("request : ["+new String(msg.pack())+"]");
					ISOMsg reply = (ISOMsg) msg.clone();
					reply.setMTI("0810");
					reply.set(39, "00");
					src.send(reply);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				return true;
			}
		});
		new Thread(server).start();
		System.out.println("Server menunggu di port "+port);
	}
}
