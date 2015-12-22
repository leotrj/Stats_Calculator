import java.text.DecimalFormat;
public class Summary{
	private int n;
	private double mean;
	private double std;
	private double variance;
	
	public Summary(int a, double b, double c, double d){
		n = a;
		mean = b;
		variance = c;
		std = d;
	}
	
	public Summary(double data[]){
		setn(data);
		setMean(data);
		setStandardDeviation(data);
		setVariance(data);
	}
	
	public String toString(){
		DecimalFormat round = new DecimalFormat("0.###");
		return "Mean: " + round.format(getMean()) + 
				"\n" + "Standard Deviation: " +  round.format(getStandardDeviation()) +
				"\n" + "Variance: " + round.format(getVariance()) + 
				"\n" + "n: " + round.format(getn());
	}
	
	/***** ACCESSORS *****/
	public int getn(){
		return n;
	}
	public double getMean(){
		return mean;
	}
	public double getStandardDeviation(){
		return std;
	}
	public double getVariance(){
		return variance;
	}
	
	/***** MODIFIERS *****/
	public void setVariance(double data[]){
		double v = 0;
		for (double val : data){
			v += Math.pow(val - this.getMean(), 2);
		}
		v = v/(this.getn() - 1);
		variance = v;
	}
	
	public void setStandardDeviation(double data[]){
		double s = 0;
		for (double val : data){
			s += Math.pow(val - this.getMean(), 2);
		}
		s = Math.sqrt(s/(data.length - 1));
		std = s;
	}
	
	public void setStandardDeviation(double v){
		double s = 0;
		s = Math.sqrt(v);
		std = s;
	}
	
	public void setMean(double data[]){
		double m = 0;
		for (double val : data){
			m += val;
		}
		m = m/data.length;
		mean = m;
	}
	
	public void setn(double data[]){
		n = data.length;
	}
}
