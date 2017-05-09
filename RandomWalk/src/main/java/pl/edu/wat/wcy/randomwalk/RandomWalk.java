/*
 * Copyright 2008-2017 Gephi
 * Authors : Paweł Łuksza
 * 
 * This file is part of Gephi.
 *
 * Gephi is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Gephi is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Gephi.  If not, see <http://www.gnu.org/licenses/>.
 */

package pl.edu.wat.wcy.randomwalk;

import java.awt.Color;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.SerializationUtils;
import org.gephi.graph.api.Edge;
import org.gephi.graph.api.Graph;
import org.gephi.graph.api.GraphController;
import org.gephi.graph.api.Node;
import org.openide.util.Lookup;

/**
 *
 * @author Paweł
 */
public class RandomWalk {

    private Graph graph;
    private Map<String, Double> map;
    private Map<String, Map<String, Double>> mapOfEdgeWeights;

    public RandomWalk(String startNode) throws UnsupportedOperationException {
        map = new HashMap<>();
        mapOfEdgeWeights = new HashMap<>();

        graph = Lookup.getDefault().lookup(GraphController.class).getGraphModel().getGraph();

        for (Node node : graph.getNodes()) {
            if (node.getId().equals(startNode)) {
                map.put(startNode, 1.0);
            }
        }

        if (map.isEmpty()) {
            throw new UnsupportedOperationException("Nie ma takiego wierzchołka początkowego!");
        }

        for (Node node : graph.getNodes()) {
            Map<String, Double> tmpMapOfWeights = new HashMap<>();
            double totalWeight = 0.0;
            for (Edge edge : graph.getEdges(node)) {
                if (!edge.isDirected()) {
                    totalWeight += edge.getWeight();
                } else if (edge.getSource().equals(node)) {
                    totalWeight += edge.getWeight();
                }
            }

            for (Edge edge : graph.getEdges(node)) {
                if (!edge.isDirected()) {
                    Node tmpNode = (edge.getSource().equals(node)) ? edge.getTarget() : edge.getSource();
                    tmpMapOfWeights.put(tmpNode.getId().toString(), edge.getWeight() / totalWeight);
                } else if (edge.getSource().equals(node)) {
                    tmpMapOfWeights.put(edge.getTarget().getId().toString(), edge.getWeight() / totalWeight);
                }
            }
            mapOfEdgeWeights.put(node.getId().toString(), tmpMapOfWeights);
        }

        Logger.getLogger(getClass().getSimpleName()).log(Level.INFO, "Weights = {0}", new Object[]{mapOfEdgeWeights.toString()});
    }

    public Map<String, Double> nextStep() {
        Map<String, Double> nextMap = new HashMap<>();

        for (Map.Entry<String, Double> m : map.entrySet()) {
            for (Node node : graph.getNodes()) {
                if (node.getId().toString().equals(m.getKey())) {
                    double currProbability = m.getValue();
                    double nextProbability = 0.0;
                    boolean isAdded = false;
                    for (Map.Entry<String, Double> edge : mapOfEdgeWeights.get(m.getKey()).entrySet()) {
                        isAdded = true;
                        nextProbability = currProbability * edge.getValue();
                        if (nextMap.containsKey(edge.getKey())) {
                            nextProbability += nextMap.get(edge.getKey());
                        }
                        nextMap.put(edge.getKey(), nextProbability);
                    }

                    if (!isAdded) {
                        if (nextMap.containsKey(node.getId().toString())) {
                            nextProbability = nextMap.get(node.getId().toString());
                        }
                        nextProbability += currProbability;
                        nextMap.put(node.getId().toString(), nextProbability);
                    }
                }
            }
        }
        map = (HashMap<String, Double>) SerializationUtils.clone((Serializable) nextMap);
        nextMap.clear();

        for (Map.Entry<String, Double> m : map.entrySet()) {
            Logger.getLogger(getClass().getSimpleName()).log(Level.INFO, "Key: {0}, Value: {1}", new Object[]{m.getKey(), m.getValue()});
        }

        return new TreeMap<>(map);
    }

    public void setColors(Map<String, Double> currMap) {
        for (Node node : graph.getNodes()) {
            node.setColor((currMap.containsKey(node.getId().toString())) ? Color.RED : Color.BLACK);

        }
    }
}
