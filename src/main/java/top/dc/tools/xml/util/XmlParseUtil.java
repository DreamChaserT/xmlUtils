package top.dc.tools.xml.util;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import top.dc.tools.xml.anno.DcXml;
import top.dc.tools.xml.anno.DcXmlInner;

import java.lang.reflect.Field;

/**
 * @author weixia.tian
 */
public class XmlParseUtil {

    /**
     * 解析XML 方法
     *
     * @param src   原始XML
     * @param clazz 类型
     * @param <T>   clazz 类型实例
     * @return 解析结果
     */
    public static <T> T parseMain(String src, Class<T> clazz) {
        T res = null;
        try {
            res = clazz.newInstance();

            final Document parse = DocumentHelper.parseText(src);
            if (null != parse) {
                final Element root = parse.getRootElement();
                res = parseObj(root, clazz);
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
     * @return 解析结果
     */
    private static <T> T parseObj(Element src, Class<T> clazz) {

        try {
            T res = clazz.newInstance();

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

                    if (isProperty) {
                        //标签属性
                        final String data = src.attributeValue(domName);
                        if (null != data) {
                            field.setAccessible(true);
                            field.set(res, data);
                        }
                    } else if (isContent) {
                        //标签内部值
                        final Object data = src.getData();
                        if (null != data) {
                            field.setAccessible(true);
                            field.set(res, data);
                        }
                    } else {
                        //下一级属性
                        final Element element = src.element(domName);
                        if (null != element) {
                            final Class<?> type = field.getType();
                            final DcXmlInner xmlInner = type.getAnnotation(DcXmlInner.class);
                            Object data = null;
                            if (null != xmlInner) {
                                //另一个xml 需要继续转换
                                data = parseObj(element, type);
                            } else {
                                data = element.getData();
                            }
                            if (null != data) {
                                field.setAccessible(true);
                                field.set(res, data);
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


    public static void main(String[] args){

    }

}
