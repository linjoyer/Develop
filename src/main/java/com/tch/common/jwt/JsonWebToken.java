package com.tch.common.jwt;


import com.tch.dao.primary.UserInfoRepository;
import com.tch.domain.entity.system.UserInfo;
import com.tch.domain.protocols.APIResult;
import com.tch.domain.protocols.APIResultCode;
import com.tch.common.shiro.PasswordHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by shz on 2017/8/31.
 */
@RestController
public class JsonWebToken {
    final Logger log= LoggerFactory.getLogger(JsonWebToken.class);
    @Autowired
    private UserInfoRepository userRepositoy;
    @Autowired
    private MyAudience audienceEntity;
    @Autowired
    PasswordHelper passwordHelper;

    @RequestMapping("oauth/token")
    public Object getAccessToken(@RequestBody LoginPara loginPara)
    {
        APIResult resultMsg;
        try
        {
            if(loginPara.getClientId() == null
                    || (loginPara.getClientId().compareTo(audienceEntity.getClientId()) != 0))
            {
                resultMsg = new APIResult(APIResultCode.INVALID_CLIENTID);
                return resultMsg;
            }
            //验证码校验在后面章节添加


            //验证用户名密码
            UserInfo user = userRepositoy.findByUsername(loginPara.getUserName());
            if (user == null)
            {
                resultMsg = new APIResult(APIResultCode.INVALID_PASSWORD);
                return resultMsg;
            }
            else
            {
                String md5Password = passwordHelper.encryptPassword(loginPara.getUserName(),loginPara.getPassword(),user.getSalt());

                if (md5Password.compareTo(user.getPassword()) != 0)
                {
                    resultMsg = new APIResult(APIResultCode.INVALID_PASSWORD);
                    return resultMsg;
                }
            }

            //拼装accessToken
            String accessToken = JwtHelper.createJWT(loginPara.getUserName(), String.valueOf(user.getUserid()),
                     audienceEntity.getClientId(), audienceEntity.getName(),
                    audienceEntity.getExpiresSecond() * 1000, audienceEntity.getBase64Secret());
            //返回accessToken
            AccessToken accessTokenEntity = new AccessToken();
            accessTokenEntity.setAccess_token(accessToken);
            accessTokenEntity.setExpires_in(audienceEntity.getExpiresSecond());
            accessTokenEntity.setToken_type("bearer");
            resultMsg = new APIResult(APIResultCode.SUCCESS,"获取成功",accessTokenEntity);
            return resultMsg;
        }
        catch(Exception ex)
        {
            log.error(ex.getMessage());
            ex.printStackTrace();
            resultMsg = new APIResult(APIResultCode.ERROR);
            return resultMsg;
        }
    }
}
