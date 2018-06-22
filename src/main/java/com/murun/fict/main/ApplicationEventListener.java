package com.murun.fict.main;


import com.murun.fict.model.LegalEntity;
import com.murun.fict.model.LegalEntityRedis;
import com.murun.fict.repository.LegalEntityRedisRepository;
import com.murun.fict.repository.LegalEntityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ApplicationEventListener {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private LegalEntityRedisRepository legalEntityRedisRepository;

    @Resource
    private LegalEntityRepository legalEntityRepository;

    @Resource
    private RedisConnectionFactory redisConnectionFactory;

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {

        logger.info("onApplicationEvent ContextRefreshedEvent ");
        redisConnectionFactory.getConnection().serverCommands().flushDb();
        System.out.println("1****************************************************************************************************");
        Iterable<LegalEntity> it = legalEntityRepository.findAll();
        System.out.println("2****************************************************************************************************");

        it.forEach( le -> { if ( le.getLegalEntityType().getLegalEntityTypeId() ==4 ) {  legalEntityRedisRepository.save(LegalEntityRedis.from(le));} });


        System.out.println("3****************************************************************************************************");
        legalEntityRedisRepository.findAll().forEach(System.out::println);
        System.out.println("4****************************************************************************************************");
    }
}

