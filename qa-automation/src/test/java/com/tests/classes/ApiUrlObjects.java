package com.tests.classes;

public class ApiUrlObjects
{
    /**
     * To override local then use localApiUrl & localApiToken in settings.local
     *
     * @return ApiUrlObject that has url(), graphQl(), authToken() methods
     */
    public static ApiUrlObject classification()
    {
        return ApiUrlObject.builder()
                .prodUrlObject(ApiUrlPairObject.builder()
                        .url("https://classification.dom-prod.with.digital/api")
                        .authToken("0lkCFfiGGfCyGjCtSwAXxWnUArHYGhqum9dIDlmaLPbCz4qYeamhsvAvC4OS1qZi")
                        .build())
                .stagingObject(ApiUrlPairObject.builder()
                        .url("https://classification-stage.dom-non-prod.with.digital/api")
                        .authToken("SuRRB6u7PfB9ezkQstBiZ9nZMgyNKbYWFZVESGqKQLHkAeg3eXrqTKJU3zeCBGiy")
                        .build())
                .devObject(ApiUrlPairObject.builder()
                        .url("https://classification-dev.dom-non-prod.with.digital/api")
                        .authToken("5t36bZGVkg3EAedmKcJKeYeaLLMgHjpB2zimBJP3HGN62nBY84vxqaA2ggGyssCZ")
                        .build())
                .localObject(ApiUrlPairObject.builder()
                        .url("http://localhost:8002/api")
                        .authToken("Kkb3NNxy4ESwWvStVHUusyk9hYPQYU2u")
                        .build())
                .repo("/pageant/dom-classification-microservice")
                .build();
    }

    /**
     * To override local then use localApiUrl & localApiToken in settings.local
     *
     * @return ApiUrlObject that has url(), graphQl(), authToken() methods
     */
    public static ApiUrlObject currency()
    {
        return ApiUrlObject.builder()
                .prodUrlObject(ApiUrlPairObject.builder()
                        .url("https://currency.dom-prod.with.digital/api")
                        .authToken("dvsK9rPRPkpqmq3EGQPOQp99w3EquyMM6kBJqb0GbSvbWIuNaO20BeuhbpwerazW")
                        .build())
                .stagingObject(ApiUrlPairObject.builder()
                        .url("https://currency-stage.dom-non-prod.with.digital/api")
                        .authToken("C8YxrbuSVxNnWdGE4fPiJ3T82hjBdyVEZojZeM5rz7c8qaN3KgmL2wBkCYrg64qz")
                        .build())
                .devObject(ApiUrlPairObject.builder()
                        .url("https://currency-dev.dom-non-prod.with.digital/api")
                        .authToken("d474NUmXouFjGeFAhz2WWoLUjve8PsMTcBzrHSKn8ujocnSeVzBjtrx2EAkzuj5Y")
                        .build())
                .localObject(ApiUrlPairObject.builder()
                        .url("http://localhost:8008/api")
                        .authToken("SHgdvKHmczeY4anTHD9kFpgTmjn5Q9Lq")
                        .build())
                .repo("/pageant/dom-currency-microservice")
                .build();
    }

    /**
     * To override local then use localApiUrl & localApiToken in settings.local
     *
     * @return ApiUrlObject that has url(), graphQl(), authToken() methods
     */
    public static ApiUrlObject fund()
    {
        return ApiUrlObject.builder()
                .prodUrlObject(ApiUrlPairObject.builder()
                        .url("https://fund.dom-prod.with.digital/api")
                        .authToken("3gSxZg3X4yz9etmJsVtXNgKPfGojA0MipkXmccbnLk9u9FhteEtcgWNTAotXEala")
                        .build())
                .stagingObject(ApiUrlPairObject.builder()
                        .url("https://fund-stage.dom-non-prod.with.digital/api")
                        .authToken("EdFeRegPttqsedG5bvRSfNSjspEHTC4JvdZvnEUg8ExG5BXDBdbaSjA9ER5o3D3m")
                        .build())
                .devObject(ApiUrlPairObject.builder()
                        .url("https://fund-dev.dom-non-prod.with.digital/api")
                        .authToken("94t6Q98uT2SQ3o8fuUKnXvbwZLuwbvFtet7xJndBWPMni4SD2bBitGoN2Vp4za4d")
                        .build())
                .localObject(ApiUrlPairObject.builder()
                        .url("http://localhost:8011/api")
                        .authToken("rwQVoX10XnuMoUBMRiKks3BAr34QrA05")
                        .build())
                .repo("/pageant/dom-fund-microservice")
                .build();
    }

    /**
     * To override local then use localApiUrl & localApiToken in settings.local
     *
     * @return ApiUrlObject that has url(), graphQl(), authToken() methods
     */
    public static ApiUrlObject investor()
    {
        return ApiUrlObject.builder()
                .prodUrlObject(ApiUrlPairObject.builder()
                        .url("https://investor.dom-prod.with.digital/api")
                        .authToken("L0NkUcrfILzasHzAkp13ykFghsX4Pc9QNdVeM8RqORJcivuR01WmfpLBgq5uJRcJ")
                        .build())
                .stagingObject(ApiUrlPairObject.builder()
                        .url("https://investor-stage.dom-non-prod.with.digital/api")
                        .authToken("YLtshb7U2PCXmKVTA8dhfG4idzhYEoYNSfwCZzosqZLBDLAug93LEvTA8scTs9QD")
                        .build())
                .devObject(ApiUrlPairObject.builder()
                        .url("https://investor-dev.dom-non-prod.with.digital/api")
                        .authToken("DWeKG9V9WG3YHx55LQ7exrh9vvtApKYbyg8iSNkRhdpUtgQAwRhqSUtkzFFxQZHu")
                        .build())
                .localObject(ApiUrlPairObject.builder()
                        .url("http://localhost:8006/api")
                        .authToken("kZ3Gcr9mhJyPUVAiviYDQtPCUJDyEi74")
                        .build())
                .repo("/pageant/dom-investor-microservice")
                .build();
    }

    /**
     * To override local then use localApiUrl & localApiToken in settings.local
     *
     * @return ApiUrlObject that has url(), graphQl(), authToken() methods
     */
    public static ApiUrlObject lpOutreach()
    {
        return ApiUrlObject.builder()
                .prodUrlObject(ApiUrlPairObject.builder()
                        .url("https://lp-outreach.dom-prod.with.digital/api")
                        .authToken("sxeWGuJgTxn6fed4KmvFD95UQnTKFWtGs9erfFD9fhqbTBKx8u3F8qQUU4kK7JvU")
                        .build())
                .stagingObject(ApiUrlPairObject.builder()
                        .url("https://lp-outreach-stage.dom-non-prod.with.digital/api")
                        .authToken("8YTzgstAMLCCdFcsLJJrxVaqZUAabAJSndyM9Yke2hQyoXpiBDBf2obPMpkXLZzM")
                        .build())
                .devObject(ApiUrlPairObject.builder()
                        .url("https://lp-outreach-dev.dom-non-prod.with.digital/api")
                        .authToken("QuiQr5kThLAxDckEoKnyDGjQjBScJGJ3NMTYgWcSiMVTnuNsXMxxQebEfRM6Zz9i")
                        .build())
                .localObject(ApiUrlPairObject.builder()
                        .url("http://localhost:8010/api")
                        .authToken("CLXr6R2ubdlTgodt9jfR0J0adj3PwI4W")
                        .build())
                .repo("/pageant/dom-lp-outreach-microservice")
                .build();
    }

    /**
     * To override local then use localApiUrl & localApiToken in settings.local
     *
     * @return ApiUrlObject that has url(), graphQl(), authToken() methods
     */
    public static ApiUrlObject manager()
    {
        return ApiUrlObject.builder()
                .prodUrlObject(ApiUrlPairObject.builder()
                        .url("https://manager.dom-prod.with.digital/api")
                        .authToken("2nNeeIKlwLxBeE6PvQ19oW7BAO9MfqZi5qIXcWPgzGnO7g3kDdPIMwWxMW59yPXM")
                        .build())
                .stagingObject(ApiUrlPairObject.builder()
                        .url("https://manager-stage.dom-non-prod.with.digital/api")
                        .authToken("a59G7FY4SUhq4PnVD65jtwmMVWvUqsuEsTakv6hHp3ma62E4DX4Txwu3MEjaRp7G")
                        .build())
                .devObject(ApiUrlPairObject.builder()
                        .url("https://manager-dev.dom-non-prod.with.digital/api")
                        .authToken("xfhdEGU9jGEKvtNAWN6iP3F6Dn33wPFXETqqUjwRPmon8GJEvCGteoSoeyXKXwVm")
                        .build())
                .localObject(ApiUrlPairObject.builder()
                        .url("http://localhost:8005/api")
                        .authToken("T4LuTZK7rW9ufweDAbU7pVyxG4CJu2dB")
                        .build())
                .repo("/pageant/dom-manager-microservice")
                .build();
    }

    /**
     * To override local then use localApiUrl & localApiToken in settings.local
     *
     * @return ApiUrlObject that has url(), graphQl(), authToken() methods
     */
    public static ApiUrlObject organisation()
    {
        return ApiUrlObject.builder()
                .prodUrlObject(ApiUrlPairObject.builder()
                        .url("https://organisation.dom-prod.with.digital/api")
                        .authToken("UXlierOQgrLE0EiDUbSDPnUJgTdYLzaHbnWg0tdkRux3lgsA9Y2CfG6petzIsP0I")
                        .build())
                .stagingObject(ApiUrlPairObject.builder()
                        .url("https://organisation-stage.dom-non-prod.with.digital/api")
                        .authToken("tn5DGCqcNDhDbFtmjQPEMjGUNU3eL6H833wnp7o5eoE3RUA5FrgBHUcWBzv2AqKb")
                        .build())
                .devObject(ApiUrlPairObject.builder()
                        .url("https://organisation-dev.dom-non-prod.with.digital/api")
                        .authToken("xhjVCchLCSxYBua9bMR586PuAKPAPrUrtG9H9e2SC3cJoVvh5uHgXcB5E5pc2HEJ")
                        .build())
                .localObject(ApiUrlPairObject.builder()
                        .url("http://localhost:8001/api")
                        .authToken("ERzHF1eBTJZOk4K4rm8MngRXuvQX56Rk")
                        .build())
                .repo("/pageant/dom-organisation-microservice")
                .build();
    }

    /**
     * To override local then use localApiUrl & localApiToken in settings.local
     *
     * @return ApiUrlObject that has url(), graphQl(), authToken() methods
     */
    public static ApiUrlObject person()
    {
        return ApiUrlObject.builder()
                .prodUrlObject(ApiUrlPairObject.builder()
                        .url("https://person.dom-prod.with.digital/api")
                        .authToken("4lXKcl1jPVqSk9sIohCMiEdwuGkSHsipHG0pKdNMM33e3VHuCqzvs1bFzMpYFBq6")
                        .build())
                .stagingObject(ApiUrlPairObject.builder()
                        .url("https://person-stage.dom-non-prod.with.digital/api")
                        .authToken("RdUBn3L5hW7uhFjfg2WM27kbhU6BqDQaznckvyZ7o5biZ6uVcgs9GY7Q2oPeDymC")
                        .build())
                .devObject(ApiUrlPairObject.builder()
                        .url("https://person-dev.dom-non-prod.with.digital/api")
                        .authToken("G4eycEUJKtknkm7hRX3SxVMXutH6vC9rToNecSvhoQyivisTgxkaucXRQ5m3PABE")
                        .build())
                .localObject(ApiUrlPairObject.builder()
                        .url("http://localhost:8003/api")
                        .authToken("b9267ab8604af650ebe2d5c4fba1b235")
                        .build())
                .repo("/pageant/dom-person-microservice")
                .build();
    }

    /**
     * To override local then use localApiUrl & localApiToken in settings.local
     *
     * @return ApiUrlObject that has url(), graphQl(), authToken() methods
     */
    public static ApiUrlObject publishing()
    {
        return ApiUrlObject.builder()
                .prodUrlObject(ApiUrlPairObject.builder()
                        .url("https://publishing.dom-prod.with.digital/api")
                        .authToken("FuoQkaMNJM8BvzbhwNX8pFWKrRvDW5k6CPj96iFQwqNSG7qv3BXmVa8wdotEG48c")
                        .build())
                .stagingObject(ApiUrlPairObject.builder()
                        .url("https://publishing-stage.dom-non-prod.with.digital/api")
                        .authToken("FuoQkaMNJM8BvzbhwNX8pFWKrRvDW5k6CPj96iFQwqNSG7qv3BXmVa8wdotEG48c")
                        .build())
                .devObject(ApiUrlPairObject.builder()
                        .url("https://publishing-dev.dom-non-prod.with.digital/api")
                        .authToken("S5SmAxtGmSZdogYNT7QYLhxZXz7qNhWGXDqEhtBtYNY3KL4wwdLj4VKV2aPtpAYo")
                        .build())
                .localObject(ApiUrlPairObject.builder()
                        .url("http://localhost:8004/api")
                        .authToken("FYoWuJ5FkxcjTwaKuzMGwfripaqFaf7A")
                        .build())
                .repo(null)
                .build();
    }

    /**
     * To override local then use localApiUrl & localApiToken in settings.local
     *
     * @return ApiUrlObject that has url(), graphQl(), authToken() methods
     */
    public static ApiUrlObject serviceProvider()
    {
        return ApiUrlObject.builder()
                .prodUrlObject(ApiUrlPairObject.builder()
                        .url("https://service-provider.dom-prod.with.digital/api")
                        .authToken("4scs2WJCVxAwikfQ4ji88yskXDSZbSo4nxZLJwYUDCYjVamkTSLu3WoA3nCLhC9U")
                        .build())
                .stagingObject(ApiUrlPairObject.builder()
                        .url("https://service-provider-stage.dom-non-prod.with.digital/api")
                        .authToken("sUFF67LVXW32Re6egN4p4uyWaDq6ZDXK4qnQmAdgL8P57bWkJ6XkzPhPKwYeVsXV")
                        .build())
                .devObject(ApiUrlPairObject.builder()
                        .url("https://service-provider-dev.dom-non-prod.with.digital/api")
                        .authToken("qQrURrBgUsTYBZKA2z8dFP6s6JKZiE6VzFbzTcSEqQM98yMKduDQZEBjWCGtxEE8")
                        .build())
                .localObject(ApiUrlPairObject.builder()
                        .url("http://localhost:8007/api")
                        .authToken("ZOOwUlE0C1gmCtIzfDXw69Mmpfp16q2i")
                        .build())
                .repo("/pageant/dom-service-provider-microservice")
                .build();
    }
}
