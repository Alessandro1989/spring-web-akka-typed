package com.example.springakka.actors.datastream.messages;

import akka.actor.typed.ActorRef;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QueryToken implements Command {
    private final Request request;
    private final ActorRef<Command> replyTo;

    public QueryToken(Request request, ActorRef<Command> replyTo) {
        this.request = request;
        this.replyTo = replyTo;
    }


}
