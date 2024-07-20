package com.company;

import java.util.*;

public class droneOperation {
    public void takeOff(){
        System.out.println("Drone is taking off");
    }

    public void returnToHome(){
        System.out.println("Drone is returning to Home");
    }

    public void land(){
        System.out.println("Drone is landing");
    }

    public void failure(){
        System.out.println("Drone failure");
    }

    public static class TakeOff extends droneOperation {
        @Override
        public void takeOff(){
            super.takeOff();
        }
    }

    public static class ReturnToHome extends droneOperation {
        @Override
        public void returnToHome() {
            super.returnToHome();
        }
    }

    public static class Land extends droneOperation {
        @Override
        public void land() {
            super.land();
        }
    }

    public static class Failure extends droneOperation {
        @Override
        public void failure() {
            super.failure();
        }
    }

    public static class Survey {
        public void surveyRoute(String path){
            System.out.println("The survey route is " + path);
        }
    }

    public static class MissionPlanning {
        final int numNodes = 100;
        final int[][] graph;
        Random rand = new Random();

        public MissionPlanning() {
            graph = new int[numNodes][numNodes];
            generateGraph();
        }

        private void generateGraph() {
            for (int i = 0; i < numNodes; i++) {
                for (int j = i + 1; j < numNodes; j++) {
                    int weight = rand.nextInt(100) + 1;
                    graph[i][j] = weight;
                    graph[j][i] = weight; // Assuming an undirected graph
                }
            }
        }

        public String findShortest(int start, int end) {
            int[] dist = new int[numNodes];
            boolean[] visited = new boolean[numNodes];
            int[] prev = new int[numNodes];
            PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(node -> node.distance));

            Arrays.fill(dist, Integer.MAX_VALUE);
            Arrays.fill(prev, -1);
            dist[start] = 0;
            pq.add(new Node(start, 0));

            while (!pq.isEmpty()) {
                Node node = pq.poll();
                int u = node.id;

                if (visited[u]) {
                    continue;
                }
                visited[u] = true;

                for (int v = 0; v < numNodes; v++) {
                    if (graph[u][v] != 0 && !visited[v]) {
                        int newDist = dist[u] + graph[u][v];
                        if (newDist < dist[v]) {
                            dist[v] = newDist;
                            prev[v] = u;
                            pq.add(new Node(v, newDist));
                        }
                    }
                }
            }
            return reconstructPath(start, end, prev);
        }

        private String reconstructPath(int start, int end, int[] prev) {
            List<Integer> path = new ArrayList<>();
            for (int at = end; at != -1; at = prev[at]) {
                path.add(at);
            }
            Collections.reverse(path);
            return path.toString();
        }

        private static class Node {
            int id, distance;

            Node(int id, int distance) {
                this.id = id;
                this.distance = distance;
            }
        }
    }

    public static void main(String[] args) {
        droneOperation takeOff = new TakeOff();
        droneOperation returnToHome = new ReturnToHome();
        droneOperation land = new Land();
        droneOperation failure = new Failure();

        takeOff.takeOff();

        MissionPlanning missionPlanning = new MissionPlanning();
        int start = 0;
        int end = 99;
        String path = missionPlanning.findShortest(start, end);

        Survey survey = new Survey();
        survey.surveyRoute(path);

        returnToHome.returnToHome();
        land.land();
    }
}
