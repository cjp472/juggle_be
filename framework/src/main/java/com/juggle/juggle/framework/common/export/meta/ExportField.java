package com.juggle.juggle.framework.common.export.meta;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 用于绑定实体属性、Excel列名、Excel列索引之间的关系
 * @author allan
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExportField {
    String name() ;//列名
    int position();//列标
}
