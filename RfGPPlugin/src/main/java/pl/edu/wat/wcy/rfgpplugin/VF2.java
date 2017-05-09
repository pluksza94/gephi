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

/*
 * L. P. Cordella, P. Foggia, C. Sansone, M. Vento. "An improved algorithm for
 * matching large graphs". Proc. of the 3rd IAPR TC-15 Workshop on Graphbased
 * Representations in Pattern Recognition. 2001, 149-159.
 *
 * Based on IGraph library implementation.
 *
 * http://igraph.sourceforge.net/
 *
 * Author: Cezary Bartosiak
 * Modified: Paweł Łuksza
 */

package pl.edu.wat.wcy.rfgpplugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.Graphs;
//import org.jgrapht.alg.isomorphism.VF2GraphIsomorphismInspector;
import org.jgrapht.graph.DefaultEdge;

public class VF2 {

    public static boolean containsGraph(Map<UndirectedGraph<Integer, DefaultEdge>, Double> A, UndirectedGraph x, Double increasingValue) {
        boolean result = false;
        for (Map.Entry<UndirectedGraph<Integer, DefaultEdge>, Double> a : A.entrySet()) {
            boolean matched = false;
//            VF2GraphIsomorphismInspector<Integer, DefaultEdge> vf2 = new VF2GraphIsomorphismInspector<>(a.getKey(), x);
//            matched = vf2.isomorphismExists();

            matched = isIsomorphic(a.getKey(), x);
            if (matched) {
                A.replace(a.getKey(), a.getValue() + increasingValue);
                result = true;
            }
        }
        return result;
    }

    public static boolean isIsomorphic(UndirectedGraph graph1, UndirectedGraph graph2) {

        int noOfNodes = graph1.vertexSet().size();
        List<Integer> core1, core2;
        List<Integer> in1, in2;
        int size1 = 0, size2 = 0;
        List<Integer> neis1, neis2;
        int matchedNodes = 0;
        int depth;
        int cand1, cand2;
        int last1, last2;
        Stack<Integer> path;

        if (noOfNodes != graph2.vertexSet().size() || graph1.edgeSet().size() != graph2.edgeSet().size()) {
            return false;
        }

        core1 = new ArrayList<>(noOfNodes);
        core2 = new ArrayList<>(noOfNodes);
        in1 = new ArrayList<>(noOfNodes);
        in2 = new ArrayList<>(noOfNodes);
        for (int i = 0; i < noOfNodes; ++i) {
            core1.add(0);
            core2.add(0);
            in1.add(0);
            in2.add(0);
        }
        path = new Stack<>();

        depth = 0;
        last1 = -1;
        last2 = -1;
        while (depth >= 0) {
            int i;

            cand1 = -1;
            cand2 = -1;
            if (size1 != size2); else if (size1 > 0 && size2 > 0) {
                if (last2 >= 0) {
                    cand2 = last2;
                } else {
                    i = 0;
                    while (cand2 < 0) {
                        if (in2.get(i) > 0 && core2.get(i) == 0) {
                            cand2 = i;
                        }
                        i++;
                    }
                }

                i = last1 + 1;
                while (cand1 < 0 && i < noOfNodes) {
                    if (in1.get(i) > 0 && core1.get(i) == 0) {
                        cand1 = i;
                    }
                    i++;
                }
            } else {
                if (last2 >= 0) {
                    cand2 = last2;
                } else {
                    i = 0;
                    while (cand2 < 0) {
                        if (core2.get(i) == 0) {
                            cand2 = i;
                        }
                        i++;
                    }
                }

                i = last1 + 1;
                while (cand1 < 0 && i < noOfNodes) {
                    if (core1.get(i) == 0) {
                        cand1 = i;
                    }
                    i++;
                }
            }

            if (cand1 < 0 || cand2 < 0) {
                if (depth >= 1) {
                    last2 = path.pop();
                    last1 = path.pop();
                    matchedNodes--;
                    core1.set(last1, 0);
                    core2.set(last2, 0);

                    if (in1.get(last1) != 0) {
                        size1++;
                    }

                    if (in2.get(last2) != 0) {
                        size2++;
                    }

                    neis1 = Graphs.neighborListOf(graph1, last1);
                    for (i = 0; i < neis1.size(); ++i) {
                        int node = neis1.get(i);
                        if (in1.get(node) == depth) {
                            in1.set(node, 0);
                            size1--;
                        }
                    }

                    neis2 = Graphs.neighborListOf(graph2, last2);
                    for (i = 0; i < neis2.size(); ++i) {
                        int node = neis2.get(i);
                        if (in2.get(node) == depth) {
                            in2.set(node, 0);
                            size2--;
                        }
                    }
                }

                depth--;
            } else {
                int xin1 = 0, xin2 = 0;
                boolean end = false;
                neis1 = Graphs.neighborListOf(graph1, cand1);
                neis2 = Graphs.neighborListOf(graph2, cand2);
                if (graph1.degreeOf(cand1) != graph2.degreeOf(cand2)) {
                    end = true;
                }

                for (i = 0; !end && i < neis1.size(); ++i) {
                    int node = neis1.get(i);
                    if (core1.get(node) != 0) {
                        int node2 = core1.get(node) - 1;
                        if (!neis2.contains(node2)) {
                            end = true;
                        }
                    } else {
                        if (in1.get(node) != 0) {
                            xin1++;
                        }
                    }
                }
                for (i = 0; !end && i < neis2.size(); ++i) {
                    int node = neis2.get(i);
                    if (core2.get(node) != 0) {
                        int node2 = core2.get(node) - 1;
                        if (!neis1.contains(node2)) {
                            end = true;
                        }
                    } else {
                        if (in2.get(node) != 0) {
                            xin2++;
                        }
                    }
                }

                if (!end && xin1 == xin2) {
                    depth++;
                    path.push(cand1);
                    path.push(cand2);
                    matchedNodes++;
                    core1.set(cand1, cand2 + 1);
                    core2.set(cand2, cand1 + 1);

                    if (in1.get(cand1) != 0) {
                        size1--;
                    }
                    if (in2.get(cand2) != 0) {
                        size2--;
                    }

                    for (i = 0; i < neis1.size(); ++i) {
                        int node = neis1.get(i);
                        if (in1.get(node) == 0 && core1.get(node) == 0) {
                            in1.set(node, depth);
                            size1++;
                        }
                    }

                    for (i = 0; i < neis2.size(); ++i) {
                        int node = neis2.get(i);
                        if (in2.get(node) == 0 && core2.get(node) == 0) {
                            in2.set(node, depth);
                            size2++;
                        }
                    }
                    last1 = -1;
                    last2 = -1;
                } else {
                    last1 = cand1;
                    last2 = cand2;
                }
            }

            if (matchedNodes == noOfNodes) {
                return true;
            }
        }

        return false;
    }
}
