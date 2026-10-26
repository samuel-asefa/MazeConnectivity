public class UF_FastFind implements UF {

private int[] group;
private int numGroups;

public UF_FastFind(int numNodes) {
group = new int[numNodes];
for (int i = 0; i < numNodes; i++) {
group[i] = i;
}
numGroups = numNodes;
}

public int find(int x) {
return group[x];
}

public void union(int a, int b) {
int groupA = group[a];
int groupB = group[b];
if (groupA == groupB) return;

for (int i = 0; i < group.length; i++) {
if (group[i] == groupB) group[i] = groupA;
}
numGroups--;
}

public int numNodes() { return group.length; }

public boolean connected(int id1, int id2) {
return group[id1] == group[id2];
}

public int numGroups() { return numGroups; }

public void display() {
for (int i = 0; i < group.length; i++) {
System.out.println("Node " + i + ": Group " + group[i]);
}
}
public static void main(String[] args){
       UF uf = new UF_FastFind(10);
       uf.union(0,2);
       uf.union(2,4);
       uf.union(5,7);
       uf.display();
       System.out.println("Num Groups -> "+uf.numGroups());
       uf.union(2,5);
       uf.display();
       System.out.println("Num Groups -> "+uf.numGroups());
    }
}
