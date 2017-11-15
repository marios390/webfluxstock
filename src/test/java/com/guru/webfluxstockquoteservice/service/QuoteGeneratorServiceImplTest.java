package com.guru.webfluxstockquoteservice.service;

import com.guru.webfluxstockquoteservice.model.Quote;
import org.junit.Before;
import org.junit.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

import static org.junit.Assert.*;

public class QuoteGeneratorServiceImplTest {

    QuoteGeneratorServiceImpl quoteGeneratorService = new QuoteGeneratorServiceImpl();

    @Before
    public void setUp() throws Exception{

    }

    @Test
    public void fetchQuotesStream() throws Exception{

        Flux<Quote> quoteFlux = quoteGeneratorService.fetchQuoteStream(Duration.ofMillis(100L));

        quoteFlux.take(22000)
                .subscribe(System.out::println);


    }

    @Test
    public void fetchQuoteStreamCountDown() throws Exception{
         Flux<Quote> quoteFlux = quoteGeneratorService.fetchQuoteStream(Duration.ofMillis(100L));
        Consumer<Quote> println = System.out::println;
        Consumer<Throwable> errorhandler = e -> System.out.println("Some error occured");

        CountDownLatch countDownLatch  = new CountDownLatch(1);
        Runnable alldone =  ()-> countDownLatch.countDown();

        quoteFlux.take(10)
                .subscribe(println,errorhandler,alldone);

        countDownLatch.await();
    }


}