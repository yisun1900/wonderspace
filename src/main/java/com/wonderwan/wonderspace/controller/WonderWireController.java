package com.wonderwan.wonderspace.controller;

import com.wonderwan.wonderspace.model.param.WonderBoxAuthVO;
import com.wonderwan.wonderspace.model.param.WonderBoxLicenseVO;
import com.wonderwan.wonderspace.model.response.Result;
import com.wonderwan.wonderspace.service.SdwanAuthService;
import com.wonderwan.wonderspace.service.WonderBoxAuthService;
import com.wonderwan.wonderspace.service.WonderBoxLicenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wonderwire")
public class WonderWireController {

    @Autowired
    private WonderBoxAuthService wonderBoxAuthService;

    @Autowired
    private WonderBoxLicenseService wonderBoxLicenseService;

    @Autowired
    private SdwanAuthService sdwanAuthService;

    @PostMapping("/wonderbox/license/obtain")
    public Result<String> getWonderwanLicense(@RequestBody WonderBoxLicenseVO wonderBoxLicenseVO) {
        return wonderBoxLicenseService.getWonderBoxLicense(wonderBoxLicenseVO);
    }

    @PostMapping("/wonderbox/license/activate")
    public Result<String> activateWonderwanLicense(@RequestBody WonderBoxLicenseVO wonderBoxLicenseVO) {
        return wonderBoxLicenseService.activateWonderBoxLicense(wonderBoxLicenseVO);
    }

    @PostMapping("/wonderbox/certificate/obtain")
    public String getWonderBoxCertificate(@RequestBody WonderBoxAuthVO wonderBoxAuthVO) {
        return sdwanAuthService.getSdwanAuth(wonderBoxAuthVO);
    }
}
