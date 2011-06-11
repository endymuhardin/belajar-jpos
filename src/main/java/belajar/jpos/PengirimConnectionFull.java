package belajar.jpos;

import java.io.IOException;

import org.jpos.iso.ISOChannel;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMUX;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOPackager;
import org.jpos.iso.ISORequest;
import org.jpos.iso.channel.ASCIIChannel;
import org.jpos.iso.packager.ISO87APackager;

public class PengirimConnectionFull {
	static ISOMUX mux;
	
	public static void main(String[] args) throws Exception {
		String tujuan = "localhost";
		Integer port = 12345;
		
		// message yang mau dikirim
		ISOMsg msg = ISOMsgHelper.createEchoRequest();
		
		// packager untuk format message
		ISOPackager packager = new ISO87APackager();
		
		// channel untuk mengirim
		ISOChannel channel = new ASCIIChannel(tujuan, port, packager);
		
		// mux untuk memaintain koneksi
		mux = new ISOMUX(channel);

		// jalankan di thread terpisah
		new Thread(mux).start();
		
		kirimConnectionfull(msg, channel);
	}

	private static void kirimConnectionfull(ISOMsg msg, ISOChannel channel)
			throws IOException, ISOException {
		ISORequest req = new ISORequest(msg);
		mux.queue(req);
		
		// terima reply
		int timeout = 30 * 1000; // 30 detik timeout
		ISOMsg reply = req.getResponse(timeout);
		
		// tampilkan reply
		if(reply == null) {
			System.out.println("Tidak dapat response sampai timeout");
		} else {
			System.out.println("Reply : ["+new String(reply.pack())+"]");
		}
	}
}
