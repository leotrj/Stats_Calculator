import java.util.Scanner;
public class StatMain {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		boolean quit = false;
		do{
			menu();
			System.out.print("Select an option: ");
			int a = input.nextInt();
			if(a == 1  || a == 2){
				option();
				System.out.print("Select an option: ");
				int b = input.nextInt();
				if(a == 1){
					System.out.print("Enter Confidence Level: ");
					double l = input.nextDouble();
					if(b == 1){
						Summary s = new Summary(data());
						ConfidenceInterval c = new ConfidenceInterval(s, l);
						System.out.println("\n" + c);
					}else if(b == 2){
						Summary s = new Summary(data());
						Summary s2 = new Summary(data());
						ConfidenceInterval c = new ConfidenceInterval(s, s2, l, 2);
						System.out.println("\n" + c);
					}else{
						Summary s = new Summary(data());
						Summary s2 = new Summary(data());
						ConfidenceInterval c = new ConfidenceInterval(s, s2, l, 1);
						System.out.println("\n" + c);
					}
				}else{
					System.out.print("Enter population mean: ");
					double um = input.nextDouble();
					System.out.print("Enter significance level: ");
					double sl = input.nextDouble();
					System.out.print("Enter side(s): ");
					int sides = input.nextInt();
					if(b == 1){
						Summary s = new Summary(data());
						sampleTest t = new sampleTest(s, um, sl, sides);
						System.out.println("\n" + t);
					}else if(b == 2){
						double[][] d = tdata();
						Summary s = new Summary(d[0]);
						Summary s2 = new Summary(d[1]);
						sampleTest t = new sampleTest(s, s2, um, um, sl, sides, 2);
						System.out.println("\n" + t);
					}else{
						double[][] d = tdata();
						Summary s = new Summary(d[0]);
						Summary s2 = new Summary(d[1]);
						sampleTest t = new sampleTest(s, s2, um, um, sl, sides, 1);
						System.out.println("\n" + t);
					}
				}
				
			}else if(a == 3){
				quit = true; 
			}else{
				System.out.println("Invalid Option");
			}
		System.out.println();
		}while(quit == false);
	}
	
	public static void menu(){
		//Displays menu
		System.out.println("1. Confidence Interval");
		System.out.println("2. T-Test");
		System.out.println("3. Quit");
	}

	public static void option(){
		//Displays calculation options
		System.out.println("1. 1 Sample");
		System.out.println("2. 2 Sample");
		System.out.println("3. Matched Pair");
	}
	
	public static double[] data(){
		//Creates an array of user's data input
		Scanner kbReader = new Scanner(System.in);
        System.out.print("Enter Data (w/ space inbetween): ");
        String input = kbReader.nextLine();
        String[] numbers = input.split(" ");
        double data[] = new double[numbers.length];
        for(int i = 0; i < numbers.length; i++){
            data[i] = Double.parseDouble(numbers[i]);
        }
		return data;
	}
	public static double[][] tdata(){
		//Creates an array of user's data input
		Scanner kbReader = new Scanner(System.in);
		System.out.print("Enter First Data (w/ space inbetween): ");
		String input = kbReader.nextLine();
		String[] numbers = input.split(" ");
		double data[] = new double[numbers.length];
		for(int i = 0; i < numbers.length; i++){
			data[i] = Double.parseDouble(numbers[i]);
		}
		
		System.out.print("Enter Second Data (w/ space inbetween): ");
		input = kbReader.nextLine();
		numbers = input.split(" ");
		double data2[] = new double[numbers.length];
		for(int i = 0; i < numbers.length; i++){
			data2[i] = Double.parseDouble(numbers[i]);
		}
		
		double[][] a = {data, data2};
		return a;
	}
}
