package me.rhespanhol.badoochallenge.graph;

import me.rhespanhol.badoochallenge.pojo.Rate;

/**
 * Created by rhespanhol on 21/12/15.
 */
public class Edge {

    // rates doesnt matter to find the shortest path
    private static final int WEIGHT = 1;

    private String from;
    private String to;
    private float rate;

    public Edge(Rate rate) {
        this.from = rate.getFrom();
        this.to = rate.getTo();
        this.rate = Float.parseFloat(rate.getRate());

    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public float getRate() {
        return rate;
    }

    public int getWeight() {
        return WEIGHT;
    }
}
