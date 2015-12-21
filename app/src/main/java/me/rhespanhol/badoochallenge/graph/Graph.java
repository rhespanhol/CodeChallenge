package me.rhespanhol.badoochallenge.graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Set;

import me.rhespanhol.badoochallenge.pojo.Rate;

/**
 * Created by rhespanhol on 21/12/15.
 */
public class Graph {


    private static Set<String> sGraphNodes = new HashSet<>();
    private static ArrayList<Edge> sGraphEdges = new ArrayList<>();
    private static Graph sGraph;

    public Graph() {

    }

    public static Graph getGraph(ArrayList<Rate> rates) {

        if (sGraph == null) {
            sGraph = buildGraph(rates);
        }

        return sGraph;

    }


    private static Graph buildGraph(ArrayList<Rate> rates) {

        Graph graph = new Graph();

        for (Rate rate : rates) {
            sGraphNodes.add(rate.getFrom());
            sGraphEdges.add(new Edge(rate));
        }


        return graph;

    }

    public Set<String> getGraphNodes() {
        return sGraphNodes;
    }

    public ArrayList<Edge> getGraphEdges() {
        return sGraphEdges;
    }

    public float getConversionRate(LinkedList<String> path) {

        String from = path.get(0);
        String to;

        float conversionRate = 1f;

        ListIterator<String> stringListIterator = path.listIterator(1);

        while (stringListIterator.hasNext()) {
            to = stringListIterator.next();

            for(Edge edge: sGraphEdges) {
                if( edge.getTo().equals(to) && edge.getFrom().equals(from) ) {
                    conversionRate *= edge.getRate();

                    from = to;
                }
            }

        }

        return conversionRate;

    }

    @Override
    public String toString() {

        String graph = "Graph{ " ;

        ArrayList<String> graphNodes = new ArrayList<>();
        graphNodes.addAll(sGraphNodes);

        for(String node: graphNodes) {
            graph += " graphNodes= " + node;
        }

        for(Edge edge: sGraphEdges) {
            graph += " graphEdges= from: " + edge.getFrom() + " weight: " + edge.getWeight() + " to: " + edge.getTo();
        }


        return graph;
    }
}
