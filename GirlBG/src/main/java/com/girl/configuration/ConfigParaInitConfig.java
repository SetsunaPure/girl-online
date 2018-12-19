package com.girl.configuration;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.girl.core.entity.PubConfig;
import com.girl.core.mapper.PubConfigMapper;
import com.girl.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ConfigParaInitConfig implements CommandLineRunner {

    @Autowired
    RedisService redisService;

    @Autowired
    PubConfigMapper pubConfigMapper;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(String... args) throws Exception {

        //缓存汇率和提现折扣比例
        PubConfig coinRatePC = new PubConfig();
        coinRatePC.setCode("coinRate");
        String coinRate = pubConfigMapper.selectOne(coinRatePC).getValue();

        PubConfig coinChargeMoneyBiliPC = new PubConfig();
        coinChargeMoneyBiliPC.setCode("coinChargeMoneyBili");
        String coinChargeMoneyBili = pubConfigMapper.selectOne(coinChargeMoneyBiliPC).getValue();

        redisService.set("coinRate", coinRate);
        redisService.set("coinChargeMoneyBili", coinChargeMoneyBili);

        logger.info("缓存消息为：汇率{}，提现折扣比例{}", redisService.get("coinRate"),
                redisService.get("coinChargeMoneyBili"));
    }
}
