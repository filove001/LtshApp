package com.ltsh.app.chat.utils;

import com.ltsh.app.chat.entity.common.Result;
import com.ltsh.app.chat.enums.ResultCodeEnum;
import com.ltsh.app.chat.handler.CallbackHandler;

import org.jivesoftware.smack.PacketCollector;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketIDFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Registration;

/**
 * Created by Random on 2018/1/18.
 */

public class XmppUtils {

    private static Result register(String account, String nickName, String email, String password) {
        Registration reg = new Registration();
        //设置类型
        reg.setType(IQ.Type.SET);
        //发送到服务器
        reg.setTo(XmppConnection.getConnection().getServiceName());
        //设置用户名
        reg.setUsername(account);
        //设置密码
        reg.setPassword(password);
        //设置其余属性 不填可能会报500异常 连接不到服务器 asmack一个Bug
        //设置昵称（其余属性）
        reg.addAttribute("name", nickName);
        //设置邮箱（其余属性）
        reg.addAttribute("email", email);
        //设置android端注册
        reg.addAttribute("android", "geolo_createUser_android");
        //创建包过滤器
        PacketFilter filter = new AndFilter(new PacketIDFilter(reg
                .getPacketID()), new PacketTypeFilter(IQ.class));
        //创建包收集器
        PacketCollector collector = XmppConnection.getConnection()
                .createPacketCollector(filter);
        //发送包
        XmppConnection.getConnection().sendPacket(reg);
        //获取返回信息
        IQ result = (IQ) collector.nextResult(SmackConfiguration
                .getPacketReplyTimeout());
        // 停止请求results（是否成功的结果）
        collector.cancel();
        //通过返回信息判断
        if (result == null) {   //无返回，连接不到服务器
        } else if (result.getType() == IQ.Type.ERROR) {     //错误状态
            if (result.getError().toString()
                    .equalsIgnoreCase("conflict(409)")) {   //账户存在 409判断
            } else {
            }
        } else if (result.getType() == IQ.Type.RESULT) {//注册成功跳转登录
            return new Result<>(ResultCodeEnum.SUCCESS);
        }
        return new Result<>(ResultCodeEnum.REGISTER_FAIL);
    }

    private static Result login(String account, String password) {
        // 连接服务器，用户登录
        try {
            XmppConnection.getConnection().login(account, password);
            // 连接服务器成功，更改在线状态
            Presence presence = new Presence(Presence.Type.available);
            XmppConnection.getConnection().sendPacket(presence);
            return new Result<>(ResultCodeEnum.SUCCESS);
        } catch (XMPPException e) {
            e.printStackTrace();
        }
        return new Result<>(ResultCodeEnum.LOGIN_FAIL);
    }
}
