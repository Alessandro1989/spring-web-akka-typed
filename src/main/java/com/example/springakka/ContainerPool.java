package com.example.springakka;

import akka.actor.typed.ActorRef;
import akka.actor.typed.ActorSystem;
import com.example.springakka.actors.datastream.messages.Command;
import com.example.springakka.actors.datastream.messages.QueryToken;
import lombok.Getter;


@Getter
public class ContainerPool {
    private final ActorSystem system;
    private final ActorRef<Command> poolQuery;
    private final ActorRef<QueryToken> tokenActor;

    //others pools..
    public ContainerPool(ActorSystem<Void> system, ActorRef<Command> poolQuery, ActorRef<QueryToken> tokenActor) {
        this.system = system;
        this.poolQuery = poolQuery;
        this.tokenActor = tokenActor;
    }
}
