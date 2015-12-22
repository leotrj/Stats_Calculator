import java.text.DecimalFormat;

import org.apache.commons.math3.distribution.*;
import org.apache.commons.math3.exception.MathIllegalArgumentException;

public class ConfidenceInterval {
	private Summary s;		//summary statistic
	private double level;	//confidence level
	private double tcrit;	//critical value
	private double ci;		//confidence interval
	private int df;			//degree of freedom

	public ConfidenceInterval(Summary a, double x){
		s = a;
		level = x;
		df = a.getn() - 1;
		tcrit = this.calcTcrit(df);
		ci = this.calcCI();
	}
	
	public ConfidenceInterval(Summary x1, Summary x2, double x, int t){
		int n = x1.getn();
		double b = x1.getMean() - x2.getMean();
		double c = x1.getVariance() + x2.getVariance();
		double d = Math.sqrt(c);
		Summary temp = new Summary(n, b, c, d);
		s = temp;
		if (t == 1){
			MatchedPairInterval(x1, x2, x);
		}else{
			TwoSampleInterval(x1, x2, x);
		}
	}
	
	public void MatchedPairInterval(Summary x1, Summary x2, double x){
		level = x;
		df = s.getn() - 1;
		tcrit = this.calcTcrit(df);
		ci = this.calcCI();
	}
	
	public void TwoSampleInterval(Summary x1, Summary x2, double x){;
		level = x;
		df = Math.min(x1.getn(), x2.getn());
		tcrit = this.calcTcrit(df);
		ci = this.tcalcCI(x1, x2);
	}
	
	public String toString(){
		DecimalFormat round = new DecimalFormat("0.###");
		double lower = s.getMean() - ci;
		double upper = s.getMean() + ci;
		String mean = "Mean: " + round.format(s.getMean());
		String dof = "Degrees of Freedom: " + df;
		return mean + "\n" + dof + "\nConfidence Interval " + round.format(level*100)
				+ "%: (" + round.format(lower) + ", " + round.format(upper) + ")";
	}
	
	
	/***** ACCESSORS *****/
	public double getLevel(){
		return level;
	}
	
	public double getTcrit(){
		return tcrit;
	}
	
	public double getdf(){
		return df;
	}
	
	public double getCI(){
		return ci;
	}
	
	/***** MODIFIERS *****/	
	public double calcTcrit(double x){
		try{
			TDistribution tDist = new TDistribution(x);
			double critVal = tDist.inverseCumulativeProbability(1.0 - (1 - level)/2);
			return critVal;
		} catch (MathIllegalArgumentException e){
			return Double.NaN;
		}
	}
	
	public double calcCI(){
		return tcrit*s.getStandardDeviation()/Math.sqrt(s.getn());
	}
	
	public double tcalcCI(Summary x1, Summary x2){
		double temp = Math.sqrt((x1.getStandardDeviation()/x1.getn()) + (x2.getStandardDeviation()/x2.getn()));
		return tcrit*temp;
	}
}