package item01_static_factory_method;

import java.util.Collections;

public class StaticFactoryMethodMain {

	public static void main(String[] args) {
		Book02 book21 = new Book02("title1", "author1");
		Book02 book22 = Book02.withAuthor("author2");
		Book02 book23 = Book02.withTitle("title3");

	}
}
