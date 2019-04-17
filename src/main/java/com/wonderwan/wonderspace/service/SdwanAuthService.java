package com.wonderwan.wonderspace.service;

import com.wonderwan.wonderspace.common.util.BoxCfgUtil;
import com.wonderwan.wonderspace.model.entity.SdwanClientVeth;
import com.wonderwan.wonderspace.model.entity.SdwanServerVeth;
import com.wonderwan.wonderspace.model.entity.User;
import com.wonderwan.wonderspace.model.expand.SdwanClientLinkBO;
import com.wonderwan.wonderspace.model.param.*;
import io.github.garyttierney.chacha20poly1305.AeadCipherException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class SdwanAuthService {

    @Autowired
    private SdwanClientLinkService sdwanClientLinkService;

    @Autowired
    private SdwanClientVethService sdwanClientVethService;

    @Autowired
    private UserService userService;

    private short[] handleMac(String peerDevMac) {
        String[] macSegs = peerDevMac.split("-");
        short[] segs = new short[macSegs.length];
        for (int i = 0; i < segs.length; i++) {
            macSegs[i] = StringUtils.trimLeadingCharacter(macSegs[i], '0');
            if (macSegs[i].length() > 0)
                segs[i] = Short.valueOf(macSegs[i], 16);
            else
                segs[i] = 0;
        }
        return segs;
    }


    static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    public String getSdwanAuth(WonderBoxAuthVO wonderBoxAuthVO) {
        //box激活时，发送过来的设备的原始UUID，暂时使用第一块网卡的mac地址替代
//        String peerDevMac = wonderBoxAuthVO.getUuid();//"00-0c-29-d6-f9-5b";

        //查询用户
        UserVO userVO = new UserVO();
        userVO.setUuid(wonderBoxAuthVO.getUuid());
        userVO.setLicense(wonderBoxAuthVO.getLicense());
        User user = userService.queryUserByUniqueParam(userVO);
        //查询服务器
        SdwanServerVethService sdwanServerVethService = new SdwanServerVethService();
        SdwanServerVethVO sdwanServerVethVO = new SdwanServerVethVO();
        sdwanServerVethVO.setServerVethId(user.getServerVethId());
        SdwanServerVeth sdwanServerVeth = sdwanServerVethService.queryWonderSpace(sdwanServerVethVO);

        //查询盒子
        SdwanClientVethVO sdwanClientVethVO = new SdwanClientVethVO();
        SdwanClientVeth sdwanClientVeth = sdwanClientVethService.queryWonderBox(sdwanClientVethVO);
        //TODO：激活后插入uuid
        SdwanClientLinkVO sdwanClientLinkVO = new SdwanClientLinkVO();
        sdwanClientLinkVO.setClientVethId(sdwanClientVeth.getClientVethId());
        List<SdwanClientLinkBO> sdwanClientLinkBOList = sdwanClientLinkService.querySdwanClientChain(sdwanClientLinkVO);
//        List<KeyPairUtil.KeyPair> keyPairList = new ArrayList<>();

//        for (int i = 0; i < sdwanClientLinkBOList.size(); i++) {
//            keyPairList.add(KeyPairUtil.genKeyPair(sdwanClientLinkBOList.get(i).getSdwanClientVeth1().));
//        }

//        KeyPairUtil.KeyPair[] keyPairs = new KeyPairUtil.KeyPair[]{
//                KeyPairUtil.genKeyPair(peerDevMac),//服务器端作为box第一个连接的Peer的公钥私钥
//                KeyPairUtil.genKeyPair(peerDevMac),//模拟同box直连的其他的Peer的公钥私钥
//                KeyPairUtil.genKeyPair(peerDevMac),
//                KeyPairUtil.genKeyPair(peerDevMac),
//                KeyPairUtil.genKeyPair(peerDevMac),
//                KeyPairUtil.genKeyPair(peerDevMac),
//                KeyPairUtil.genKeyPair(peerDevMac),
//                KeyPairUtil.genKeyPair(peerDevMac),
//                null};
        BoxCfgUtil.Cfg cfg = new BoxCfgUtil.Cfg();
        cfg.auth = new BoxCfgUtil.Cfg.Authorize();
        cfg.iface = new BoxCfgUtil.Cfg.Interface();
        cfg.auth.version = sdwanServerVeth.getVersion();//"1.1";
        cfg.auth.license = user.getLicense();//"lstuwkvrunuiok";
        cfg.auth.uuid = String.valueOf(BoxCfgUtil.getPeerDeviceUUID(this.handleMac(wonderBoxAuthVO.getUuid())));//噪音化UUID
        cfg.auth.cid = user.getCid();//"77521199";
        cfg.auth.uid = user.getUid();//"88997766";
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.YEAR, 2020);
        cfg.auth.expirationDate = user.getExpireDt().getTime() / 1000;//calendar.getTimeInMillis() / 1000;

        cfg.iface.iface = sdwanServerVeth.getIface();//"wg0";
        cfg.iface.address = sdwanServerVeth.getIpv4();//"10.0.20.2";
        cfg.iface.listenPort = new Integer(sdwanServerVeth.getListenPort());
        cfg.iface.dns = sdwanServerVeth.getDns();//"8.8.8.8";
        cfg.iface.runMode = sdwanServerVeth.getRunMode();//"nat";
        cfg.iface.speed = user.getBandwidth();//10;
        cfg.iface.privateKey = sdwanClientVeth.getPrivateKey();//keyPairs[0].getPrivateKey();

        List<SdwanClientVeth> sdwanClientVethList =
                sdwanClientLinkBOList.stream().flatMap(sdwanClientLinkBO -> {
                    List<SdwanClientVeth> allSdwanClientVethList = new ArrayList<>();
                    allSdwanClientVethList.add(sdwanClientLinkBO.getSdwanClientVeth1());
                    allSdwanClientVethList.add(sdwanClientLinkBO.getSdwanClientVeth2());
                    return allSdwanClientVethList.stream();
                }).filter(distinctByKey(SdwanClientVeth::getClientVethId)).collect(Collectors.toList());

        for (int i = 1; i <= sdwanClientVethList.size() - 1; i++) {
            BoxCfgUtil.Cfg.Peer e = new BoxCfgUtil.Cfg.Peer();
            e.index = i;
            e.persistentKeepalive = 5;
            e.allowedIPs = " 0.0.0.0/0";
            e.publicKey = sdwanClientVethList.get(i - 1).getPublicKey();
//            e.endpoint = String.format("10.221.7.%d:36889", i); 刘莫潺注销掉
            e.endpoint = sdwanClientVethList.get(i - 1).getIpv4();
            cfg.peers.add(e);
        }

        String text;
        try {
            text = BoxCfgUtil.genAuthText(cfg);
        } catch (AeadCipherException e) {
            text = "";
        }
        return text;
    }
}
