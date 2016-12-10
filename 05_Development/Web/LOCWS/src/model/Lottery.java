package model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Lottery {
	private String date;
	private String eight;
	private String seven;
	private String six;
	private String five;
	private String four;
	private String three;
	private String two;
	private String one;
	private String special;

	public Lottery() {

	}

	public Lottery(String date, String eight, String seven, String six,
			String five, String four, String three, String two, String one,
			String special) {
		super();
		this.date = date;
		this.eight = eight;
		this.seven = seven;
		this.six = six;
		this.five = five;
		this.four = four;
		this.three = three;
		this.two = two;
		this.one = one;
		this.special = special;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getEight() {
		return eight;
	}

	public void setEight(String eight) {
		this.eight = eight;
	}

	public String getSeven() {
		return seven;
	}

	public void setSeven(String seven) {
		this.seven = seven;
	}

	public String getSix() {
		return six;
	}

	public void setSix(String six) {
		this.six = six;
	}

	public String getFive() {
		return five;
	}

	public void setFive(String five) {
		this.five = five;
	}

	public String getFour() {
		return four;
	}

	public void setFour(String four) {
		this.four = four;
	}

	public String getThree() {
		return three;
	}

	public void setThree(String three) {
		this.three = three;
	}

	public String getTwo() {
		return two;
	}

	public void setTwo(String two) {
		this.two = two;
	}

	public String getOne() {
		return one;
	}

	public void setOne(String one) {
		this.one = one;
	}

	public String getSpecial() {
		return special;
	}

	public void setSpecial(String special) {
		this.special = special;
	}
}