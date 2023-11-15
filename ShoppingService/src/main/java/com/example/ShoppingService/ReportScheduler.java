package com.example.ShoppingService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.cloud.sleuth.SpanCustomizer;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.cloud.sleuth.instrument.async.LazyTraceExecutor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableScheduling
public class ReportScheduler {

    private final BeanFactory beanFactory;

    private static final ExecutorService executorService = Executors.newFixedThreadPool(5);
    private static final Random r = new Random();

    private static final Logger log = LoggerFactory.getLogger(ReportScheduler.class);

    private final SpanCustomizer spanCustomizer;

    public ReportScheduler(BeanFactory beanFactory, SpanCustomizer spanCustomizer) {
        this.beanFactory = beanFactory;
        this.spanCustomizer = spanCustomizer;
    }

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
    public void scheduleReportGeneration() {
        LazyTraceExecutor sleuthExecutor = new LazyTraceExecutor(beanFactory, executorService);
        sleuthExecutor.execute(() -> {
            try {
                int seconds = r.nextInt(40);
                Thread.sleep(seconds * 1000);
                spanCustomizer.tag("duration", String.valueOf(seconds));
                log.info("Finished generating report in {}s", seconds);
            } catch (InterruptedException e) {
                log.error("Error while generating report");
                throw new RuntimeException(e);
            }
        });
    }
}
