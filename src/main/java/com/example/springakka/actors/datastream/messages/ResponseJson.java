package com.example.springakka.actors.datastream.messages;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseJson implements Command {
    private final String responseJson;

    public ResponseJson(String responseJson) {
        this.responseJson = responseJson;
    }
}
