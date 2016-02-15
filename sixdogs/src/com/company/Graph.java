package com.company;

import java.util.*;
import com.company.ST;
import com.company.SET;



//Created by eirin on 2/6/2016.
 public class Graph {

 // symbol table: key = string vertex, value = set of neighboring vertices
 private ST<String, SET<String>> st;

 // number of edges
 private int E;

 /**
 * Create an empty graph with no vertices or edges.
 */
public Graph() {
        st = new ST<String, SET<String>>();
        }

/**
 * Create an graph from given input stream using given delimiter.
 */
public Graph(TreeMap<String, List<String>> in) {
        st = new ST<String, SET<String>>();
        List<String> list = new ArrayList<String>();
       for ( String key : in.keySet() ) {
           list = in.get(key);
           for (int l = 0; l < list.size(); l++) {
             addEdge(key,list.get(l));
              // System.out.println(list.get(l));
           }
        }
        }

/**
 * Number of vertices.
 */
public int V() {
        return st.size();
        }

/**
 * Number of edges.
 */
public int E() {
        return E;
        }

// throw an exception if v is not a vertex
private void validateVertex(String v) {
        if (!hasVertex(v)) throw new IllegalArgumentException(v + " is not a vertex");
        }

/**
 * Degree of this vertex.
 */
public int degree(String v) {
        validateVertex(v);
        return st.get(v).size();
        }

/**
 * Add edge v-w to this graph (if it is not already an edge)
 */
public void addEdge(String v, String w) {
        if (!hasVertex(v)) addVertex(v);
        if (!hasVertex(w)) addVertex(w);
        if (!hasEdge(v, w)) E++;
        st.get(v).add(w);
        st.get(w).add(v);
        }

/**
 * Add vertex v to this graph (if it is not already a vertex)
 */
public void addVertex(String v) {
        if (!hasVertex(v)) st.put(v, new SET<String>());
        }


/**
 * Return the set of vertices as an Iterable.
 */
public Iterable<String> vertices() {
        return st.keys();
        }

/**
 * Return the set of neighbors of vertex v as an Iterable.
 */
public Iterable<String> adjacentTo(String v) {
        validateVertex(v);
        return st.get(v);
        }

/**
 * Is v a vertex in this graph?
 */
public boolean hasVertex(String v) {
        return st.contains(v);
        }

/**
 * Is v-w an edge in this graph?
 */
public boolean hasEdge(String v, String w) {
        validateVertex(v);
        validateVertex(w);
        return st.get(v).contains(w);
        }

/**
 * Return a string representation of the graph.
 */
public String toString() {
        StringBuilder s = new StringBuilder();
        for (String v : st) {
        s.append(v + ": ");
        for (String w : st.get(v)) {
        s.append(w + " ");
        }
        s.append("\n");
        }
        return s.toString();
        }

public static void main(String[] args) {
        Graph G = new Graph();
        G.addEdge("A", "B");
        G.addEdge("A", "C");
        G.addEdge("C", "D");
        G.addEdge("D", "E");
        G.addEdge("D", "G");
        G.addEdge("E", "G");
        G.addVertex("H");

        // print out graph
        StdOut.println(G);

        // print out graph again by iterating over vertices and edges
        for (String v : G.vertices()) {
        StdOut.print(v + ": ");
        for (String w : G.adjacentTo(v)) {
        StdOut.print(w + " ");
        }
        StdOut.println();
        }

        }

        }


      //  Copyright © 2000–2011, Robert Sedgewick and Kevin Wayne.
