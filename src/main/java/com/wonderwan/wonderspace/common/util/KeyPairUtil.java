package com.wonderwan.wonderspace.common.util;

import org.apache.commons.codec.binary.Base64;
import org.whispersystems.curve25519.Curve25519;
import org.whispersystems.curve25519.Curve25519KeyPair;
import org.whispersystems.curve25519.JCESecureRandomProvider;
import org.whispersystems.curve25519.SecureRandomProvider;

/*

	<!-- https://mvnrepository.com/artifact/org.whispersystems/curve25519-java -->
	<dependency>
		<groupId>org.whispersystems</groupId>
		<artifactId>curve25519-java</artifactId>
		<version>0.5.0</version>
	</dependency>

 */
public final class KeyPairUtil {

	private KeyPairUtil() {
	}

	public static KeyPair genKeyPair(String devUuid) {
		SecureRandomProvider random = new DeviceUUIDRandomProvider(devUuid);
		Curve25519 cipher = Curve25519.getInstance(Curve25519.BEST, random);
		Curve25519KeyPair keyPair = cipher.generateKeyPair();
		byte[] prikeyBytes = keyPair.getPrivateKey();
		byte[] pubkeyBytes = keyPair.getPublicKey();

		String prikey = new String(Base64.encodeBase64(prikeyBytes));
		String pubkey = new String(Base64.encodeBase64(pubkeyBytes));
		return new KeyPair(prikey, pubkey);
	}

	private static class DeviceUUIDRandomProvider extends JCESecureRandomProvider {

		private String uuid;

		public DeviceUUIDRandomProvider(String uuid) {
			this.uuid = uuid;
		}

		@Override
		public void nextBytes(byte[] output) {
			super.nextBytes(output);
			byte[] bytes = uuid.getBytes();
			int length = Math.min(bytes.length, output.length);
			System.arraycopy(bytes, 0, output, 0, length);
		}

	}

	public static class KeyPair {

		private KeyPair(String privateKey, String publicKey) {
			this.privateKey = privateKey;
			this.publicKey = publicKey;
		}

		private String privateKey;
		private String publicKey;

		public String getPrivateKey() {
			return privateKey;
		}

		public String getPublicKey() {
			return publicKey;
		}

	}
}
