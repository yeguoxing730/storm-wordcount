package com.storm.wordcount.topology;

import com.storm.wordcount.bolt.CountBolt;
import com.storm.wordcount.bolt.LineBolt;
import com.storm.wordcount.spout.WordReader;
import com.storm.wordcount.spout.WordSpout;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;

/**
 * Created by yeguoxing on 2018/3/19.
 */
public class MainFileTopology {
    public void runLocal(int waitSeconds,String[] args) {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("wordSpout", new WordSpout(), 1);
        builder.setSpout("fileSpout", new WordReader(), 1);
        builder.setBolt("lineBolt", new LineBolt(), 1).shuffleGrouping("fileSpout");

        builder.setBolt("countBolt", new CountBolt(), 1).shuffleGrouping("wordSpout").shuffleGrouping("lineBolt");

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
        MainFileTopology topology = new MainFileTopology();
        topology.runLocal(16,args);
    }
}
