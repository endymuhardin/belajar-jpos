package belajar.jpos;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;

public class ISOMsgHelper {
	static SimpleDateFormat formatterBit7 = new SimpleDateFormat("MMddHHmmss");
	static SimpleDateFormat formatterBit12 = new SimpleDateFormat("HHmmss");
	
	public static ISOMsg createEchoRequest() throws ISOException {
		ISOMsg netmanRequest = new ISOMsg();
		netmanRequest.setMTI("0800");
		netmanRequest.set(7, formatterBit7.format(new Date()));
		netmanRequest.set(12, formatterBit12.format(new Date()));
		netmanRequest.set(70, "301");
		return netmanRequest;
	}
}
