package com.storm.wordcount.topology;

import com.storm.wordcount.bolt.CountBolt;
import com.storm.wordcount.spout.WordSpout;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;


public class MainTopology {
    public void runLocal(int waitSeconds,String[] args) {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("wordSpout", new WordSpout(), 1);
        builder.setBolt("countBolt", new CountBolt(), 1).shuffleGrouping("wordSpout");

        Config config = new Config();
        config.put("wordsFile", args[0]);
        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("word_count", config, builder.createTopology());

        try {
            Thread.sleep(waitSeconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        cluster.killTopology("word_count");
        cluster.shutdown();
    }

    public static void main(String[] args) {
        MainTopology topology = new MainTopology();
        topology.runLocal(60,args);
    }
}
