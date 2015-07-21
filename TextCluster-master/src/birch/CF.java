package birch;

public class CF {

	/**
	 * �������ݵ�ĸ���
	 */
    private int N;
    
    /**
     * ����N�����ݵ�����Ժͣ���ÿ�����Ե�ֵ��Ӧ��ӵĺͣ����ݵ��м������Դ˱�����Ϊ��ά
     */
    private double[] LS;
    
    /**
     * ����N�����ݵ��ƽ����
     */
    private double[] SS;
 
    //�����˶�����캯��
     
    public CF() {
        LS=new double[BIRCH.dimen];
        SS=new double[BIRCH.dimen];
    }
 
    
    /**
     * ����һ��data point instance����һ��Clustering Feature�����˹��캯��������Ϊһ���µ�MinCluster����CF�ģ���
     * �ڴ������ݵ����N=1��
     * �ڴ������ݵ�����Ժ�LS���Ǵ�������data�м�¼������
     * �ڴ������ݵ��ƽ����SS���Ǵ�������data�и���Ԫ�أ�Ԫ�ؾ������ݵ������������Ӧ��ֵ����ƽ����
     * @param data
     */
    public CF(double[] data) {
        int len = data.length;
        this.N = 1;
        this.LS = data;
        this.SS=new double[len];
        for (int i = 0; i < len; i++)
            this.SS[i] = Math.pow(data[i], 2);
    }
     
    //���ƹ��캯��(���)
    public CF(CF cf){
        this.N=cf.getN();
        int len=cf.getLS().length;
        this.LS=new double[len];
        this.SS=new double[len];
        for(int i=0;i<len;i++){
            this.LS[i]=cf.getLS()[i];
            this.SS[i]=cf.getSS()[i];
        }
    }
 

    /**
     * ����D2��������CF Entry֮��ľ��룬D2��Ϊ�ؼ����
     * @param entry
     * @return
     */
    public double getDistanceTo(CF entry) {
        double dis = 0.0;
        int len = this.LS.length;
        for (int i = 0; i < len; i++) {
            dis += this.SS[i] / this.N + entry.getSS()[i] / entry.getN() - 2
                    * this.LS[i] * entry.getLS()[i] / (this.N * entry.getN());
        }
        return Math.sqrt(dis);
    }
     
    //����D0������������֮���ŷ�Ͼ���
//  public double getDistanceTo(CF entry) {
//      int len=entry.getLS().length;
//      double[] a=new double[len];
//      double[] b=new double[len];
//      for(int i=0;i<len;i++){
//          a[i]=this.getLS()[i]/this.N;
//          b[i]=this.getSS()[i]/this.N;
//      }
//      return calEuraDist(a,b,len);
//  }
 
    // ���ϻ��ȥһ��CF��ֵ
    public void addCF(CF entry, boolean add) {
        int opt = 1; // Ĭ��Ϊ���
        if (!add) // ���addΪfalse��Ϊ���
            opt = -1;
        this.N = this.N + entry.getN() * opt;
        int len = this.LS.length;
        for (int i = 0; i < len; i++) {
            this.LS[i] = this.LS[i] + entry.getLS()[i] * opt;
            this.SS[i] = this.SS[i] + entry.getSS()[i] * opt;
        }
    }
 
    //��������������ŷ�Ͼ���
    public static double calEuraDist(double[] arr1,double[] arr2,int len){
        double result=0.0;
        for(int i=0;i<len;i++){
            result+=Math.pow(arr1[i]-arr2[i],2.0);
        }
        return Math.sqrt(result);
    }
    public int getN() {
        return N;
    }
 
    public void setN(int n) {
        N = n;
    }
 
    public double[] getLS() {
        return LS;
    }
 
    public void setLS(double[] lS) {
        LS = lS;
    }
 
    public double[] getSS() {
        return SS;
    }
 
    public void setSS(double[] sS) {
        SS = sS;
    }
}
