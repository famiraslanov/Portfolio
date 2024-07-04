package com.library.helpers;

import com.microsoft.graph.options.Option;
import com.microsoft.graph.options.QueryOption;
import com.tests.enums.platform.UserLogin;
import lombok.Builder;
import lombok.Getter;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.Optional;

@Builder
@Getter
public class EmailSearch
{
    private String subject;
    private String content;
    @Builder.Default
    private boolean onlyUnread = true;


    public LinkedList<Option> build(UserLogin emailInbox)
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\"");
        stringBuilder.append("to:").append(URLEncoder.encode(emailInbox.emailAddress, StandardCharsets.UTF_8));

        stringBuilder.append(
                Optional.ofNullable(subject).map(s -> " AND subject:(" + s + ")").orElse("") +
                        Optional.ofNullable(content).map(c -> " AND (" + c + ")").orElse("") +
                        (onlyUnread ? " AND isread:false" : "")
        );

        stringBuilder.append("\"");

        LinkedList<Option> requestOptions = new LinkedList<>();
        requestOptions.add(new QueryOption("$search", stringBuilder.toString()));

        return requestOptions;
    }
}


