package com.example.springakka.actors.datastream.messages;

import akka.actor.typed.ActorRef;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Request implements Command {
    private final ActorRef<Command> replyTo;

    public Request(ActorRef<Command> replyTo) {
        this.replyTo = replyTo;
    }
}
