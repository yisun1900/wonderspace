package com.wonderwan.wonderspace.common.constant;

public interface WonderSpaceConstants {

/*    byte[] RAWKEY = {
            (byte) 0x42, (byte) 0x90, (byte) 0xbc, (byte) 0xb1, (byte) 0x54, (byte) 0x17, (byte) 0x35, (byte) 0x31, (byte) 0xf3, (byte) 0x14, (byte) 0xaf,
            (byte) 0x57, (byte) 0xf3, (byte) 0xbe, (byte) 0x3b, (byte) 0x50, (byte) 0x06, (byte) 0xda, (byte) 0x37, (byte) 0x1e, (byte) 0xce, (byte) 0x27,
            (byte) 0x2a, (byte) 0xfa, (byte) 0x1b, (byte) 0x5d, (byte) 0xbd, (byte) 0xd1, (byte) 0x10, (byte) 0x0a, (byte) 0x10, (byte) 0x07};

    byte[] RAWAD = {(byte) 0x87, (byte) 0xe2, (byte) 0x29, (byte) 0xd4, (byte) 0x50, (byte) 0x08, (byte) 0x45, (byte) 0xa0, (byte) 0x79, (byte) 0xc0};

    byte[] RAWNONCE = new byte[]{0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0};*/

    byte[] RAWAD = {
            'D', 'e', 's', 'c', 'e', 'n', 'd', 'a', 'n', 't', 's',
            'o', 'f', 't', 'h', 'e', 'D', 'r', 'a', 'g', 'o', 'n'
    };

    byte[] RAWNONCE = {
            0x1, 0x9, 0x8, 0x0, 0x0, 0x1, 0x0, 0x1
    };

    byte[] RAWKEY = {
            0x1, 0x0, 0x0, 0x0, 0x0, 0x0, 0x8, 0x9,
            0x0, 0x8, 0x3, 0x7, 0x5, 0x2, 0x8, 0x9,
            0x1, 0x1, 0x2, 0x3, 0x2, 0x2, 0x8, 0x9,
            0x8, 0x0, 0x3, 0x0, 0x5, 0x0, 0x8, 0x9
    };

    String Authorize = "[Authorize]";

    String Version = "Version = ";

    String Certificate = "Certificate = ";

    String License = "License = ";

    String UUID = "UUID = ";

    String CID = "CID = ";

    String UID = "UID = ";

    String ExpirationDate = "ExpirationDate = ";

    String PublicKey = "PublicKey = ";

    String PrivateKey = "PrivateKey = ";

    String Interface = "[Interface]";

    String Iface = "Iface = ";

    String Address = "Address = ";

    String ListenPort = "ListenPort = ";

    String DNS = "DNS = ";

    String RunMode = "RunMode = ";

    String Speed = "Speed = ";

    String Peer = "[Peer]";

    String Index = "Index = ";

    String Endpoint = "Endpoint = ";

    String PersistentKeepalive = "PersistentKeepalive = ";

    String AllowedIPs = "AllowedIPs = ";

//    String INTERFACE_CERTIFICATE_VERSION = "Interface.Certificate.Version=";
//
//    String INTERFACE_CERTIFICATE = "Interface.Certificate=";
//
//    String INTERFACE_ADDRESS = "Interface.Address=";
//
//    String INTERFACE_LISTENPORT = "Interface.Listenport=";
//
//    String INTERFACE_RUNMODE = "Interface.RunMode=";
//
//    String INTERFACE_DNS = "Interface.DNS=";
//
//    String INTERFACE_IPV4 = "Interface.ipv4=";
//
//    String INTERFACE_IPV6 = "Interface.ipv6=";
//
//    String INTERFACE_BANDWIDTH = "Interface.bandwidth=";
//
//    String INTERFACE_TOKEN = "Interface.token=";
//
//    String PEER_ENDPOINT = "Peer.Endpoint=";
//
//    String BLANK_SPACE = "\n";
//
//    String CERTIFICATE_VERSION = "Certificate.Version=";
//
//    String SERVER_ENDPOINT = "Server.Endpoint=";
//
//    String SERVER_PUBLIC_KEY = "Server.PublicKey=";
//
//    String CLIENT_PRIVATE_KEY = "Client.PrivateKey=";
//
//    String CLIENT_LICENSE = "Client.License=";
//
//    String CLIENT_ADDRESS = "Client.Address=";
//
//    String CLIENT_LISTENPORT = "Client.Listenport=";
//
//    String CLIENT_RUN_MODE = "Client.RunMode=";
//
//    String CLIENT_UUID = "Client.UUID=";
//
//    String CLIENT_CID = "Client.CID=";
//
//    String CLIENT_UID = "Client.UID=";
//
//    String CLIENT_EXPIRATION_DATE = "Client.ExpirationDate=";

    String DRUID_PUBLIC_KEY_NAME = "config.decrypt.key";

    String DRUID_PASSWORD_NAME = "password";
}
