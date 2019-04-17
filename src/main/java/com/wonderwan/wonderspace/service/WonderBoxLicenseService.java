package com.wonderwan.wonderspace.service;

import com.wonderwan.wonderspace.common.enumeration.UserStatusEnum;
import com.wonderwan.wonderspace.model.entity.User;
import com.wonderwan.wonderspace.model.param.UserVO;
import com.wonderwan.wonderspace.model.param.WonderBoxLicenseVO;
import com.wonderwan.wonderspace.model.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.Optional;

@Service
public class WonderBoxLicenseService {

    @Value("#{wonder-box.license.ttl}")
    private Long wonderBoxLicenseTtl;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private GenerateKeyService generateKeyService;

    @Autowired
    private UserService userService;

    protected String generateWonderBoxNewLicense(String uid) {
        try {
            String newLicense = generateKeyService.createWonderBoxLicense();
            redisTemplate.multi();
            redisTemplate.opsForValue().set(uid, newLicense, Duration.ofMinutes(wonderBoxLicenseTtl));
            redisTemplate.opsForValue().set(newLicense, uid, Duration.ofMinutes(wonderBoxLicenseTtl));
            redisTemplate.exec();
            return newLicense;
        } catch (Exception e) {
            redisTemplate.discard();
        }
        return "";
    }

    public String generateWonderBoxLicense(String uid) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(uid)).map(license ->
                StringUtils.isEmpty(license) ? this.generateWonderBoxNewLicense(uid) : license
        ).orElse(this.generateWonderBoxNewLicense(uid));
    }

    public User getUserById(String uid) {
        UserVO userVO = new UserVO();
        userVO.setUid(uid);
        return userService.queryUserByUniqueParam(userVO);
    }

    @Transactional(rollbackFor = Throwable.class, propagation = Propagation.SUPPORTS)
    public void updateUserByActivateLicense(User user, String license, String uuid) {
        user.setLicense(license);
        user.setUuid(uuid);
        //TODO: mock service
        user.setBandwidth(50);
        user.setServices(7);
        user.setUserType("1");
        user.setExpireDt(new Timestamp(1557936000));
        userService.updateUser(user);
    }

    @Transactional(rollbackFor = Throwable.class, propagation = Propagation.REQUIRED)
    public Result<String> getWonderBoxLicense(WonderBoxLicenseVO wonderBoxLicenseVO) {
        Result<String> result = new Result<>();
        try {
            User user = this.getUserById(wonderBoxLicenseVO.getUid());
            String license = this.generateWonderBoxLicense(user.getUid());
            if (user != null && UserStatusEnum.UNINITIALIZED.equals(user.getStatus()) &&
                    !StringUtils.isEmpty(license)) {
                result.buildSuccessInfo("", "", license);
            } else if (user == null) {
                result.buildErrorInfo("User is invalid", "");
            } else if (!UserStatusEnum.UNINITIALIZED.equals(user.getStatus())) {
                result.buildErrorInfo("User status is not uninitialized", "");
            } else if (StringUtils.isEmpty(license)) {
                result.buildErrorInfo("Service error,the license is empty", "");
            }
        } catch (Exception e) {
            result.buildErrorInfo(e.getMessage(), "");
        }
        return result;
    }

    @Transactional(rollbackFor = Throwable.class, propagation = Propagation.REQUIRED)
    public Result<String> activateWonderBoxLicense(WonderBoxLicenseVO wonderBoxLicenseVO) {
        Result<String> result = new Result<>();
        try {
            if (StringUtils.isEmpty(wonderBoxLicenseVO.getLicense())) {
                result.buildErrorInfo("license is empty", "");
            } else {
                String uuid = wonderBoxLicenseVO.getUuid();
                String license = wonderBoxLicenseVO.getLicense();
                String uid = redisTemplate.opsForValue().get(license);
                User user = this.getUserById(uid);
                if (user != null && UserStatusEnum.UNINITIALIZED.equals(user.getStatus())
                        && !StringUtils.isEmpty(uid) && redisTemplate.hasKey(license)) {
                    this.updateUserByActivateLicense(user, license, uuid);
                    result.buildSuccessInfo("", "", "Success to activate license");
                } else if (user == null) {
                    result.buildErrorInfo("User is invalid", "");
                } else if (!UserStatusEnum.UNINITIALIZED.equals(user.getStatus())) {
                    result.buildErrorInfo("User status is not uninitialized", "");
                } else if (StringUtils.isEmpty(uuid)) {
                    result.buildErrorInfo("Box's uuid is empty", "");
                } else if (StringUtils.isEmpty(license)) {
                    result.buildErrorInfo("License is empty", "");
                } else if (!redisTemplate.hasKey(license)) {
                    result.buildErrorInfo("License is overdue", "");
                }
            }
        } catch (Exception e) {
            result.buildErrorInfo(e.getMessage(), "");
        }
        return result;
    }
}
