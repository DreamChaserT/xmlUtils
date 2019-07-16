package top.dc.tools.xml.anno;

import java.lang.annotation.*;

/**
 * @author weixia.tian
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DcXmlRoot {

    /**
     * dom 节点名字
     */
    String value() default "";

}
