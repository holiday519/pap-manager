package com.pxene.pap.common;

import java.beans.PropertyEditorSupport;
import java.util.Date;

import org.springframework.web.bind.WebDataBinder;

public class RequestUtils
{
    public static PropertyEditorSupport getDateTimePropertyEditor()
    {
        return new PropertyEditorSupport()
        {
            @Override
            public void setAsText(String value)
            {
                setValue(new Date(Long.valueOf(value)));
            }
        };
    }

    public static void inDateTimeBind(WebDataBinder binder)
    {
        binder.registerCustomEditor(Date.class, RequestUtils.getDateTimePropertyEditor());
    }
}
