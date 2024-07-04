package com.tests.enums.platform;

import com.library.Verify;

public enum UserLogin
{
    qa01("01"),
    qa02("02"),
    qa03("03"),
    qa04("04"),
    qa05("05"),
    qa06("06"),
    qa07("07"),
    qa08("08"),
    qa09("09"),
    qa10("10"),
    admin("admin", false);

    public final String emailAddress;
    public final String baseEmailInbox = "qa-ui-automation@withintelligence.com";
    public final String encryptedPassword = "W0HtL1FdvCkIQDeowaMyKj8CJfjnYiNMA/m70UTp3hg=";
    public final String encryptedPasswordAdmin = "OLjixLVjfbC/vxnoROPBxhhrvczYNPOiQJC7oLXD+Pk=";
    public final String egrEncryptedPassword = "OLjixLVjfbC/vxnoROPBxhhrvczYNPOiQJC7oLXD+Pk=";

    public boolean forRotation = true;

    UserLogin(String variation)
    {
        this.emailAddress = baseEmailInbox.replace("@", String.format("+%s@", variation));
    }

    UserLogin(String variation, boolean forRotation)
    {
        this.forRotation = forRotation;
        this.emailAddress = baseEmailInbox.replace("@", String.format("+%s@", variation));
    }


    public static UserLogin fromEmailAddress(String emailAddress)
    {
        if (!emailAddress.contains("+")) {
            Verify.fail("Unknown email address: " + emailAddress);
        }
        String[] parts = emailAddress.split("\\+");
        return UserLogin.valueOf("qa" + parts[1].substring(0, 2));
    }
}
