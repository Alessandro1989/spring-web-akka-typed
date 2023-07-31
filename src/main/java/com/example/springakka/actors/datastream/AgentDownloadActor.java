package com.example.springakka.actors.datastream;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import com.example.springakka.actors.datastream.messages.QueryDownload;
import com.example.springakka.actors.datastream.messages.ResponseDownload;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class AgentDownloadActor extends AbstractBehavior<QueryDownload> {

    public static Behavior<QueryDownload> create() {
        return Behaviors.setup(AgentDownloadActor::new);
    }
    private AgentDownloadActor(ActorContext<QueryDownload> context) {
        super(context);
    }

    @Override
    public Receive<QueryDownload> createReceive() {
        return newReceiveBuilder().onMessage(QueryDownload.class, this::onQuery).build();
    }

    private Behavior<QueryDownload> onQuery(QueryDownload query) {
        System.out.println("request to download actor: " + this.getContext().getSelf());
        if(StringUtils.isBlank(query.getToken())){
            throw new RuntimeException("token not valid");
        }
        query.getReplyTo().tell(new ResponseDownload(query.getRequest(),RandomStringUtils.randomAlphabetic(100)));
        return this;
    }
}
