package com.fitness.aiservice.service;

import com.fitness.aiservice.model.Activity;
import com.fitness.aiservice.model.Recommendation;
import com.fitness.aiservice.repository.RecommendationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.amqp.rabbit.annotation.RabbitListener;


@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityMessageListener {

    private final ActivityAIService aiService;
    private final RecommendationRepository recommendationRepository;

@RabbitListener(queues = "activity.queue")
public void processActivity(Activity activity){
//    log.info("STEP 1: Received activity {}", activity.getId());

    Recommendation recommendation = aiService.generateRecommendation(activity);
//    log.info("STEP 2: Generated recommendation {}", recommendation);

    recommendationRepository.save(recommendation);
    log.info("STEP 3: Saved to MongoDB");
}
}
