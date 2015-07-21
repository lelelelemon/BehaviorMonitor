package birch;

import java.util.ArrayList;

public class LeafNode extends TreeNode{

	/**
	 * ÿ��Ҷ�ӽڵ㣨LeafNode�������10��MinCluster
	 */
    private int L=20;
    
    /**
     *ÿһ��MinCluster��ֱ�����ܳ���T=2.8 
     */
    private double T=1.5;
    
    /**
     * ��ǰҶ�ӽڵ�ĺ��ӣ�ÿ�����Ӷ��������ɸ�MinCluster��ʽ������
     */
    private ArrayList<MinCluster> children;
    
   
    //Ҷ�ӽڵ�֮��ҪҪͨ��˫���������Ӽ�Ҫ�м�¼��ǰҶ�ӽڵ�������ֵܽڵ�ı���
     
    /**
     * ��ǰҶ�ӽڵ��ǰһ��Ҷ�ӽڵ㣨�����Ϊ�����ֵܽڵ㣩
     */
    private LeafNode pre;
    
    /**
     * ��ǰҶ�ӽڵ�ĺ�һ��Ҷ�ӽڵ㣨�����Ϊ�����ֵܽڵ㣩
     */
    private LeafNode next;
 
    public LeafNode() {
        children=new ArrayList<MinCluster>();
    }
 
    public LeafNode(double[] data) {
        super(data);
    }
 
    // �ڵ����
    public void split() {
        // �ҵ�������Զ���������ӽڵ�
        int c1 = 0;
        int c2 = 0;
        double maxDist = 0;
        //��õ�ǰ�ڵ�ĺ��ӽڵ�ĸ���len��Ȼ�������len�����ӽڵ㣬�ҵ����ӽڵ��о�����Զ����������
        int len = this.getChildren().size();
        for (int i = 0; i < len - 1; i++) {
            for (int j = i + 1; j < len; j++) {
                //double dist = this.getChildren().get(i).getCf()
                //        .getDistanceTo(this.getChildren().get(j).getCf());
            	double dist = MinCluster.getDiameter(this.getChildren().get(i), this
                        .getChildren().get(j));
                if (dist > maxDist) {
                    maxDist = dist;
                    c1 = i;
                    c2 = j;
                }
            }
        }
        // �Ծ�����Զ�ĺ��ӽڵ�Ϊ���ģ���B+1�����ӷ�Ϊ������ء�����һ�������������ڵ�ĺ��ӣ�����һ����Ҫ�´���һ���ڵ�����������
        LeafNode newNode = new LeafNode();
        newNode.addChild(this.getChildren().get(c2));
        // ������ڵ��Ѿ���Root�ڵ㣬����Ҫ����һ���µ�Root�ڵ�
        if (this.getParent() == null) {
            NonleafNode root = new NonleafNode();
            root.setN(this.getN());
            root.setLS(this.getLS());
            root.setSS(this.getSS());
            this.setParent(root);
            root.addChild(this);
        }
        //�����½ڵ�ͱ��ڵ�ĸ��ڵ�ĸ��ӹ�ϵ
        //����ǰ�ڵ����ʵ�����Ľڵ�ָ��ͬһ������
        newNode.setParent(this.getParent());
        //��ǰ�ڵ�ĸ��ڵ㽫��ʵ�����Ľڵ���뵽�亢�ӽڵ�ļ�����
        ((NonleafNode)this.getParent()).addChild(newNode);
        //����newNode���ĺ��ӽڵ�鵽newNode���������
        for (int i = 0; i < len; i++) {
            if (i != c1 && i != c2) {
            	if(MinCluster.getDiameter(this.getChildren().get(c2), this.getChildren().get(i)) < 
            			MinCluster.getDiameter(this.getChildren().get(c1), this.getChildren().get(i))){
                    newNode.addChild(this.getChildren().get(i));
                }
            }
        }
        //����newNode���ĺ��ӽڵ�ӱ��ڵ���ɾ��
        for (MinCluster cluster : newNode.getChildren()) {
            newNode.addCF(cluster.getCf(), true);
            this.deleteChild(cluster);
            this.addCF(cluster.getCf(), false);
        }
        // �������ӵ�LeafNode��ӵ�LeafNode˫��������
        if (this.getNext() != null) {
            newNode.setNext(this.getNext());
            this.getNext().setPre(newNode);
        }
        this.setNext(newNode);
        newNode.setPre(this);
        // ������ڵ���ѵ��¸��ڵ�ĺ����������˷�֦���ӣ��������ڵ����
        NonleafNode pn = (NonleafNode) this.getParent();
        if (pn.getChildren().size() > pn.getB()) {
            this.getParent().split();
        }
    }
 
    @Override
    /**
     * ʵ�ָ���TreeNote�ķ�����������µ�MinCluster��
     */
    public void absorbSubCluster(MinCluster cluster) {
        // ����ͼ�ҵ�Ҷ�ӽڵ�ĺ��ӣ�һЩsubcluster������cluster����Ĵ�
        CF cf = cluster.getCf();
        int nearIndex = 0;
        double minDist = Double.MAX_VALUE;
        //len��Ϊ���ô˺����ĸ��ڵ㣨Ҳ������Ҷ�ӽڵ㣩�ĺ��ӵĸ���
        int len = this.getChildren().size();
        if (len > 0) {
            for (int i = 0; i < len; i++) {
            	double dist = MinCluster.getDiameter(cluster, this
                        .getChildren().get(i));
                if (dist < minDist) {
                    nearIndex = i;
                }
            }
            // ���������غϲ����ֱ��
            double mergeDiameter = cf.getDistanceTo(this.getChildren().get(nearIndex)
                           .getCf());
            // ����ϲ����ִص�ֱ����������ֵ�����cluster��Ϊһ�������ĺ��Ӳ��뱾Ҷ�ӽڵ���
            if (mergeDiameter > T) {
                this.addChild(cluster);
                if (this.getChildren().size() > L) {
                    this.split();
                }
            }
            // �����������ֵ����ֱ�Ӻϲ�������
            // �������������ֵ����ѵ�ǰ��cluster���뵽��ǰҶ�ӽڵ�ĺ��ӽڵ��������������һ�����ӽڵ���
            else {
                this.getChildren().get(nearIndex).mergeCluster(cluster);
            }
        }
        // ����B��֮����Ҷ�ӽڵ㻹û��children
        else {
            this.addChild(cluster);
        }
        this.addCFUpToRoot(cluster.getCf());
    }
 
    public ArrayList<MinCluster> getChildren() {
        return children;
    }
 
    public void setChildren(ArrayList<MinCluster> children) {
        this.children = children;
    }
 
    public void addChild(MinCluster child) {
        this.children.add(child);
    }
 
    public void deleteChild(MinCluster child) {
        this.children.remove(children.indexOf(child));
    }
 
    public LeafNode getPre() {
        return pre;
    }
 
    public void setPre(LeafNode pre) {
        this.pre = pre;
    }
 
    public LeafNode getNext() {
        return next;
    }
 
    public void setNext(LeafNode next) {
        this.next = next;
    }
 
    public int getL() {
        return L;
    }
 
    public void setL(int l) {
        L = l;
    }
 
    public double getT() {
        return T;
    }
 
    public void setT(double t) {
        T = t;
    }
}
