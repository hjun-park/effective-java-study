## Static factory method

## 1. 장점

### 장점 1 : 이름을 넘길 수 있다.
- 생성자 매개변수와 생성자만으로는 객체 특성을 제대로 설명할 수 없다.
- 정적 팩토리 메서드는 메서드 이름을 통해 이를 설명할 수 있다.
```java
public class Book02 {
	String title;
	String author;

	public Book02(String title, String author){
		this.title = title;
		this.author = author;
	}

	public Book02(){}
    
    /* static factory method */
	/**
     * - withName, withTitle과 같이 이름을 명시적으로 선언할 수 있으며,
	 *  - 한 클래스에 시그니처가 같은(String) 생성자가 여러개 필요한 경우에도 다음과 같이 생성할 수 있다.
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
```

<br />

### 장점 2 : 호출될 때마다 인스턴스 생성할 필요 없음
- static 키워드로 만든 불변클래스이므로 미리 만들어둔 인스턴스를 재활용하여(캐싱) 불필요한 객체 생성을 피할 수 있다.
  - Singleton 패턴 가능 / 인스턴스화 불가 만들기 가능
- 생성하는데 비싸고 자주 호출될 때 사용하면 좋다.
```java
public final class Boolean implements java.io.Serializable,
                                      Comparable<Boolean>
{
    public static final Boolean TRUE = new Boolean(true);
    public static final Boolean FALSE = new Boolean(false);
}

/* Boolean.valueOf는 객체를 아예 생성하지 않는다. */
public static Boolean valueOf(boolean b) {
    return b ? Boolean.TRUE : Boolean.FALSE;
}
```

<br />

### 장점 3 : 반환 타입의 하위 객체를 반환할 수 있다. (유연성)
- 반환할 객체의 클래스를 자유롭게 생성할 수 있다는 장점이 있다.
- 정적 팩토리 메서드의 반환 타입을 인터페이스 타입으로 할 경우, 
  - 해당 인터페이스를 구현하는 다른 구체 타입의 객체들을 반환할 수 있다. (상속된)
```java
class TransportationUtil {
 
  public static Transportation getTransportation(int cost) throws Exception {
    	if(0 <= cost && cost <= 4000) {
        	return new Bus();
        } else if(4000 < cost && cost <= 10_000) {
        	return new Taxi();
        }
    	throw new Exception("There is no alternative.");
    }
}
 
class Transportation { }
class Bus extends Transportation { }
class Taxi extends Transportation { }
```

<br />

### 장점 4 : 입력 매개변수에 따라 매번 다른 클래스의 객체 반환 가능
- `EnumSet`이 예시이며, public 생성자 없이 정적 팩토리 메서드만 제공한다.
- 조건에 따라 반환되는 객체가 다른 것을 볼 수 있다.
```java
public abstract class EnumSet<E extends Enum<E>> extends AbstractSet<E>
    implements Cloneable, java.io.Serializable
{
    public static <E extends Enum<E>> EnumSet<E> noneOf(Class<E> elementType) {
        Enum<?>[] universe = getUniverse(elementType);
        if (universe == null)
            throw new ClassCastException(elementType + " not an enum");

        if (universe.length <= 64)
            return new RegularEnumSet<>(elementType, universe);
        else
            return new JumboEnumSet<>(elementType, universe);
    }
}
```

<br />

### 장점 5 : 정적 팩터리 메서드를 작성하는 시점에는 반환할 객체의 클래스가 존재하지 않아도 된다.
- 인터페이스나 클래스의 정적 팩터리 메서드가 만들어지는 시점에서 반환 타입의 클래스가 존재하지 않아도 된다.

```java
public class Book {
  public static List<BookInterface> getInstance() {
    new ArrayList<>();
  }
}

public interface BookInterface {
  // 구현되지 않은 인터페이스
}

// 구현 클래스 생성
public class BookImpl implements BookInterface { }

// 클라이언트에서 활용
public class BookClient {
  public static void main(String[] args) {
    List<BookInterface> bookInstanceList = Book.getInstance();
	
    BookInterface bookImpl = new BookImpl();
    bookInstanceList.add(bookImpl);
  }
}
```

<br /> 

## 2. 단점

1. `public` 혹은 `protected` 생성자가 없이 정적 펙터리 메서드만 제공하는 클래스는 하위 클래스를 생성할 수 없다.
   1. 이러한 단점은 컴포지션을 이용하고 불변 타입으로 만들도록 유도하기 때문에 오히려 장점이 될 수 있다.
2. 찾기가 어렵다.
   1. 사용자는 정적 팩터리 메서드 방식 클래스를 인스턴스화할 방법을 알아내야 한다.
   2. 따라서 정적 팩터리 메서드의 이름 규칙을 정해놓는다.

### 2-1. 정적 팩토리 메서드 명명 규칙
**명명 규칙**
1. **from** : 매개변수를 하나 받아 해당 타입의 인스턴스를 반환하는 형변환 메서드 
   - ex) `Date d = Date.from(instant);`
2. **of** : 여러 매개변수를 받아 적합한 타입의 인스턴스를 반환하는 집계 메서드
   - ex) `Set<Rank> faceCards = EnumSet.of(JACK, QUEEN, KING);`
3. **valueOf** : from과 of의 더 자세한 버전
   - ex) `BingInteger prime = BigInteger.valueOf(Integer.MAX_VALUE);`
4. **instance** / **getInstance** : (매개변수를 받는다면) 매개변수로 명시한 인스턴스를 반환하지만, 같은 인스턴스임을 보장하지는 않는다. 
   - ex) `StackWalker luke = StackWalker.getInstance(options);`
5. **create** / **newInstance** : instance / getInstance와 같지만, 매번 새로운 인스턴스를 생성해 반환함을 보장한다.
   - ex) `Object newArray = Array.newInstance(calssObject, arrayLen);`
6. **getType** : getInstance와 같으나, 생성할 클래스가 아닌 다른 클래스에 팩터리 메서드를 정의할 때 쓴다. "Type"은 팩터리 메서드가 반환할 객체의 타입이다.
   - ex) `FileStore fs = Files.getFileStore(path);`
7. **newType** : newInstance와 같으나, 생성할 클래스가 아닌 다른 클래스에 팩터리 메서드를 정의할 때 쓴다. "Type"은 팩터리 메서드가 반환할 객체의 타입이다.
   - ex) `BufferedReader br = Files.newBufferedReader(path);`
8. **type** : getType과 newType의 간결한 버전
   - ex) `List<Complaint> litany = Collections.list(legacyLintany);`

   

