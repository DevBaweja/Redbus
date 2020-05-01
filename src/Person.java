import java.util.Scanner;

public class Person {

	private String name;
	private int age;
	private boolean gender; // true for male and false for female
	private String address;

	void readPerson() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter name");
		name = sc.next();
		System.out.println("Enter age");
		age = sc.nextInt();
		while (true) {
			System.out.println("Enter Gender: 1-Male 2-Female");
			int g = sc.nextInt();
			if (g == 1) {
				gender = true;
				break;
			} else if (g == 2) {
				gender = false;
				break;
			} else
				System.out.println("Enter Valid Entry");
		}
		System.out.println("enter address");
		address = sc.next();
	}

	void showPerson() {
		System.out.println("name : " + name);
		System.out.println("age : " + age);
		if (gender)
			System.out.println("Gender : Male");
		else
			System.out.println("Gender : Female");
		System.out.println("address : " + address);
	}
	
	Person()
	{
		name = null;
		age = 18;
		gender = true;
		address = null ;
	}
	// parameteric
	Person(String name,int age,boolean gender,String address)
	{	
		this.name = name;
		this.gender = gender;
		this.age = age;
		this.address = address;
	}
	
	Person(Person P) // no need of & in java
	{
		name = P.name;
		age = P.age;
		gender = P.gender;
		address = P.address;
	}

}
