package item01_static_factory_method;

public class Book02 {
	String title;
	String author;

	public Book02(String title, String author){
		this.title = title;
		this.author = author;
	}

	public Book02(){}

	/*
	 * 장점 1
	 *  withName, withTitle과 같이 이름을 명시적으로 선언할 수 있으며,
	 * 한 클래스에 시그니처가 같은(String) 생성자가 여러개 필요한 경우에도 다음과 같이 생성할 수 있다.
	 */
	public static Book02 withAuthor(String author){
		Book02 Book02 = new Book02();
		Book02.author = author;
		return Book02;
	}

	public static Book02 withTitle(String title){
		Book02 Book02 = new Book02();
		Book02.title = title;
		return Book02;
	}
}
