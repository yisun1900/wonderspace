package com.wonderwan.wonderspace.service;

import com.wonderwan.wonderspace.model.entity.SdwanClientVeth;
import com.wonderwan.wonderspace.model.entity.SdwanServerVeth;
import com.wonderwan.wonderspace.model.entity.User;
import com.wonderwan.wonderspace.model.param.SdwanClientVethVO;
import com.wonderwan.wonderspace.model.param.SdwanServerVethVO;
import com.wonderwan.wonderspace.model.param.UserVO;
import com.wonderwan.wonderspace.model.param.WonderBoxAuthVO;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;

@Service
public class WonderBoxAuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private SdwanClientVethService sdwanClientVethService;

    @Autowired
    private SdwanServerVethService sdwanServerVethService;

    private void buildProperty(StringBuilder body, String property, String value) {
        this.buildProperty(body, property, value, true);
    }

    private void buildProperty(StringBuilder body, String property, String value, boolean isNotLast) {
        body.append(property).append(value);
        if (isNotLast) {
//            body.append(WonderSpaceConstants.BLANK_SPACE);
        }
    }

    public String getWonderWireInfo(WonderBoxAuthVO wonderBoxAuthVO) {
        User user = this.getUserByWonderAuthRequest(wonderBoxAuthVO);
        SdwanClientVeth sdwanClientVeth = this.getWonderBox(user);
        SdwanServerVeth sdwanServerVeth = this.getWonderSpace(user);
        String certificate = this.getWonderWireAuth(sdwanClientVeth, sdwanServerVeth, user);
        String wonderWireInfo = this.buildWonderWireInfo(certificate, sdwanServerVeth, user);
        return wonderWireInfo;
    }

    public String getWonderWireAuth(SdwanClientVeth sdwanClientVeth, SdwanServerVeth sdwanServerVeth, User user) {
        String certificate = this.getWonderWireCertificate(sdwanClientVeth, sdwanServerVeth, user);
        byte[] encryptValue = this.chacha20poly1305(certificate);
        String base64Value = this.base64WonderWireInfo(encryptValue);
        return base64Value;
    }

    public User getUserByWonderAuthRequest(WonderBoxAuthVO wonderBoxAuthVO) {
        UserVO userVO = new UserVO();
        userVO.setUuid(wonderBoxAuthVO.getUuid());
        userVO.setLicense(wonderBoxAuthVO.getLicense());
        return userService.queryUserByUniqueParam(userVO);
    }

    public SdwanClientVeth getWonderBox(User user) {
        SdwanClientVethVO sdwanClientVethVO = new SdwanClientVethVO();
        sdwanClientVethVO.setClientVethId(user.getClientVethId());
        return sdwanClientVethService.queryWonderBox(sdwanClientVethVO);
    }

    public SdwanServerVeth getWonderSpace(User user) {
        SdwanServerVethVO sdwanServerVethVO = new SdwanServerVethVO();
        sdwanServerVethVO.setServerVethId(user.getServerVethId());
        return sdwanServerVethService.queryWonderSpace(sdwanServerVethVO);
    }

    public String getWonderWireCertificate(SdwanClientVeth sdwanClientVeth, SdwanServerVeth sdwanServerVeth, User user) {
        StringBuilder certBuilder = new StringBuilder();
//        this.buildProperty(certBuilder, WonderSpaceConstants.CERTIFICATE_VERSION, sdwanClientVeth.getVersion());
//        this.buildProperty(certBuilder, WonderSpaceConstants.SERVER_ENDPOINT, sdwanServerVeth.getPeerEndpoint());
//        this.buildProperty(certBuilder, WonderSpaceConstants.SERVER_PUBLIC_KEY, sdwanServerVeth.getPublicKey());
//        this.buildProperty(certBuilder, WonderSpaceConstants.CLIENT_PRIVATE_KEY, sdwanClientVeth.getPrivateKey());
//        this.buildProperty(certBuilder, WonderSpaceConstants.CLIENT_LICENSE, user.getLicense());
//        this.buildProperty(certBuilder, WonderSpaceConstants.CLIENT_ADDRESS, sdwanServerVeth.getIpv4());
//        this.buildProperty(certBuilder, WonderSpaceConstants.CLIENT_LISTENPORT, sdwanServerVeth.getListenPort());
//        this.buildProperty(certBuilder, WonderSpaceConstants.CLIENT_RUN_MODE, sdwanServerVeth.getRunMode());
//        this.buildProperty(certBuilder, WonderSpaceConstants.CLIENT_UUID, user.getUuid());
//        this.buildProperty(certBuilder, WonderSpaceConstants.CLIENT_CID, user.getCid());
//        this.buildProperty(certBuilder, WonderSpaceConstants.CLIENT_UID, user.getUid());
//        this.buildProperty(certBuilder, WonderSpaceConstants.CLIENT_EXPIRATION_DATE, user.getExpireDt().toString(), false);

//        this.buildProperty(certBuilder, WonderSpaceConstants.Authorize, "");
//        this.buildProperty(certBuilder, WonderSpaceConstants.Version, sdwanClientVeth.getVersion());
//        this.buildProperty(certBuilder, WonderSpaceConstants.License, sdwanServerVeth.getPublicKey());
//        this.buildProperty(certBuilder, WonderSpaceConstants.UUID, sdwanClientVeth.getPrivateKey());
//        this.buildProperty(certBuilder, WonderSpaceConstants.CID, user.getLicense());
//        this.buildProperty(certBuilder, WonderSpaceConstants.UID, sdwanServerVeth.getIpv4());
//        this.buildProperty(certBuilder, WonderSpaceConstants.ExpirationDate, user.getExpireDt().toString());
//        this.buildProperty(certBuilder, WonderSpaceConstants.PublicKey, sdwanServerVeth.getPublicKey());
//        SdwanClientVethVO sdwanClientVethVO = new SdwanClientVethVO();
//        sdwanClientVethVO.setCid(user.getCid());
//        List<SdwanClientVeth> sdwanClientVethList = sdwanClientVethService.queryWonderBoxByUserList(sdwanClientVethVO);
//        if (!CollectionUtils.isEmpty(sdwanClientVethList)) {
//            for(){
//
//            }
//        }
//        this.buildProperty(certBuilder, WonderSpaceConstants.PrivateKey, sdwanServerVeth.getPrivateKey());
//
//        this.buildProperty(certBuilder, WonderSpaceConstants.CLIENT_CID, user.getCid());
//        this.buildProperty(certBuilder, WonderSpaceConstants.CLIENT_UID, user.getUid());
//        this.buildProperty(certBuilder, WonderSpaceConstants.CLIENT_EXPIRATION_DATE, user.getExpireDt().toString(), false);
        return certBuilder.toString();
    }

    public String buildWonderWireInfo(String certificate, SdwanServerVeth sdwanServerVeth, User user) {
        StringBuilder info = new StringBuilder();
//        this.buildProperty(info, WonderSpaceConstants.INTERFACE_CERTIFICATE_VERSION, sdwanServerVeth.getVersion());
//        this.buildProperty(info, WonderSpaceConstants.INTERFACE_CERTIFICATE, certificate);
//        this.buildProperty(info, WonderSpaceConstants.INTERFACE_ADDRESS, sdwanServerVeth.getIpv4());
//        this.buildProperty(info, WonderSpaceConstants.INTERFACE_LISTENPORT, sdwanServerVeth.getListenPort());
//        this.buildProperty(info, WonderSpaceConstants.INTERFACE_RUNMODE, sdwanServerVeth.getRunMode());
//        this.buildProperty(info, WonderSpaceConstants.INTERFACE_DNS, sdwanServerVeth.getDns());
//        this.buildProperty(info, WonderSpaceConstants.PEER_ENDPOINT, sdwanServerVeth.getPeerEndpoint());
//        this.buildProperty(info, WonderSpaceConstants.INTERFACE_IPV4, sdwanServerVeth.getIpv4());
//        this.buildProperty(info, WonderSpaceConstants.INTERFACE_IPV6, sdwanServerVeth.getIpv6());
//        this.buildProperty(info, WonderSpaceConstants.INTERFACE_TOKEN, sdwanServerVeth.getToken());
//        this.buildProperty(info, WonderSpaceConstants.INTERFACE_BANDWIDTH, user.getBandwidth().toString());
        return info.toString();
    }

    public String base64WonderWireInfo(byte[] encryptValue) {
        return new String(Base64.encodeBase64(encryptValue));
    }

    private ByteBuffer wrapDirect(byte[] data) {
        ByteBuffer buffer = ByteBuffer.allocateDirect(data.length);
        buffer.put(data);
        buffer.position(0);
        return buffer;
    }

    public byte[] chacha20poly1305(String certificate) {
//        try {
//            byte[] certbytes = certificate.getBytes();
//            byte[] key = WonderSpaceConstants.RAWKEY.clone();
//            byte[] ad = WonderSpaceConstants.RAWAD.clone();
//            byte[] nonce = WonderSpaceConstants.RAWNONCE.clone();
//            AeadMode mode = AeadMode.CHACHA20_POLY1305;
//            AeadCipher cipher = new AeadCipher(new SecretKey(key), mode);
//            ByteBuffer cipherbuf = cipher.encrypt(wrapDirect(certbytes), certbytes.length,
//                    wrapDirect(ad), ad.length, wrapDirect(nonce));
//            byte[] cipherbytes = new byte[cipherbuf.limit()];
//            cipherbuf.get(cipherbytes);
//            return Base64.encodeBase64(cipherbytes);
//        } catch (AeadCipherException e) {
//            e.printStackTrace();
//        }
        return "".getBytes();
    }
}
