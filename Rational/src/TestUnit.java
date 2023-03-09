
import java.io.PrintStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class TestUnit {

	public static void main(String[] args) {
		
		File newFile = new File("Results.txt");
		try {
			if (newFile.createNewFile()) {
				System.out.println("Create Results file successfully");
			}
			else {
				System.out.println("File existed");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Redirect System.out to Results.txt
		try {
			System.setOut(new PrintStream("Results.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		Rational zero = new Rational(0);
		System.out.println(zero.basicForm());
		System.out.println(zero.floatForm(2));
		
		Rational r4_2 = new Rational(4,2);
		System.out.println(r4_2.basicForm());
		System.out.println(r4_2.floatForm(3));
		
		Rational r2_3 = new Rational(2,3);
		System.out.println(r2_3.basicForm());
		System.out.println(r2_3.floatForm(12));
		
		Rational r1_2 = new Rational(1,2);
		System.out.println(r1_2.basicForm());
		System.out.println(r1_2.floatForm(12));
		
		Rational r3_4 = new Rational(3,4);
		System.out.println(r3_4.basicForm());
		System.out.println(r3_4.floatForm(1));
		
		Rational r3_5 = new Rational(3,5);
		System.out.println(r3_5.basicForm());
		System.out.println(r3_5.floatForm(2));
		
		Rational r1_5 = new Rational(1,5);
		System.out.println(r1_5.basicForm());
		System.out.println(r1_5.floatForm(3));
		
		Rational r2_7 = new Rational(2,7);
		System.out.println(r2_7.basicForm());
		System.out.println(r2_7.floatForm(7));
		
		Rational r2_4 = new Rational(2,4);
		System.out.println(r2_4.basicForm());
		System.out.println(r2_4.floatForm(5));

		System.out.printf("a:%s+%s=%s\n", 
				r1_2.basicForm(), 
				r3_4.basicForm(), 
				Rational.Add(r1_2, r3_4).basicForm());
		
		System.out.printf("b:%s-%s=%s\n", 
				r3_5.basicForm(), 
				r1_5.basicForm(), 
				Rational.Subtract(r3_5, r1_5).basicForm());
		
		System.out.printf("c:%s*%s=%s\n", 
				r2_7.basicForm(), 
				r3_4.basicForm(), 
				Rational.Multiply(r2_7, r3_4).basicForm());
		
		System.out.printf("d:%s÷%s=%s\n", 
				r2_4.basicForm(), 
				r2_3.basicForm(), 
				Rational.Divide(r2_4, r2_3).basicForm());
		
		System.out.printf("e:%s*%s=%s\n", 
				r2_4.basicForm(), 
				zero.basicForm(), 
				Rational.Multiply(r2_4, zero).basicForm());
		
		System.out.printf("f:%s÷%s=%s\n", 
				r2_4.basicForm(), 
				zero.basicForm(), 
				Rational.Divide(r2_4, zero).basicForm());
	}
}
