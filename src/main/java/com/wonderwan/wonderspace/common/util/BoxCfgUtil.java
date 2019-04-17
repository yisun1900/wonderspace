package com.wonderwan.wonderspace.common.util;

import io.github.garyttierney.chacha20poly1305.AeadCipher;
import io.github.garyttierney.chacha20poly1305.AeadCipherException;
import io.github.garyttierney.chacha20poly1305.AeadMode;
import io.github.garyttierney.chacha20poly1305.common.SecretKey;
import org.apache.commons.codec.binary.Base64;

import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public final class BoxCfgUtil {

	private static final SimpleDateFormat UNIXTIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	static {
		System.loadLibrary("chacha20poly1305jni");
	}

	private BoxCfgUtil() {
	}
	
	// 初始化ad，作为混淆码，不要修改
	private final static short[] rawbbid = new short[] { 0x1, 0xe, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0 };

	// 初始化ad，作为混淆码，不要修改
	private final static byte[] rawad = { 
			'D', 'e', 's', 'c', 'e', 'n', 'd', 'a', 'n', 't', 's', 
			'o', 'f', 't', 'h', 'e', 'D', 'r', 'a', 'g', 'o', 'n' 
    };
	
	// 初始化nonce，作为混淆码，不要修改
	private final static byte[] rawnonce = {
			0x1, 0x9, 0x8, 0xa, 0xb, 0x7, 0xe, 0x6
	};
	
	// 初始化key，作为混淆码，不要修改
	private final static byte[] rawkey = { 
		0x1, 0x0, 0x0, 0x0, 0x0, 0x0, 0x8, 0x9,
		0x0, 0x8, 0x3, 0x7, 0x5, 0x2, 0x8, 0x9,
		0x1, 0x1, 0x2, 0x3, 0x2, 0x2, 0x8, 0x9,
		0x8, 0x0, 0x3, 0x0, 0x5, 0x0, 0x8, 0x9
	};
	
	public static long getPeerDeviceUUID(short[] segs) {
		long uuid = 0L;
		short[] bbid = rawbbid.clone();
		bbid[0] = (short)(((segs[1] & 0xFF) ^ rawnonce[6]) & 0xFF);
		bbid[1] = (short)(((segs[5] & 0xFF) ^ rawnonce[1]) & 0xFF);
		bbid[2] = (short)(((segs[4] & 0xFF) ^ rawnonce[3]) & 0xFF);
		bbid[3] = (short)(((segs[0] & 0xFF) ^ rawnonce[7]) & 0xFF);
		bbid[4] = (short)(((segs[3] & 0xFF) ^ rawnonce[4]) & 0xFF);
		bbid[5] = (short)(((segs[2] & 0xFF) ^ rawnonce[5]) & 0xFF);
		
		uuid = (((long)bbid[7] & 0xFF) << 24 | ((long)bbid[6] & 0xFF) << 16 | 
                ((long)bbid[5] & 0xFF) <<  8 | ((long)bbid[4] & 0xFF)) << 32 
			   | 
               (((long)bbid[3] & 0xFF) << 24 | ((long)bbid[2] & 0xFF) << 16 | 
                ((long)bbid[1] & 0xFF) <<  8 | ((long)bbid[0] & 0xFF));
		return uuid;
	}

	public static String genAuthText(Cfg cfg) throws AeadCipherException {
		byte[] ad = rawad.clone();
		byte[] nonce = rawnonce.clone();
		byte[] key = rawkey.clone();

		StringBuilder authBuilder = new StringBuilder();
		authBuilder.append(String.format("Version = %s\n", cfg.auth.version));
		authBuilder.append(String.format("License = %s\n", cfg.auth.license));
		authBuilder.append(String.format("UUID = %s\n", cfg.auth.uuid));
		authBuilder.append(String.format("CID = %s\n", cfg.auth.cid));
		authBuilder.append(String.format("UID = %s\n", cfg.auth.uid));
		authBuilder.append(String.format("ExpirationDate = %s\n", String.valueOf(cfg.auth.expirationDate)));
		authBuilder.append(String.format("PrivateKey = %s\n", cfg.iface.privateKey));
		for (Cfg.Peer p : cfg.peers) {
			authBuilder.append("\n[PeerKey]\n");
			authBuilder.append(String.format("Index = %d\n", p.index));
			authBuilder.append(String.format("PublicKey = %s\n", p.publicKey));
		}

		byte[] authbytes = authBuilder.toString().getBytes();
		System.out.println(authBuilder.toString());
		System.out.println("authbytes length:" + authbytes.length);

		AeadMode mode = AeadMode.CHACHA20_POLY1305;
		AeadCipher cipher = new AeadCipher(new SecretKey(key), mode);
		ByteBuffer cipherbuf = cipher.encrypt(wrap(authbytes), authbytes.length, wrap(ad), ad.length, wrap(nonce));
		byte[] cipherbytes = new byte[cipherbuf.limit()];
		cipherbuf.get(cipherbytes);
		String certificate = new String(Base64.encodeBase64(cipherbytes));

		StringBuilder cfgBuilder = new StringBuilder();
		cfgBuilder.append("[Authorize]\n");
		cfgBuilder.append(String.format("Version = %s\n", cfg.auth.version));
		cfgBuilder.append(String.format("Certificate = %s\n", certificate));
		cfgBuilder.append(String.format("License = %s\n", cfg.auth.license));
		cfgBuilder.append(String.format("UUID = %s\n", cfg.auth.uuid));
		cfgBuilder.append(String.format("CID = %s\n", cfg.auth.cid));
		cfgBuilder.append(String.format("UID = %s\n", cfg.auth.uid));
		cfgBuilder.append(String.format("ExpirationDate = %s\n", UNIXTIME_FORMAT.format(new Date(cfg.auth.expirationDate * 1000))));

		cfgBuilder.append("\n[Interface]\n");
		cfgBuilder.append(String.format("Iface = %s\n", cfg.iface.iface));
		cfgBuilder.append(String.format("Address = %s\n", cfg.iface.address));
		cfgBuilder.append(String.format("ListenPort = %d\n", cfg.iface.listenPort));
		cfgBuilder.append(String.format("DNS = %s\n", cfg.iface.dns));
		cfgBuilder.append(String.format("RunMode = %s\n", cfg.iface.runMode));
		cfgBuilder.append(String.format("Speed = %dMbit\n", cfg.iface.speed));

		for (Cfg.Peer p : cfg.peers) {
			cfgBuilder.append("\n[Peer]\n");
			cfgBuilder.append(String.format("Index = %d\n", p.index));
			cfgBuilder.append(String.format("Endpoint = %s\n", p.endpoint));
			cfgBuilder.append(String.format("PersistentKeepalive = %d\n", p.persistentKeepalive));
			cfgBuilder.append(String.format("AllowedIPs = %s\n", p.allowedIPs));
		}

		return cfgBuilder.toString();
	}

	private static ByteBuffer wrap(byte[] data) {
		ByteBuffer buffer = ByteBuffer.allocateDirect(data.length);
		buffer.put(data);
		buffer.position(0);
		return buffer;
	}

	public static class Cfg {

		public Authorize auth;
		public Interface iface;
		public List<Peer> peers = new ArrayList<Peer>();

		public static class Authorize {
			public String version;
			public String license;
			public String uuid;
			public String cid;
			public String uid;
			public long expirationDate;
		}

		public static class Interface {
			public String iface;
			public String address;
			public int listenPort;
			public String dns;
			public String runMode;
			public int speed;
			public String privateKey;
		}

		public static class Peer {
			public int index;
			public String endpoint;
			public int persistentKeepalive;
			public String allowedIPs;
			public String publicKey;
		}
	}
}
