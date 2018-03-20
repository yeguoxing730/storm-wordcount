package com.storm.wordcount.bolt;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by yeguoxing on 2018/3/19.
 */
public class CountBolt extends BaseRichBolt {
    private HashMap<String, Integer> wordMap = new HashMap<String, Integer>();

    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {

    }

    public void execute(Tuple tuple) {
        String word = "";
        if(tuple.contains("lineword") && tuple.getStringByField("lineword")!=null){
            word = tuple.getStringByField("lineword");
            System.out.println("get line word--------"+word);
        }
        if(tuple.contains("word") && tuple.getStringByField("word") != null){
            word = tuple.getStringByField("word");
            System.out.println("get word--------"+word);
        }
        //从tuple中读取单词
//        String word = tuple.getStringByField("word");

        //计数
        int num;
        if (wordMap.containsKey(word)) {
            num = wordMap.get(word);
        } else {
            num = 0;
        }
        wordMap.put(word, 1 + num);

        //输出展示
        Set<String> keys = wordMap.keySet();
        for (String key : keys) {
            System.out.print(key + ":" + wordMap.get(key) + ",");
        }
        System.out.println();
    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }
}
