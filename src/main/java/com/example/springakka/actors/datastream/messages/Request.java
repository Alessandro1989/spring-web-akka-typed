package com.example.springakka.actors.datastream.messages;

import akka.actor.typed.ActorRef;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Request implements Command {
    private final ActorRef<Command> replyTo;
    private final ActorRef<QueryToken> tokenActor;

    public Request(ActorRef<Command> replyTo, ActorRef<QueryToken> tokenActor) {
        this.replyTo = replyTo;
        this.tokenActor = tokenActor;
    }
}
