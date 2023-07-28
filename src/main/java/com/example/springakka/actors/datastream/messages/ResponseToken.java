package com.example.springakka.actors.datastream.messages;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseToken implements Command {
    private final Request request;
    private final String token;

    public ResponseToken(Request request, String response) {
        this.request = request;
        this.token = response;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ResponseToken{");
        sb.append("request=").append(request);
        sb.append(", response='").append(token).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
