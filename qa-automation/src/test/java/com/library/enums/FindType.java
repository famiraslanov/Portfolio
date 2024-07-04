package com.library.enums;

import com.library.FindOptions;

public enum FindType
{
    elements(false),
    nestedChildrenParentLocator(true),
    nestedChildrenParentOptions(true),
    nestedChildrenParentElement(true);

    public boolean isNested;

    FindType(boolean nested)
    {
        this.isNested = nested;
    }

    public static FindType fromOptions(FindOptions options)
    {
        if (options.parentOption() != null) {
            return nestedChildrenParentOptions;
        } else if (options.parent() != null) {
            return nestedChildrenParentElement;
        } else if (options.parentLocator() != null) {
            return nestedChildrenParentLocator;
        } else {
            return elements;
        }
    }
}
