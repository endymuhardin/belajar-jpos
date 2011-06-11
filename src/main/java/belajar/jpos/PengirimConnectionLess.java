package belajar.jpos;

import java.io.IOException;

import org.jpos.iso.BaseChannel;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOPackager;
import org.jpos.iso.packager.ISO87APackager;
import org.jpos.util.Log4JListener;


public class PengirimConnectionLess {
	
	
	public static void main(String[] args) throws Exception {
		String tujuan = "localhost";
		Integer port = 12345;
		
		// message yang mau dikirim
		ISOMsg msg = ISOMsgHelper.createEchoRequest();
		
		// packager untuk format message
		ISOPackager packager = new ISO87APackager();
		
		// channel untuk mengirim
		CustomChannel channel = new CustomChannel(tujuan, port, packager);
		org.jpos.util.Logger jposLogger = new org.jpos.util.Logger();
		Log4JListener log4JListener = new Log4JListener();
		log4JListener.setLevel("info");
		jposLogger.addListener(log4JListener);
		channel.setLogger(jposLogger, "client-channel");
		
		kirimConnectionless(msg, channel);
	}

	private static void kirimConnectionless(ISOMsg msg, BaseChannel channel)
			throws IOException, ISOException {
		channel.connect();
		channel.setTimeout(30000);
		channel.send(msg);
		
		// terima reply
		ISOMsg reply = channel.receive();
		
		// tampilkan reply
		System.out.println("Reply : ["+new String(reply.pack())+"]");
		
		// selesai
		channel.disconnect();
	}

	
}
