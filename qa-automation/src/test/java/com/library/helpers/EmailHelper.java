package com.library.helpers;

import com.azure.identity.ClientSecretCredentialBuilder;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.logger.DefaultLogger;
import com.microsoft.graph.logger.LoggerLevel;
import com.microsoft.graph.models.Message;
import com.microsoft.graph.requests.GraphServiceClient;
import com.microsoft.graph.requests.MessageCollectionPage;
import com.tests.classes.PendingEmail;
import com.library.Log;
import com.library.Store;
import com.library.Verify;
import com.library.classes.MsGraphKeys;
import com.tests.enums.platform.UserLogin;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

public class EmailHelper
{

    public static Message fetchEmail(UserLogin emailInbox, EmailSearch emailSearch)
    {
        return fetchEmail(emailInbox, emailSearch, false);
    }

    public static Message fetchEmail(UserLogin emailInbox, EmailSearch emailSearch, boolean allowSoftFail)
    {
        List<Message> messageList = fetchEmails(emailInbox, emailSearch);
        if (!CollectionUtils.isEmpty(messageList) && messageList.size() == 1) {
            Log.object("Email found", messageList.get(0));
            return messageList.get(0);
        }

        Log.object("EmailSearch", emailSearch);
        Log.object("Emails found", messageList);
        Verify.isTrue(false, "No single email found", allowSoftFail);

        return null;
    }

    public static List<Message> fetchEmails(UserLogin emailInbox, EmailSearch emailSearch)
    {
        MessageCollectionPage messages = client().users(emailInbox.baseEmailInbox)
                .messages()
                .buildRequest(emailSearch.build(emailInbox))
                .get();

        return messages != null ? messages.getCurrentPage() : null;
    }

    public static void markRead(UserLogin emailInbox, Message message)
    {
        Message markedRead = new Message();
        markedRead.isRead = true;

        markedRead = client().users(emailInbox.baseEmailInbox)
                .messages(message.id)
                .buildRequest()
                .patch(markedRead);
        Log.object("NewMessageState", markedRead);
    }

    private static GraphServiceClient client()
    {
        DefaultLogger logger = new DefaultLogger();
        logger.setLoggingLevel(LoggerLevel.DEBUG);

        return GraphServiceClient.builder()
                .authenticationProvider(authProvider())
                .logger(logger)
                .buildClient();
    }

    private static TokenCredentialAuthProvider authProvider()
    {
        final MsGraphKeys msGraphKeys = Store.getSettings().getMsGraphKeys();
        return new TokenCredentialAuthProvider(
                List.of("https://graph.microsoft.com/.default"),
                new ClientSecretCredentialBuilder()
                        .clientId(msGraphKeys.getAppId())
                        .clientSecret(msGraphKeys.getSecret())
                        .tenantId(msGraphKeys.getTenantId())
                        .build()
        );
    }

    public static void checkForEmails()
    {
        PendingEmail[] pendingEmails = ApiHelper.fetchPendingEmails();
        Log.story("Emails to check for: " + pendingEmails.length);
        int numberToProcess = pendingEmails.length;
        for (PendingEmail pendingEmail : pendingEmails) {
            Log.debug("Looking for: " + pendingEmail.getToEmail() + " containing: " + pendingEmail.getUniqueContent());
            UserLogin userLogin = UserLogin.fromEmailAddress(pendingEmail.getToEmail());
            Message email = fetchEmail(
                    userLogin,
                    EmailSearch.builder().content(pendingEmail.getUniqueContent()).build()
            );

            if (email != null) {
                if (ApiHelper.markEmailReceived(pendingEmail.getId(), email)) {
                    markRead(userLogin, email);
                    numberToProcess--;
                } else {
                    Verify.isTrue(false, "Unable to mark email as received", true);
                }
            }
        }

        Verify.isEqual(0, numberToProcess, "There are " + numberToProcess + " messages left to process");
    }
}
