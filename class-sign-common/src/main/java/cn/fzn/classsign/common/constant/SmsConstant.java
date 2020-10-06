package cn.fzn.classsign.common.constant;

public interface SmsConstant {
    /**
     *  类型
     */
    class Type {
        //注册模板
        public final static String REGISTER_TEMPLATE = "reg";
        //登录模板
        public final static String LOGIN_TEMPLATE = "login";
    }

    /**
     * 模板
     */
    class Code {
        //登录模板
        public final static String LOGIN_TEMPLATE = "SMS_193511136";
        //身份验证模板
        public final static String REGISTER_TEMPLATE = "SMS_182674857";
    }

    public static final Integer SMS_SECOND=300;
}
