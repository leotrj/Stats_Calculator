import java.text.DecimalFormat;

import org.apache.commons.math3.distribution.TDistribution;
import org.apache.commons.math3.exception.MathIllegalArgumentException;

public class sampleTest {
	private Summary s;		//summary statistics
	private double t;		//t-score
	private int df;			//degrees of freedom
	private double u;		//true mean
	private double a;		//significance level
	private double p;		//p-value
	
	public sampleTest(Summary x, double y, double z, int side){
		s = x;
		u = y;
		setT();
		df = x.getn() - 1;
		a = z;
		p = this.calcP(side);
	}
	
	public sampleTest(Summary x1, Summary x2, double y1, double y2, double z, int side, int t){
		u = y1 - y2;
		a = z;
		
		//two summary statistics 
		if (t == 1){		//matched pair t test
			int n = x1.getn();
			double b = x1.getMean() - x2.getMean();
			double c = x1.getVariance() + x2.getVariance();
			double d = Math.sqrt(c);
			Summary temp = new Summary(n, b, c, d);
			s = temp;
			df = temp.getn() - 1;
			setT();
		}else{				//two sample t test
			df = Math.min(x1.getn(), x2.getn());
			tsetT(x1, x2);
		}
		p = this.calcP(side);
	}
	
	public String toString(){
		DecimalFormat round = new DecimalFormat("0.###");
		String sign;
		String m;
		if (a < p){
			sign = "<";
			m = "No enough evidence to reject the null hypothesis";
		}else{
			sign = ">";
			m = "Reject the null hypothesis at å = " + a;
		}
		
		return "Degrees of Freedom: " + df + "\n" 
				+ a + " " + sign + " " + round.format(p) + " " + m;
	}
	
	public void setT(){
		//calculates t score given one sample
	    double temp;
		temp = s.getMean() - u;
		temp = temp/(s.getStandardDeviation()/Math.sqrt(s.getn()));
		t = temp; 
	}
	
	public void tsetT(Summary x1, Summary x2){
		//calculates t score given two sample
	    double temp;
		temp = (x1.getMean() - x2.getMean()) - u;
		temp = temp/Math.sqrt((x1.getStandardDeviation()/x1.getn()) + (x2.getStandardDeviation()/x2.getn()));;
		t = temp; 
	}
	
	public double calcP(int x){
		//calculates p-value 
		try{
			TDistribution distribution = new TDistribution(null, df);
			if (x == 1){
				return distribution.cumulativeProbability(-t);
			}else{
				return 2.0 * distribution.cumulativeProbability(-t);
			}
		} catch (MathIllegalArgumentException e){
			return Double.NaN;
		}
	}
}
