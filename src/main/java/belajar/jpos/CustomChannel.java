package belajar.jpos;

import java.io.IOException;

import org.jpos.iso.BaseChannel;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOPackager;
import org.jpos.iso.ISOUtil;

public class CustomChannel extends BaseChannel {

	public CustomChannel(String tujuan, Integer port, ISOPackager packager) {
		super(tujuan, port, packager);
	}

	public CustomChannel() {
		super();
	}

	public CustomChannel(ISOPackager packager) throws IOException {
		super(packager);
	}

	@Override
	protected int getMessageLength() throws IOException, ISOException {
		int l = 0;
		int msglength = 0;
		byte[] b = new byte[2];
		while (l == 0) {
			serverIn.readFully(b, 0, 2);
			msglength = (Integer.parseInt(ISOUtil.hexString(b, 0, 1), 16) * 256)
					+ (Integer.parseInt(ISOUtil.hexString(b, 1, 1), 16));
			try {
				if ((l = msglength) == 0) {
					serverOut.write(b);
					serverOut.flush();
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
				throw new ISOException("Invalid message length "
						+ new String(b) + "length=" + l);
			}
		}
		return l;
	}

	@Override
	protected void sendMessageLength(int len) throws IOException {
		int b0 = len / 256;
		int b1 = len % 256;
		byte[] b = new byte[2];
		b[0] = ISOUtil.hex2byte(Integer.toHexString(b0))[0];
		b[1] = ISOUtil.hex2byte(Integer.toHexString(b1))[0];
		serverOut.write(b);
	}
	
}
