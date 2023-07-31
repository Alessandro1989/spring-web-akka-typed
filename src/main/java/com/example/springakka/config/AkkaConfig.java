package com.example.springakka.config;

import akka.NotUsed;
import akka.actor.typed.ActorRef;
import akka.actor.typed.ActorSystem;
import akka.actor.typed.Behavior;
import akka.actor.typed.SupervisorStrategy;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.PoolRouter;
import akka.actor.typed.javadsl.Routers;
import com.example.springakka.ContainerPool;
import com.example.springakka.actors.datastream.AgentTokenActor;
import com.example.springakka.actors.datastream.QueryActor;
import com.example.springakka.actors.datastream.messages.Command;
import com.example.springakka.actors.datastream.messages.QueryToken;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


@Configuration
public class AkkaConfig {

    @Bean
    public ContainerPool initializeContainerPoolQuery() throws InterruptedException, ExecutionException {
        int poolSize = 6;
        CompletableFuture<ContainerPool> completableFuture = new CompletableFuture<>();
        Behavior<NotUsed> rootBehavoiur = Behaviors.setup(context -> {
            PoolRouter<Command> pool = Routers.pool(
                    poolSize,
                    Behaviors.supervise(QueryActor.create()).onFailure(SupervisorStrategy.restart())).withRoundRobinRouting();
            ActorRef<Command> poolQuery = context.spawn(pool, "query-pool");
            ActorRef<QueryToken> tokenActorRef = context.spawn(Behaviors.supervise(AgentTokenActor.create())
                    .onFailure(SupervisorStrategy.resume()), "tokenActor");
            ContainerPool containerPool = new ContainerPool(context.getSystem(), poolQuery, tokenActorRef);
            completableFuture.complete(containerPool);
            return Behaviors.empty();
        });
        ActorSystem.create(rootBehavoiur, "HelloAkkaHttpServer");
        ContainerPool pool = completableFuture.get();
        return pool;
    }

}