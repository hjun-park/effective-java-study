package item01_static_factory_method;

/**
 * static factory method를 적용하지 않았을 때의 기본 예제
 */
public class Book01 {
	private String title;
	private String author;

	public Book01(String title, String author){
		this.title = title;
		this.author = author;
	}

	/**
	 * 생성자는 하나의 시그니처만 사용하므로, 다음과 같이 생성자 생성이 불가능하다.
	 * 생성자는 똑같은 타입의 파라미터로 받는 생성자를 여러개 생성할 수 없다.
	 */
	// public Book(String title){
	// 	this.title = title;
	// }
	//
	// public Book(String author){
	// 	this.author = author;
	// }
}
