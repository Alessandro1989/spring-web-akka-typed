package com.example.springakka.actors.datastream;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import com.example.springakka.actors.datastream.messages.QueryToken;
import com.example.springakka.actors.datastream.messages.ResponseToken;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;



public class AgentTokenActor extends AbstractBehavior<QueryToken> {
    private String token;
    public static Behavior<QueryToken> create() {
        //BeanUtils.getBean(..); for passing bean to constructors (example a configuration bean)
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
        if(StringUtils.isBlank(token)){
            token = RandomStringUtils.randomAlphabetic(10);
        }
        ResponseToken r = new ResponseToken(query.getRequest(),token);
        query.getReplyTo().tell(r);
        return this;
    }
}
