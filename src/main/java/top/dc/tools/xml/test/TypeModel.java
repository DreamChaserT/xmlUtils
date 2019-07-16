package top.dc.tools.xml.test;

import top.dc.tools.xml.anno.DcXml;
import top.dc.tools.xml.anno.DcXmlInner;

/**
 * @author weixia.tian
 */
@DcXmlInner
public class TypeModel {
    private String id;
    @DcXml(ignore = true)
    private String name;
    @DcXml(isProperty = true)
    private String attr;
}
