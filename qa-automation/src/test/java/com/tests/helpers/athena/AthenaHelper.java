package com.tests.helpers.athena;

import com.library.Find;
import com.library.Store;
import com.library.Verify;
import com.library.helpers.Function;
import org.openqa.selenium.By;

import java.time.Duration;

public class AthenaHelper
{
    private static String baseUrl = "https://athena.crm11.dynamics.com/main.aspx?appid=a46a9221-ad9c-eb11-b1ac-000d3a872e1b&pagetype=entityrecord&etn=task&id=";

    /*
        PLEASE NOTE:

        This helper is written for one off test case usage
        It requires manual interaction with your device during login
        The design/pattern used here should not be replicated as an example (due to non-robust selectors, sleeps, etc.)
        If this becomes mainstream code then this will need to be rewritten
     */

    public static void login()
    {
        String userName = Store.getSettings().getAthenaUserName();
        String encryptedPassword = Store.getSettings().getAthenaEncryptedPassword();

        loadId("03f21757-0695-ee11-be37-6045bd0c1cdf", true);
        Function.sleep(Duration.ofSeconds(2), "Wait for annimation");
        Verify.isFound(By.cssSelector(".remove-segoe-ui-symbol"), "Correct page not found");

        Find.insert(By.cssSelector("input[name='loginfmt']"), userName);
        Find.click(By.id("idSIButton9"));

        Verify.isFound(By.id("x509_login"), "OKTA page not found");
        Find.insert(By.id("okta-signin-username"), userName);
        Function.sleep(Duration.ofSeconds(1), "Wait for spinner");
        Find.insert(Find.options().locator(By.id("okta-signin-password")).decryptText(true), encryptedPassword);
        Find.click(By.id("okta-signin-submit"));

        Verify.isFound(By.id("form63"), "Push form not seen");
        Function.sleep(Duration.ofSeconds(1), "Wait for form to reload");
        Find.click(By.cssSelector(".button-primary"));
        Function.sleep(Duration.ofSeconds(2), "Wait for page load");
        System.out.println("ACTION: Please check your phone for the SSO push message and acknowledge it (within 30s)");
        Find.click(Find.options().locator(By.id("idSIButton9")).timeoutMS(30000).maxWaitMS(60000));
        Function.sleep(Duration.ofSeconds(5), "Initial login page settle");
    }

    public static void loadId(String id)
    {
        loadId(id, false);
    }

    private static void loadId(String id, boolean fromLogin)
    {
        Function.load(baseUrl + id);

        if (!fromLogin) {
            Verify.isFound(By.id("mainContent"), "Main container not found");
            Verify.isFound(By.cssSelector("img[title='With Intelligence']"), "Logo not loaded");
            Verify.isFound(By.cssSelector("h1[id^='formHeaderTitle_']"), "h1 title not seen");
            Verify.contains(Function.getCurrentUrl(), id, "URL did not contain the task id");
        }
    }
}
