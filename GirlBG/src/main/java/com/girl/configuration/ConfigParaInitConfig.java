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

import java.util.List;

@Component
public class ConfigParaInitConfig implements CommandLineRunner {

    @Autowired
    RedisService redisService;

    @Autowired
    PubConfigMapper pubConfigMapper;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(String... args) throws Exception {

        List<PubConfig> pubConfigs = pubConfigMapper.selectList(new EntityWrapper<PubConfig>());

        for(PubConfig pc : pubConfigs){
            if (pc.getCode().equals("coinRate")){
                redisService.set("coinRate", pc.getValue());
            } else if(pc.getCode().equals("coinChargeMoneyBili")){
                redisService.set("coinChargeMoneyBili", pc.getValue());
            } else if(pc.getCode().equals("QINIU_ACCESSKEY")){
                redisService.set("QINIU_ACCESSKEY", pc.getValue());
            } else if(pc.getCode().equals("QINIU_SECRETKEY")){
                redisService.set("QINIU_SECRETKEY", pc.getValue());
            } else if(pc.getCode().equals("QINIU_DOMAINNAMEimages")){
                redisService.set("QINIU_DOMAINNAMEimages", pc.getValue());
            } else if(pc.getCode().equals("J_PUSH_APP_KEY")){
                redisService.set("J_PUSH_APP_KEY", pc.getValue());
            } else if(pc.getCode().equals("J_PUSH_APP_SECRET")) {
                redisService.set("J_PUSH_APP_SECRET", pc.getValue());
            }
        }

//        //缓存汇率和提现折扣比例
//        PubConfig coinRatePC = new PubConfig();
//        coinRatePC.setCode("coinRate");
//        String coinRate = pubConfigMapper.selectOne(coinRatePC).getValue();
//
//        PubConfig coinChargeMoneyBiliPC = new PubConfig();
//        coinChargeMoneyBiliPC.setCode("coinChargeMoneyBili");
//        String coinChargeMoneyBili = pubConfigMapper.selectOne(coinChargeMoneyBiliPC).getValue();
//
//        PubConfig qinieAccesskeyPC = new PubConfig();
//        coinChargeMoneyBiliPC.setCode("QINIU_ACCESSKEY");
//        String QINIU_ACCESSKEY = pubConfigMapper.selectOne(qinieAccesskeyPC).getValue();
//
//
//        redisService.set("coinRate", coinRate);
//        redisService.set("coinChargeMoneyBili", coinChargeMoneyBili);

        logger.info("缓存消息为：汇率{}，提现折扣比例{}", redisService.get("coinRate"),
                redisService.get("coinChargeMoneyBili"));

    }
}
