package com.example.springakka.service;

import akka.actor.typed.javadsl.AskPattern;
import com.example.springakka.ContainerPool;
import com.example.springakka.actors.datastream.messages.Command;
import com.example.springakka.actors.datastream.messages.Request;
import com.example.springakka.actors.datastream.messages.ResponseJson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
public class DataStreamService {

    private final ContainerPool containerPool;

    @Autowired
    public DataStreamService(ContainerPool containerPool) {
        this.containerPool = containerPool;
    }

    public String getData() throws ExecutionException, InterruptedException {
        log.info("asking download..");
        CompletionStage<Command> result =
                AskPattern.ask(
                        containerPool.getPoolQuery(),
                        replyTo -> new Request(replyTo),
                        Duration.ofSeconds(300),
                        containerPool.getSystem().scheduler());
        ResponseJson r = (ResponseJson) result.toCompletableFuture().get();
        log.info("response: {}", r.getResponseJson());
        return r.getResponseJson();
    }


}
