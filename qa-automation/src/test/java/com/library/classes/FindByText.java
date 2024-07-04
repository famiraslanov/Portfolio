package com.library.classes;

import com.library.enums.Comparator;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindByText
{
    private Comparator comparator;
    private String textToLookFor;

    public static FindByText by()
    {
        return new FindByText();
    }

    public FindByText comparator(Comparator comparator)
    {
        this.comparator = comparator;
        return this;
    }

    public FindByText lookFor(String textToLookFor)
    {
        this.textToLookFor = textToLookFor;
        return this;
    }

    public FindByText contains(String textContains)
    {
        this.comparator = Comparator.contains;
        this.textToLookFor = textContains;
        return this;
    }

    public FindByText equals(String textEquals)
    {
        this.comparator = Comparator.equals;
        this.textToLookFor = textEquals;
        return this;
    }

    public FindByText startsWith(String textEquals)
    {
        this.comparator = Comparator.startsWith;
        this.textToLookFor = textEquals;
        return this;
    }

    public FindByText regex(String regex)
    {
        this.comparator = Comparator.regex;
        this.textToLookFor = regex;
        return this;
    }

    public FindByText regexCaseInsensitive(String regex)
    {
        this.comparator = Comparator.regexCaseInsensitive;
        this.textToLookFor = regex;
        return this;
    }
}
