package com.example.springakka;

import akka.actor.typed.ActorRef;
import akka.actor.typed.ActorSystem;
import com.example.springakka.actors.datastream.messages.Command;
import lombok.Getter;


@Getter
public class ContainerPool {
    private final ActorSystem system;
    private final ActorRef<Command> poolQuery;

    //others pools..
    public ContainerPool(ActorSystem<Void> system, ActorRef<Command> poolQuery) {
        this.system = system;
        this.poolQuery = poolQuery;
    }
}
