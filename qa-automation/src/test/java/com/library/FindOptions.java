package com.library;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.library.classes.FindByText;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Field;

@Setter
@Getter
@Accessors(fluent = true)
@JsonDeserialize(builder = FindOptions.class)
public class FindOptions
{
    private By locator = null;
    private boolean failOnNotFound = true;
    private int timeoutMS = Store.getSettings().getDefaultTimeoutMS();
    private int maxWaitMS = Store.getSettings().getDefaultMaxWaitMS();
    private boolean maxTimeoutSet = false;

    private boolean visible = false;
    private boolean clickable = false;
    private boolean enabled = false;

    private boolean applyQuickWatch = true;

    private boolean clearFirst = true;

    @JsonIgnore // WebElement is a snapshot of the DOM at a given time so no point in Serializing
    private WebElement parent;
    private boolean allowSoftFail = false;
    private boolean returnFirst = false;
    private boolean returnLast = false;
    private boolean scrollTo = false;

    private boolean scrollOnElement = false;

    private By parentLocator;
    private FindOptions parentOption;
    private FindByText findByText = null;

    private boolean debugThisCall = false;
    private boolean staleRefRetry = false;
    private boolean clonedOptions = false;
    private boolean checkForNoSpinner = false;
    private boolean decryptText = false;

    private boolean findLogging = true; // Primarily used to suppress Find logging when used in the 100ms polling for notFound


    public FindOptions timeoutMS(int timeoutMS)
    {
        this.timeoutMS = timeoutMS;

        if (!this.maxTimeoutSet) {
            this.maxWaitMS = timeoutMS;
        }
        return this;
    }

    public int maxWaitMS()
    {
        if (maxWaitMS < timeoutMS()) {
            maxWaitMS(timeoutMS);
        }

        return maxWaitMS;
    }

    public FindOptions maxWaitMS(int maxWaitMS)
    {
        this.maxWaitMS = maxWaitMS;
        this.maxTimeoutSet = true;
        return this;
    }

    public FindOptions scrollOnElement(Boolean scroll)
    {
        this.scrollTo = scroll;
        this.scrollOnElement = scroll;
        return this;
    }

    public FindOptions failOnNotFound(Boolean failOnNotFound)
    {
        if (debugThisCall || Store.getSettings().isVerboseFindLogging()) {
            Log.debug("failOnNotFound being set to " + failOnNotFound);
        }

        this.failOnNotFound = failOnNotFound;
        return this;
    }

    public FindOptions clone()
    {
        FindOptions temp = new FindOptions();
        for (Field field : this.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                field.set(temp, field.get(this));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        temp.clonedOptions = true;

        if (debugThisCall || Store.getSettings().isVerboseFindLogging()) {
            Log.debug("Cloned options used");
        }

        return temp;
    }
}
