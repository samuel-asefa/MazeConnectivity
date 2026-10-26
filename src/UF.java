public interface UF {

    // Returns the group containing this node.
    int find(int id);

    // Returns the total number of nodes.
    int numNodes();

    // Returns true if the two nodes belong to the same group.
    boolean connected(int id1, int id2);

    // Combines the groups containing the two nodes.
    void union(int id1, int id2);

    // Returns the current number of groups.
    int numGroups();

    // Prints each node and its group.
    void display();
}