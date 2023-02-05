## Avoid Unnecessary Object

### 1. 불필요한 객체 생성은 피하라

- 다음은 좋은 예시가 아니다.

```
String s = new String("String format");
```

- 객체를 생성하지 않고 재사용하는 좋은 예시

```
String s = "String format";
```

<br />

- Boolean 예시
```java
public final class Boolean implements java.io.Serializable, Comparable<Boolean> {

   // 1. Boolean Instance에 대해 미리 캐싱해 둔다.
   public static final Boolean TRUE = new Boolean(true);
   public static final Boolean FALSE = new Boolean(false);


   // 2. 캐싱된 Boolean 객체를 반환함으로써 불필요한 객체 생성을 피한다.
   @HotSpotIntrinsicCandidate
	public static Boolean valueOf(boolean b) {
		return (b ? TRUE : FALSE);
	}
}

```

<br />

### 2. [예시] 정규표현식에서 불필요한 객체 생성 방지
- 다음과 같은 정규표현식 객체가 있다.
- 이는 대체로 한 번 쓰고나면 버려지기 때문에 가비지 컬렉터의 대상이 된다.
- 이후 여러번 생성해서 쓴다면 성능 저하 문제가 있을 수 있다.
```java
static boolean isRomanNumeral(String s){
  return s.matches("^(?=.)M*(C[MD]|D?C{0,3})" + "(X[CL]|L?X{0,3})(I[XV]|V?I{0,3})$");
}
```


<br />

- 아래처럼 캐싱해서 사용한다면 성능상 이점이 있다.
```java
public class RomanNumerals{

  private static final Pattern ROMAN = Pattern.compile("^(?=.)M*(C[MD]|D?C{0,3})" + "(X[CL]|L?X{0,3})(I[XV]|V?I{0,3})$");

  static boolean isRomanNumeral(String s) {
    return ROMAN.matcher(s).matches();
  }
}
```



