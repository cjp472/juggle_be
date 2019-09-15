package com.juggle.juggle.framework.common.i18n.meta;

import java.lang.annotation.*;

@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface I18n {
    String value() ;
}
