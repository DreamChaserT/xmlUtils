package top.dc.tools.xml.anno;

import java.lang.annotation.*;

/**
 * @author weixia.tian
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DcXml {
    /**
     * dom 节点名字
     */
    String value() default "";

    /**
     * 是否忽略此属性
     */
    boolean ignore() default false;

    /**
     * 是否为标签参数
     */
    boolean isProperty() default false;

    /**
     * 是否为内部值
     */
    boolean isContent() default false;
}
