package com.juggle.juggle.framework.common.export.meta;

import com.juggle.juggle.framework.common.export.meta.impl.ViewRender;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface ExportViewRender {
	Class<ViewRender> value();
}
