package com.zhangbao.eblog.template;

import com.zhangbao.eblog.common.template.DirectiveHandler;
import com.zhangbao.eblog.common.template.TemplateDirective;
import com.zhangbao.eblog.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 本周热议
 */
@Component
public class RankTemplate extends TemplateDirective {

    @Autowired
    RedisUtil redisUtil;

    @Override
    public String getName() {
        return "hots";
    }

    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        String rankKey = "week:rank";//
        List<Map> list = new ArrayList<>();
        Set<ZSetOperations.TypedTuple> typedTuples = redisUtil.getZSetRank(rankKey, 0, 6);
        for (ZSetOperations.TypedTuple typedTuple : typedTuples) {
            Map<String,Object> map = new HashMap<>();
            String key = "rank:post:"+typedTuple.getValue();
            Object title = redisUtil.hget(key, "post:title");
            map.put("id",typedTuple.getValue());
            map.put("title",title);
            map.put("commentCount",typedTuple.getScore());
            list.add(map);
        }
        handler.put(RESULTS,list).render();
    }
}
