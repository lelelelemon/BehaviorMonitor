package birch;

import java.util.ArrayList;

public class NonleafNode extends TreeNode{

	private int B=5;
    private ArrayList<TreeNode> children;
 
    public NonleafNode() {
        children=new ArrayList<TreeNode>();
    }
 
    public NonleafNode(double[] data) {
        super(data);
    }
 
    // �ڵ����
    public void split() {
        // �ҵ�������Զ���������ӽڵ�
        int c1 = 0;
        int c2 = 0;
        double maxDist = 0;
        int len = this.getChildren().size();
        for (int i = 0; i < len - 1; i++) {
            for (int j = i + 1; j < len; j++) {
                double dist = this.getChildren().get(i)
                        .getDistanceTo(this.getChildren().get(j));
                if (dist > maxDist) {
                    maxDist = dist;
                    c1 = i;
                    c2 = j;
                }
            }
        }
        // �Ծ�����Զ�ĺ��ӽڵ�Ϊ���ģ���B+1�����ӷ�Ϊ������ء�����һ�������������ڵ�ĺ��ӣ�����һ����Ҫ�´���һ���ڵ�����������
        NonleafNode newNode = new NonleafNode();
        newNode.addChild(this.getChildren().get(c2));
        //������ڵ��Ѿ���Root�ڵ㣬����Ҫ����һ���µ�Root�ڵ�
        if(this.getParent()==null){
            NonleafNode root= new NonleafNode();
            root.setN(this.getN());
            root.setLS(this.getLS());
            root.setSS(this.getSS());
            root.addChild(this);
            this.setParent(root);
        }
        newNode.setParent(this.getParent());
        ((NonleafNode)this.getParent()).addChild(newNode);
        for (int i = 0; i < len; i++) {
            if (i != c1 && i != c2) {
                if (this.getChildren().get(i)
                        .getDistanceTo(this.getChildren().get(c2)) < this
                        .getChildren().get(i)
                        .getDistanceTo(this.getChildren().get(c1))) {
                    newNode.addChild(this.getChildren().get(i));
                }
            }
        }
        for (TreeNode entry : newNode.getChildren()) {
            newNode.addCF(entry, true);
            this.deleteChild(entry);
            this.addCF(entry, false);
        }
        //������ڵ���ѵ��¸��ڵ�ĺ����������˷�֦���ӣ��������ڵ����
        NonleafNode pn=(NonleafNode)this.getParent();
        if(pn.getChildren().size()>B){
            this.getParent().split();
        }
    }
    public void absorbSubCluster(MinCluster cluster){
        //�ӱ��ڵ�ĺ�����Ѱ����cluster������ӽڵ�
        CF cf=cluster.getCf();
        int nearIndex=0;
        double minDist=Double.MAX_VALUE;
        for(int i=0;i<this.getChildren().size();i++){
            double dist=cf.getDistanceTo(this.getChildren().get(i));
            if(dist<minDist){
                nearIndex=i;
            }
        }
        //���Ǹ�������ӽڵ�absorb������µ���cluster
        this.getChildren().get(nearIndex).absorbSubCluster(cluster);
    }
 
    public ArrayList<TreeNode> getChildren() {
        return children;
    }
 
    public void setChildren(ArrayList<TreeNode> children) {
        this.children = children;
    }
 
    public void addChild(TreeNode child) {
        this.children.add(child);
    }
 
    public void deleteChild(TreeNode child) {
        this.children.remove(children.indexOf(child));
    }
 
    public int getB() {
        return B;
    }
 
    public void setB(int b) {
        B = b;
    }
}
