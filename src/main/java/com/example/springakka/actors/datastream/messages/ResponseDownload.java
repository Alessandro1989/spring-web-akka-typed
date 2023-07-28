package com.example.springakka.actors.datastream.messages;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDownload implements Command {
    private final Request request;
    private final String data;

    public ResponseDownload(Request request, String response) {
        this.request = request;
        this.data = response;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ResponseToken{");
        sb.append("request=").append(request);
        sb.append(", response='").append(data).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
