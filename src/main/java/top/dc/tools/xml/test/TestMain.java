package top.dc.tools.xml.test;

import static top.dc.tools.xml.util.XmlFormatUtil.formatMain;
import static top.dc.tools.xml.util.XmlParseUtil.parseMain;

/**
 * @author weixia.tian
 */
public class TestMain {
    public static void main(String[] args){
        String msg="<xml><ToUserName><![CDATA[321]]></ToUserName>\n" +
                "<FromUserName><![CDATA[123]]></FromUserName>\n" +
                "<CreateTime>123</CreateTime>\n" +
                "<MsgType><![CDATA[text]]></MsgType>\n" +
                "<Content><![CDATA[12]]></Content>\n" +
                "<MsgId>321</MsgId>\n" +
                "<type attr=\"123456\"><id>id here</id><name>name here</name></type>\n" +
                "</xml>";
        final MsgRequest msgRequest = parseMain(msg, MsgRequest.class);
        System.currentTimeMillis();

        final String res=formatMain(msgRequest,MsgRequest.class);

        System.currentTimeMillis();
    }
}
