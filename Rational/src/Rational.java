
import java.math.BigDecimal;
import java.math.RoundingMode;


public class Rational {
	private int _numerator = 0;
	private int _denominator = 1;
	
	//---------------------static methods for add, substract, multiply, Divide-------------------
	public static  Rational Add(Rational a, Rational b) {
		int numerator = a.numerator() * b.denominator() + b.numerator() * a.denominator();
		int denominator = a.denominator() * b.denominator();
		return new Rational(numerator, denominator);
	}
	
	public static  Rational Subtract(Rational a, Rational b) {
		int numerator = a.numerator() * b.denominator() - 
				b.numerator() * a.denominator();
		int denominator = a.denominator() * b.denominator();
		return new Rational(numerator, denominator);
	}
	
	public static  Rational Multiply(Rational a, Rational b) {
		return new Rational(a.numerator() * b.numerator(), a.denominator() * b.denominator());
	}
	
	public static  Rational Divide(Rational a, Rational b) {
		if (0 == b.numerator() ) {
			throw new IllegalArgumentException("The divisor can not be zero");
		}
		else {
			return new Rational( a.numerator() * b.denominator(),a.denominator() * b.numerator());
		}
	}
	
	//--------------------------object methods----------------------------------------------------
	//Two constructors with different arguments, both use "Modify" function to initialize
	public Rational(int num) {
		this.modify(num, 1);
	}

	public Rational(int num, int de) {
		this.modify(num, de);
	}

	//The entrance method to keep the rational reduced
	public void modify(int num, int de) {
		if(0 == de)  throw new IllegalArgumentException("The denominator can not be zero.");;
		this._numerator = num;
		this._denominator = de;
		this.reduce();
	}
		
	public String basicForm() {
		if(1 == this._denominator || 0 == this._numerator) return Integer.toString(this._numerator);
		return String.format("%s/%s", this._numerator, this._denominator);
	}
	
	//use BigDecimal to handle with the result with infinite decimals
	public String floatForm(int precision) {
		  if (precision < 0) { 
			  throw new IllegalArgumentException("The precision must be a positive integer or zero.");
	        }
	        BigDecimal b1 = BigDecimal.valueOf(this._numerator);
	        BigDecimal b2 = BigDecimal.valueOf(this._denominator);
	        return b1.divide(b2, precision, RoundingMode.DOWN).toString();
	}
	
	//---------------------methods not called outside of the class------------------------------------
	protected int Min(int x1, int x2) {
		return x1 > x2 ? x2 : x1;
	}
	
	//perform reduce operation
	protected void reduce() {
		if (0 == this._numerator|| 1 == this._denominator) return;
		int x = Min(this._denominator,this._numerator);
		int max_divisor = 1;
		for (int i = 2; i <= x; ++i) {
			if (this._numerator % i == 0 && this._denominator % i == 0) {
				max_divisor = i;
			}
		}
		this._denominator = this._denominator / max_divisor;
		this._numerator = this._numerator / max_divisor;
	}
	
	protected int numerator() {
		return this._numerator;
	}
	protected int denominator() {
		return this._denominator;
	}
}

