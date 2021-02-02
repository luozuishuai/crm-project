package com.shangma.cn.common.pool;

import com.shangma.cn.common.untils.MailUtils;

public class AsyncFactory {

    /**
     * 发送邮件
     */
    public static Runnable sendHtmlMail(String subject, String to, String text) {
        return () -> MailUtils.sendHtmlMail(subject, to, text);
    }
}
