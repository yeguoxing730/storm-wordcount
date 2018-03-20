package com.storm.wordcount.bolt;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.HashMap;
import java.util.Map;


public class LineBolt extends BaseBasicBolt {
    private HashMap<String, Integer> wordMap = new HashMap<String, Integer>();

    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {

    }

    public void execute(Tuple tuple, BasicOutputCollector collector) {
        //从tuple中读取单词
        String line = tuple.getStringByField("line");

        String[] words = line.split("\\s+");
        for(String word:words){
            System.out.println("line---------"+line+"--------"+word);
            collector.emit(new Values(word));
        }

    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("lineword"));
    }
}
