package com.csmall.mail;

import android.text.TextUtils;
import android.util.Log;

import com.csmall.log.LogHelper;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Created by wangchao on 2017/4/1.
 */

public class MailSender {
    private static final String TAG = "MailSender";
    private static IMailConfig mailConfig;

    public static void setMailConfig(IMailConfig mailConfig) {
        MailSender.mailConfig = mailConfig;
    }

    public static boolean sendMail(MailData mailData) {
        if (mailConfig == null) {
            LogHelper.e(TAG, "mailConfig == null");
            return false;
        }
        Properties props = new Properties();
        props.put("mail.smtp.host", mailConfig.getSmtp());// 存储发送邮件服务器的信息
        props.put("mail.smtp.auth", "true");// 同时通过验证
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        // 基本的邮件会话
        Session session = Session.getInstance(props);
        session.setDebug(true);// 设置调试标志
        // 构造信息体
        MimeMessage message = new MimeMessage(session);

        // 发件地址
        Address fromAddress = null;
        // fromAddress = new InternetAddress("sarah_susan@sina.com");
        try {
            fromAddress = new InternetAddress(mailConfig.getAccount());
        } catch (AddressException e) {
            Log.e(TAG, "", e);
            return false;
        }
        try {
            message.setFrom(fromAddress);
        } catch (MessagingException e) {
            Log.e(TAG, "", e);
            return false;
        }

        // 收件地址
        Address toAddress = null;
        try {
            toAddress = new InternetAddress(mailConfig.getAccount());
        } catch (AddressException e) {
            Log.e(TAG, "", e);
            return false;
        }
        try {
            message.addRecipient(Message.RecipientType.TO, toAddress);
        } catch (MessagingException e) {
            Log.e(TAG, "", e);
            return false;
        }

        // 解析邮件内容

        try {
            message.setSubject(mailData.mailTitle);// 设置信件的标题
        } catch (MessagingException e) {
            Log.e(TAG, "", e);
            return false;
        }

        // 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
        Multipart mp = new MimeMultipart();
        MimeBodyPart mbp = new MimeBodyPart();
        try {
            mbp.setContent(mailData.mailContent, "text/html;charset=utf-8");
        } catch (MessagingException e) {
            Log.e(TAG, "", e);
            return false;
        }
        try {
            mp.addBodyPart(mbp);
        } catch (MessagingException e) {
            Log.e(TAG, "", e);
            return false;
        }
        if (!TextUtils.isEmpty(mailData.mailAttach)) {//有附件
            mbp = new MimeBodyPart();
            FileDataSource fds = new FileDataSource(mailData.mailAttach); //得到数据源
            try {
                mbp.setDataHandler(new DataHandler(fds)); //得到附件本身并至入BodyPart
            } catch (MessagingException e) {
                Log.e(TAG, "", e);
                return false;
            }
            try {
                mbp.setFileName(fds.getName());  //得到文件名同样至入BodyPart
            } catch (MessagingException e) {
                Log.e(TAG, "", e);
                return false;
            }
            try {
                mp.addBodyPart(mbp);
            } catch (MessagingException e) {
                Log.e(TAG, "", e);
                return false;
            }
        }
        try {
            message.setContent(mp); //Multipart加入到信件
        } catch (MessagingException e) {
            Log.e(TAG, "", e);
            return false;
        }
        try {
            message.saveChanges(); // implicit with send()//存储有信息
        } catch (MessagingException e) {
            Log.e(TAG, "", e);
            return false;
        }

//                   Enumeration efile=file.elements();
//                    while(efile.hasMoreElements()){
//                             mbp=new MimeBodyPart();
//                                 filename=efile.nextElement().toString(); //选择出每一个附件名
//                                FileDataSource fds=new FileDataSource(filename); //得到数据源
//                                  mbp.setDataHandler(new DataHandler(fds)); //得到附件本身并至入BodyPart
//                               mbp.setFileName(fds.getName());  //得到文件名同样至入BodyPart
//                                 mp.addBodyPart(mbp);
//                            }
//                        file.removeAllElements();
//                       }


        // send e-mail message
        Transport transport = null;
        try {
            transport = session.getTransport("smtp");
        } catch (NoSuchProviderException e) {
            Log.e(TAG, "", e);
            return false;
        }
        try {
            LogHelper.d(TAG, "mailConfig.getSmtp():" +mailConfig.getSmtp()
                    + " mailConfig.getAccount():" + mailConfig.getAccount()
                    + " mailConfig.getPassword():" + mailConfig.getPassword());
            transport.connect(mailConfig.getSmtp(), mailConfig.getAccount(), mailConfig.getPassword());
        } catch (MessagingException e) {
            Log.e(TAG, "", e);
            return false;
        }

        try {
            transport.sendMessage(message, message.getAllRecipients());
        } catch (MessagingException e) {
            Log.e(TAG, "", e);
            return false;
        }
        try {
            transport.close();
        } catch (MessagingException e) {
            Log.e(TAG, "", e);
            return false;
        }
        return true;
    }

}
