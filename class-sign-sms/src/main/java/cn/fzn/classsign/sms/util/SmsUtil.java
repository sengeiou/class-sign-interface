package cn.fzn.classsign.sms.util;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

import java.util.Random;

public class SmsUtil {
    private static final String SignName = "课堂签";

    public static Boolean SendSms(String tele, String code, String TemplateCode) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4G6L8e3qGsTJrJ1zjed5", "13sozHXtFxDuobsaplM7EospvQ8sdU");
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", tele);
        request.putQueryParameter("SignName", SignName);
        request.putQueryParameter("TemplateCode", TemplateCode);
        while(code.length()<6){
            code="0"+code;
        }
        System.out.println(code);
        request.putQueryParameter("TemplateParam", "{\"code\":'" + code + "'}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            if (response.getData().contains("OK")) {
                return true;
            } else {
                return false;
            }
        } catch (ServerException e) {
            e.printStackTrace();
            return false;
        } catch (ClientException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public static String generateVerificationCode() {
        Random random = new Random();
        String code = "";
        while (code.length() < 6) {
            code += random.nextInt(10);
        }
        return code;
    }
}
