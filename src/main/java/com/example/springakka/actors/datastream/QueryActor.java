package com.example.springakka.actors.datastream;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.SupervisorStrategy;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import com.example.springakka.actors.datastream.messages.*;

public class QueryActor extends AbstractBehavior<Command> {

    public static Behavior<Command> create() {
        return Behaviors.setup(QueryActor::new);
    }
    private final ActorRef<QueryDownload> downloadActor;
    private QueryActor(ActorContext<Command> context) {
        super(context);
        System.out.println("query actory: "+ this.getContext().getSelf());
        downloadActor = context.spawn(Behaviors.supervise(AgentDownloadActor.create()).onFailure(SupervisorStrategy.restart()),
                "downloadAgent");
        System.out.println("download actor: " + downloadActor);
    }

    @Override
    public Receive<Command> createReceive() {
        return newReceiveBuilder().onMessage(Request.class, this::onRequest)
                .onMessage(ResponseToken.class,this::onResponseToken)
                .onMessage(ResponseDownload.class,this::onResponseDownload).build();
    }


    private Behavior<Command> onResponseToken(ResponseToken response) {
        String token = response.getToken();
        System.out.println("token: "+ token);
        downloadActor.tell(new QueryDownload(response.getRequest(),token, this.getContext().getSelf()));
        return this;
    }

    private Behavior<Command> onResponseDownload(ResponseDownload response) {
        String data = response.getData();
        System.out.println("data: "+ data);
        String fakeJsonResponse = "{ 'data' : '"+data+"'}";
        response.getRequest().getReplyTo().tell(new ResponseJson(fakeJsonResponse));
        return this;
    }


    private Behavior<Command> onRequest(Request request) {
        System.out.println("request to query actor: " + this.getContext().getSelf());
        request.getTokenActor().tell(new QueryToken(request, this.getContext().getSelf()));
        return this;
    }
}
