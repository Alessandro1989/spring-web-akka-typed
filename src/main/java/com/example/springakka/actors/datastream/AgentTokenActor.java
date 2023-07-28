package com.example.springakka.actors.datastream;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import com.example.springakka.actors.datastream.messages.QueryToken;
import com.example.springakka.actors.datastream.messages.ResponseToken;
import org.apache.commons.lang3.RandomStringUtils;


public class AgentTokenActor extends AbstractBehavior<QueryToken> {

    public static Behavior<QueryToken> create() {
        return Behaviors.setup(AgentTokenActor::new);
    }
    private AgentTokenActor(ActorContext<QueryToken> context) {
        super(context);
    }

    @Override
    public Receive<QueryToken> createReceive() {
        return newReceiveBuilder().onMessage(QueryToken.class, this::onQuery).build();
    }

    private Behavior<QueryToken> onQuery(QueryToken query) {
        System.out.println("request to token actor: " + this.getContext().getSelf());
        ResponseToken r = new ResponseToken(query.getRequest(),RandomStringUtils.randomAlphabetic(10));
        query.getReplyTo().tell(r);
        return this;
    }
}