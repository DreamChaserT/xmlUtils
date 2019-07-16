package top.dc.tools.xml.test;

import top.dc.tools.xml.anno.DcXmlRoot;

/**
 * @author weixia.tian
 */

@DcXmlRoot("xml")
public class MsgRequest {
    private String ToUserName;
    private String FromUserName;
    private String CreateTime;
    private String MsgType;
    private String Content;
    private String MsgId;
    private TypeModel type;
}
