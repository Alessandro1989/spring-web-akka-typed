package com.example.springakka.actors.datastream.messages;

import akka.actor.typed.ActorRef;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QueryDownload implements Command {
    private final Request request;
    private final String token;
    private final ActorRef<Command> replyTo;

    public QueryDownload(Request request, String token, ActorRef<Command> replyTo) {
        this.request = request;
        this.token = token;
        this.replyTo = replyTo;
    }


}
