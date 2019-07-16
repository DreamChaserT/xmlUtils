package top.dc.tools.xml.util;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import top.dc.tools.xml.anno.DcXml;
import top.dc.tools.xml.anno.DcXmlInner;
import top.dc.tools.xml.anno.DcXmlRoot;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * @author weixia.tian
 */
public class XmlFormatUtil {
    /**
     * 生成XML 方法
     *
     * @param src   数据
     * @param clazz 类型
     * @param <T>   clazz 类型实例
     * @return 解析结果
     */
    public static <T> String formatMain(T src, Class<T> clazz) {
        String res = "";
        try {
            final Document document = DocumentHelper.createDocument();

            //读取根节点标签名
            final DcXmlRoot xmlRoot = clazz.getAnnotation(DcXmlRoot.class);
            final String rootName = xmlRoot.value();

            Element root = document.addElement(rootName);
            root = formatObj(root, src, clazz);

            if (null != root) {
                res = root.asXML();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 内部节点转换
     *
     * @param src   节点
     * @param clazz 节点类型
     * @param <T>   clazz 类型实例
     * @return 将 class 转换为 element
     */
    private static Element formatObj(Element element, Object src, Class clazz) {

        try {
            Element res = element;

            final Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                final DcXml rlXml = field.getAnnotation(DcXml.class);
                String domName = "";
                boolean ignore = false;
                boolean isProperty = false;
                boolean isContent = false;

                if (null != rlXml) {
                    domName = rlXml.value();
                    ignore = rlXml.ignore();
                    isProperty = rlXml.isProperty();
                    isContent = rlXml.isContent();
                }

                if (!ignore) {

                    if (StringUtils.isEmpty(domName)) {
                        //如果注解没有填写dom名称,使用变量名
                        domName = field.getName();
                    }

                    field.setAccessible(true);
                    final Object data = field.get(src);
                    if (null != data) {

                        if (isProperty) {
                            //标签属性
                            element.addAttribute(domName, data.toString());
                        } else if (isContent) {
                            //标签内部值
                            element.setText(data.toString());
                        } else {
                            //下一级属性
                            final Class innerCalzz = field.getType();
                            final Annotation xmlInner = innerCalzz.getAnnotation(DcXmlInner.class);
                            final Element innerElement = element.addElement(domName);
                            if (null != xmlInner) {
                                formatObj(innerElement, data, innerCalzz);
                            }else{
                                innerElement.setText(data.toString());
                            }
                        }
                    }
                }
            }
            return res;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {

    }
}
