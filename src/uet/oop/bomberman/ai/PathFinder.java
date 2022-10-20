package uet.oop.bomberman.ai;

import java.util.ArrayList;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

public class PathFinder {
        Node[][] node;
        ArrayList<Node> openList = new ArrayList<>();
        public ArrayList<Node> pathList = new ArrayList<>();
        Node startNode, goalNode, currentNode;
        boolean goalReached = false;
        int step = 0;

        public PathFinder() {
            instantiateNodes();
        }
        public void instantiateNodes() {
            node = new Node[BombermanGame.WIDTH][BombermanGame.HEIGHT];
            int col =0;
            int row =0;
            while(col < BombermanGame.WIDTH && row < BombermanGame.HEIGHT) {
                    node[col][row] = new Node(col, row);
                    col++;
                    if(col == BombermanGame.WIDTH) {
                            col = 0;
                            row++;
                    }
            }
        }
        public void resetNodes() {
                int col =0;
                int row =0;
                while(col < BombermanGame.WIDTH && row < BombermanGame.HEIGHT) {
                        node[col][row].open = false;
                        node[col][row].checked = false;
                        node[col][row].solid = false;
                        col++;
                        if(col == BombermanGame.WIDTH) {
                                col = 0;
                                row++;
                        }
                }
                openList.clear();
                pathList.clear();
                goalReached = false;
                step = 0;
        }
        public void setNode(int startCol, int startRow, int goalCol, int goalRow) {
                resetNodes();
                startNode = node[startCol][startRow];
                currentNode = startNode;
                goalNode = node[goalCol][goalRow];
                openList.add(currentNode);
                int col =0;
                int row =0;
                while(col < BombermanGame.WIDTH && row < BombermanGame.HEIGHT) {
                        int tileNum  = BombermanGame.map[col][row];
                        if(tileNum == 0 || tileNum == 3) {
                                node[col][row].solid = true;
                        }
                        for(int i =0; i< BombermanGame.bricks.size(); i++) {
                                if(BombermanGame.bricks.get(i) != null) {
                                        int bCol = BombermanGame.bricks.get(i).getX()/Sprite.SCALED_SIZE;
                                        int bRow = BombermanGame.bricks.get(i).getY()/Sprite.SCALED_SIZE;
                                        node[bCol][bRow].solid = true;
                                }
                        }
                        getCost(node[col][row]);
                        col++;
                        if(col == BombermanGame.WIDTH) {
                                col = 0;
                                row++;
                        }
                }
        }
        public void getCost(Node node) {
                //G cost
                int xDistance = Math.abs(node.col - startNode.col);
                int yDistance = Math.abs(node.row - startNode.row);
                node.gCost = xDistance + yDistance;
                // H cost
                xDistance = Math.abs(node.col - goalNode.col);
                yDistance = Math.abs(node.row - goalNode.row);
                node.hCost = xDistance + yDistance;
                // F cost
                node.fCost = node.gCost + node.hCost;
        }
        public boolean search() {
                while(goalReached == false && step < 500) {
                        int col = currentNode.col;
                        int row = currentNode.row;
                        //Check the current node
                        currentNode.checked = true;
                        openList.remove(currentNode);

                        //Open the up node
                        if(row - 1 >= 0) {
                                openNode(node[col][row-1]);
                        }
                        if(col - 1 >= 0) {
                                openNode(node[col-1][row]);
                        }
                        if(row + 1 < BombermanGame.HEIGHT) {
                                openNode(node[col][row+1]);
                        }
                        if(col + 1 < BombermanGame.WIDTH) {
                                openNode(node[col+1][row]);
                        }
                        //find the best node
                        int bestNodeIndex = 0;
                        int bestNodefCost = 999;
                        for(int i = 0; i < openList.size(); i++) {
                                //Check if this node's Fcost is better
                                if(openList.get(i).fCost < bestNodefCost) {
                                        bestNodefCost = openList.get(i).fCost;
                                        bestNodeIndex =  i;
                                }
                                //if fCost is equal check the gCost
                                else if(openList.get(i).fCost == bestNodefCost) {
                                        if(openList.get(i).gCost < openList.get(bestNodeIndex).gCost) {
                                                bestNodeIndex = i;
                                        }
                                }
                        }
                        if(openList.isEmpty()) {
                                break;
                        }

                        //After the loop the bestNode is next step(currentnode)
                        currentNode = openList.get(bestNodeIndex);
                        if(currentNode == goalNode) {
                                goalReached = true;
                                trackThePath();
                        }
                        step++;
                }
                return goalReached;
        }
        public void openNode(Node node) {
                if(node.open == false && node.checked == false && node.solid == false) {
                        node.open = true;
                        node.parent = currentNode;
                        openList.add(node);
                }
        }
        public void trackThePath() {
                Node current = goalNode;
                while (current != startNode) {
                        pathList.add(0, current);
                        current = current.parent;
                }
        }
}
