package io.gabrielcosta.githubpopular.utils;

import okhttp3.Headers;

public final class RemainRequest {

    public static int numberOfRemainRequest(final Headers headers) {
        return  Integer.parseInt(headers.get("X-RateLimit-Remaining"));
    }

    public static boolean hasRequestToMake(final Headers headers) {
        return numberOfRemainRequest(headers) == 0;
    }

}
