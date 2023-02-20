# 이펙티브 코틀린 (Effective Kotlin best practice)

# 1장 안정성

## ITEM 01. 가변성을 제안하라

### 코틀린 가변성 제한하기
- 읽기전용 프로퍼티 (val)
- 가변 컬렉션과 읽기 전용 컬렉션 구분하기
- 데이터 클래스의 copy

#### 읽기전용 프로퍼티 (val)
- val는 변경이 불가능하므로 게터만 제공된다. (세터 x)
- val는 var로 오버라이드할 수 있다.
- val는 읽기전용 프로퍼티지만, 변경할 수 없음(불변, immutable)을 의미하는 건 아니다.
- getter로 정의한 val는 스마트 캐스트를 할 수 없다.

#### 가변 컬렉션과 읽기 전용 컬렉션 구분하기
- 컬렉션 다운캐스팅은 하지말자.
```kotlin
val list = listOf(1,2,3)

// 이렇게 하지 마세요!
if (list is MutableList) {
    list.add(4)
}
```
- mutable로 변경해야 한다면, 복제(copy)를 통해 새로운 mutable 컬렉션을 만드는 list.toMutableList를 활용하자.
```kotlin
val list = listOf(1,2,3)

val mutableList = list.toMutableList()
mutableList.add(4)
```
  
#### 데이터 클래스의 copy

immputable객체의 장점
1. 한ㄴ 번 정의된 상태가 유지되므로, 코트를 이해하기 쉽다.
2. immutable 객체는 공유했을 때도 충돌이 따로 이루어지지 않으므로, 병렬 처리를 안전하게 할 수 있다.
3. immutable 객체에 대한 참조는 변경되지 않으므로, 쉽게 캐시할 수 있다.
4. immutable 객체는 방어적 복사본(defensive copy)을 만들 필요가 없다. 또한 객체를 복사할 때 깊은 복사를 따로 하지 않아도 된다.
5. immutable 객체는 다른 객체를 만들 때 활용하기 좋다. 또한 immutable 객체는 실행을 더 쉽게 예측할 수 있다.
6. immutable 객체는 set or map의 key로 사용할 수 있다. mutable 객체는 안된다!
  
</br>

우리가 직접 만드는 객체 또한 Immutable 해야 한다.
```kotlin
class User(
    val name: String,
    val surname: String
) {
    fun withSurname(surname: String) = User(name, surname)
}

val user = User("Maja", "Markiewicz")
user = user.withSurname("Moskala")
print(user) // User(name=Maja, surname=Moskala)
```
</br>

모든 프로퍼티를 대상으로 이런 함수를 하나하나 만드는 건 매우 귀찮은 일이다.  
그럴 때는 data 한정자를 사용하면 된다.  
```kotlin
data class User(
    val name: String,
    val surname: String
)

val user = User("Maja", "Markiewicz")
user = user.copy(surname = "Moskala")
print(user) // User(name=Maja, surname=Moskala)
```

</br>

#### 변경 가능 지점 노출하지 말기

</br>

아래 코드는 mutable 객체를 외부로 노출해서 다른 곳에서 쉽게 변경할 수 있는 코드가 됩니다.
```kotlin
data class User(val name: String)

class UserRepository {
    private val storedUsers: MutableMap<Int, String> = 
        mutableMapOf()

    fun loadAll(): MutableMap<Int, String> {
        return storedUsers
    }
}
```

</br>

컬렉션은 객체를 읽기 전용 슈퍼타입으로 업캐스트하여 가변성을 제한하는 게 좋습니다.
```kotlin
data class User(val name: String)

class UserRepository {
    private val storedUsers: MutableMap<Int, String> = 
        mutableMapOf()

    fun loadAll(): Map<Int, String> { // Map 인터페이스로 리턴
        return storedUsers
    }
}
```

#### 정리
- var보단 val를 사용하자
- mutable 프로퍼티보단 immutable 프로퍼티를 사용하자
- mutable 객체와 클래스보다는 immutable 객체와 클래스를 사용하자
- 변경이 필요한 대상을 만들어야 한다면, immutable 데이터 클래스로 만들고 copy를 활용하자
- 컬렉션에 상태를 저장해야 한다면, mutable 컬렉션보다는 읽기 전용 컬렉션을 사용하자
- 변이 지점을 적절하게 설계하고, 불필요한 변이 지점을 만들지 말자
- mutable 객체를 외부에 노출하지 말자
다만 몇 가지 예외가 있습니다.  
가끔 효율성 때문에 immutable 객체보다 mutable 객체를 사용하는 것이 좋을 때가 있습니다.  
이러한 최적화는 코드에서 성능이 중요한 부분에서만 사용하는 것이 좋습니다.  
추가로 immutable 객체를 사용할 때는 언제나 멀티스레드 때에 더 많은 주의를 기울여야 한다는 것을 기억하세요.  
그래도 일단 immutable 객체와 mutable 객체를 구분하는 기준은 가변성입니다.

</br>

## ITEM 02. 변수의 스코프를 최소화하라.

- 지역변수를 사용하자.
- 최대한 좁은 스코프를 갖게 변수를 사용하자.

```kotlin
// 좋은 예시들

for ((i, user) in users.withIndex()) {
    print("User at $i is $user")
}

val user: User = if (hasValue) {
    getValue()
} else {
    User()
}

fun updateWeather(degrees: Int) {
    val (description, color) = when {
        degrees < 5 -> "cold" to Color.BLUE
        degrees < 23 -> "mild" to Color.YELLOW
        else -> "hot" to Color.RED
    }
    // ...
}
```
</br>

> 스코프를 좁게 만드는 가장 중요한 이유는, 프로그램을 추적하고 관리하기 쉽기 때문이다.

#### 정리  

> 여러가지 이유로 변수의 스코프는 좁게 만들어서 활용하는 것이 좋습니다.  
> 또한 var보다는 val를 사용하는 것이 좋습니다. 람다에서 변수를 캡처한다는 것을 꼭 기억하세요.

</br>

## ITEM 03. 최대한 플랫폼 타임을 사용하지 말라

> 자바 코드를 수정할 수 있으면, @NotNull, @Nullable과 같은 어노테이션을 붙여서 타입을 명확하게 해주자.

</br>

#### 정리

> 다른 프로그래밍 언어에서 와서 nullable 여부를 알 수 없는 타입을 플랫폼 타입이라고 부른다.  
> 이러한 플랫폼 타입을 사용하는 코트는 해당 부분만 위험할 뿐만 아니라, 이를 활용하는 곳까지 영향을 줄 수 있는 위험한 코드다.  
> 따라서 이런 코드를 사용하고 있다면 빨리 해당 코드를 제거하는 것이 좋다.  
> 또한 연결되어 있는 자바 생성자, 메서드, 필드에 nullable 여부를 지정하는 어노테이션을 활용하는 것도 좋다.  
> 이러한 정보는 코틀린 개발자 뿐만 아니라 자바 개발자에게도 유용한 정보다.

</br>

## ITEM 04. inferred(추론)타입으로 리턴하지 말라

```kotlin
open class Animal
class Zebra: Animal()

fun main() {
    var animal = Zebra()
    animal = Animal() // 오루 Type mismatch
}
```

</br>

> 할당 시 inferred 타입은 오른쪽 피연산자에 맞게 설정된다. 절대 슈퍼클래스 or 인터페이스로는 설정되지 않는다

</br>

#### 정리

> 타입을 확실하게 지정해야 하는 경우에는 명시적으로 타입을 지정해야 한다는 원칙만 갖고 있으면 된다.  
> 이는 굉장히 중요한 정보이므로, 숨기지 않는 것이 좋다.  
> 또한 안전을 위해 외부 API를 만들 때는 반드시 타입을 지정하고, 이렇게 지정한 타입을 특별한 이유와 확실한 확인 없이는 제거하지 말아라.  
> inferred 타입은 프로젝트가 진전될 때, 제한이 너무 많아지거나 예측하지 못한 결과를 낼 수 있다는 것을 기억하라!!

</br>

## ITEM 05. 예외를 활용해 코드에 제한을 걸아라

- require(): 아규먼트를 제한할 수 있습니다.  
- check(): 상태와 관련된 동작을 제한할 수 있습니다.  
- assert(): 어떤 것이 true인지 확인할 수 있습니다. 단, 테스트모드에서만 동작합니다.  
- return 또는 throw와 홤께 활용하는 Elvis 연산자

스마트 캐스팅 예시  
```kotlin
fun changeDress(person: Person) {
    require(person.outfit is Dress)
    val dress: Dress = person.outfit
}

class Person (val email: String?)
fun sendEmail(person: Person, message: String) {
    require(person.email != null)
    val email: String = person.email
}

fun sendEmail(val email: String) {
    val email = requireNotNull(person.email)
    ...
}

fun sendEmail(person: Person, text: String) {
    val email: String = person.email ?: return
}
```

개인적으로 assert()는 실제코드에 테스트코드가 섞여있는 모습이라, 좋은 코드로 보이진 않는다.  
특별히 사용해야되는 순간이 오지 않는 이상 사용하지 않을 것 같다. 
나머지 다른 것들은 상황에 맞게 잘 활용하면 좋은 코드가 나올 것 같다.

</br>

#### 정리

이번 절에서 활용한 내용을 기반으로, 다음과 같은 이득을 얻을 수 있어야 한다.
- 제한을 훨씬 더 쉽게 확인할 수 있다.
- 애플리케이션을 더 안정적으로 지킬 수 있다.
- 코드를 잘못 쓰는 상황을 막을 수 있다.
- 스마트 캐스팅을 활용할 수 있다.
  
이를 위해 활용했던 메커니즘을 정리하면 다음과 같다.  
- require(): 아규먼트와 관련된 예측을 정의할 때 사용하는 범용적인 방법
- check(): 상태와 관련된 예측을 정의할 때 사용하는 범용적인 방법
- assert(): 테스트 모드에서 테스트를 할 때 사용하는 범용적인 방법 
- return 또는 throw와 홤께 활용하는 Elvis 연산자

</br>

## ITEM 06. 사용자 정의 오류보다는 표준 오류를 사용하라.

동의.  
표준 Exception을 사용하게 되면, 다른 사람이 Exception의 종류, 원인을 쉽게 파악할 수 있게 된다.

## ITEM 07. 결과 부족이 발생할 경우 null과 Failure를 사용하라.

예외를 정보를 전달하는 방법으로 사용해서는 안 됩니다.  
예외는 잘못된 특별한 상황을 나타내야 하며, 처리되어야 합니다.  
예외는 예외적인 상황이 발생했을 때 사용하는 것이 좋습니다.  

이유.
- 많은 개발자가 예외가 전파되는 과정을 제대로 추적하지 못합니다.
- 코틀린의 모든 예외는 unchecked예외입니다. 따라서 사용자가 예외를 처리하지 않을 수도 있으며, 이와 관련된 내용은 문서에도 제대로 드러나지 않습니다. 실제로 API를 사용할 때 예외와 관련된 사항을 단순하게 메서드 등을 사용하면서 파악하기 힘듭니다.
- 예외는 예외적인 상황을 처리하기 위해서 만들어졌으므로 명시적인 테스트만큼 빠르게 동작하지 않습니다.
- try-catch 블록 내부에 코드를 배치하면, 컴파일러가 할 수 있는 최적화가 제한됩니다.

null과 Failure는 예상되는 오류를 표현할 때 굉장히 좋습니다.
```kotlin
inline fun <reified T> String.readObjectOrNull(): T? {
    //...
    if (incorrectSign) {
        return null
    }
    ///
    return result
}

inline fun <reified T> String.readObject(): Result<T> {
    //...
    if (incorrectSign) {
        return Failure(JsonParsingException())
    }
    ///
    return Success(result)
}

sealed class Result<out T>
class Success<out T>(val result: T): Result<T>()
class Failure(val throwable: Throwable): Result<Nothing>()

class JsonParsingException: Exception()
```

null 처리 시
```kotlin
val age = userText.readObjectOrNull<Person>()?.age ?: -1
```

Result와 같은 공용체를 처리할 때
```kotlin
val person = userText.readObject<Person>()
val age = when(person) {
    is Success -> person.age
    is Failure -> -1
}
```

#### 정리
- try-catch 블록보다 효율적이고, 사용하기 쉽고, 명확하다.
- 예외는 놓칠 수 있고, 전체 애플리케이션을 중지시킬 수도 있다.
- 추가적인 정보를 전달하려면 sealed result
- 그렇지 않다면 Null

## ITEM 08. 적절하게 null을 처리하라.

기본적으로 nullable 타입을 처리하는 방법
- ?., 스마트캐스팅, Elvis 연산자 등을 활용해서 안전하게 처리한다.
- 오류를 throw 한다.
- 함수 또는 프로퍼티를 리팩터링해서 nullable 타입이 나오지 않게 바꾼다.  
  
#### 정리
- not-null assertion(!!)은 간단하지만 위험하다.
- 지금은 Null이 아니라고 생각할 수 있지만, 리팩토링 후 null이 될 수 있다. (미래에 충분히)
- nullability는 어떻게든 적절하게 처리해야 하므로, 추가비용이 발생한다.
  - 필요한 경우가 아니라면 Nullability를 피해라.
  - lateinit 프로퍼티와 NotNull 델리게이트를 활용해라.

## ITEM 09. use를 사용하여 리소스를 닫아라.

명시적으로 close 메서드를 호출해야 하는 자바 표준 라이브러리들이 있다.
- InputStream, OutputStream
- java.sql.Connection
- java.io.Reader(FileReader, BufferedReader, CSSParser)
- java.new.Socket, java.util.Scanner

전통적으로 try-finally 블록을 해서 닫았다.  
하지만, 이러한 방법은 filnally 블록에서 에러가 발생할 수도 있는데, 그 에러를 또 따로 처리하진 않는다.  
다음과 같이 use를 사용하자.
```kotlin
fun countCharactersInFile(path: String): Int {
    val reader = BufferedReader(FileReader(path))
    reader.use {
        return reader.lineSequence().sumBy { it.length }
    }
}
```

자바에서도 try-with-resources 방식으로 처리할 수 있다.

## ITEM 10. 단위 테스트를 만들어라.

단위 테스트는 일반적으로 다음과 같은 내용을 확인한다.
- 일반적인 use case(이를 happy path라고도 부른대): 요소가 사용될 거라고 예상되는 일반적인 방법을 테스트합니다. 예를 들어 앞의 코드처럼 함수로 간단한 숫자 몇 개를 테스트합니다.
- 일반적인 오류 케이스와 잠재적인 문제: 제대로 동작하지 않을 거라고 예상되는 일반적인 부분, 과거에 문제가 발생했던 부분 등을 테스트합니다.
- 엣지 케이스와 잘못된 아규먼트: Int의 경우 Int.MAX_VALUE를 사용하는 경우, nullable의 경우 'null' 또는 'null 값으로 채워진 객체'를 사용하는 경우를 의미합니다. 또한 피보나치 수는 양의 정수로만 구할 수 있습니다. 음의 정수 등을 넣으면 아규먼트 자체가 잘못된 것입니다. 이러한 경우를 테스트할 수 있습니다.

</br>

### 단위 테스트의 장점
- 테스트가 잘 된 요소는 신뢰할 수 있습니다. 요소를 신뢰할 수 있으므로 요소를 활용한 작업에 자신감이 생깁니다.
- 테스트가 잘 만들어져 있다면, 리팩터링하는 것이 두렵지 않습니다. 테스트가 있으므로, 리팩터링했을 때 버그가 생기는 지 쉽게 확인할 수 있습니다. 따라서 테스트를 잘 만든 프로그램은 코드가 점점 발전합니다. 반면 테스트가 없으면 실수로 오류를 일으킬 수도 있다는 생각에 레거시 코드(기존의 코드)를 수정하려고 만지는 것을 두려워하게 됩니다.
- 수동으로 테스트하는 것보다 단위 테스트로 확인하는 것이 빠릅니다. 빠른 속도의 피드백 루프(코드를 작성하고 테스트하고를 반복하는 것)가 만들어지므로, 개발의 전체적인 속도가 빨라지고 재미있습니다. 또한 버그를 빨리 찾을 수 있으므로 버그를 수정하는 비용도 줄어듭니다.

### 단위 테스트의 단점
- 시간이 오래 걸린다. 다만 장기적으로 좋은 단위 테스트는 '디버깅 시간', '버그를 찾는 데 드는 시간'을 줄여 줍니다. 또한 단위 테스트가 수동 테스트보다 훨씬 빠르므로 시간이 절약됩니다.
- 테스트를 활용할 수 있게 코드를 조정해야 합니다. 변경하기 어렵기는 하지만, 이러한 변경을 통해서 훌륳아고 잘 정립된 아키텍처를 사용하는 것이 강제됩니다.
- 좋은 단위 테스트를 만드는 작업이 꽤 어렵습니다. 남은 개발 과정에 대한 확실한 이해가 필요합니다. 잘못 만들어진 단위 테스트는 득보다 실이 큽니다. 단위 테스트를 제대로 하려면, 단위 테스트를 하는 방법을 배워야 합니다. 소프트웨어 테스팅 또는 테스트 주도 개발과 관련된 내용을 이해해야 합니다.

숙련된 코틀린 개발자가 되려면, 단위 테스트와 관련된 기술을 습득하고, 중요한 코드라고 할 수 있는 다음과 같은 부분에 대해 단위 테스트하는 방법을 알고 있어야 합니다.
- 복잡한 부분
- 계속해서 수정이 일어나고 리팩터링이 일어날 수 있는 부분
- 비즈니스 로직 부분
- 공용 API 부분
- 문제가 자주 발생하는 부분
- 수정해야 하는 프로덕션 버그

## ITEM 11. 가독성을 목표로 설계하라

### 인식 부하 감소

```kotlin
// A
if (person != null && person.isAdult) {
    view.showPerson(person)
} else {
    view.showError()
}

// B
person?.takeIf { it.isAdult }
    ?.let(view::showPerson)
    ?: view.showError()
```

가독성이란 코드를 읽고 얼마나 빠르게 이해할 수 있는지를 의미한다.  
코틀린 초보자에게는 A가 더 읽고 이해하기 쉽다.  
경험이 많은 코틀린 개발자라면 그래도 코드를 쉽게 읽을 수 있을 것이다.  
하지만, 숙련된 개발자만을 위한 코드는 좋은 코드가 아니다.  
A와 B는 사실 비교조차 할 수 없을 정도로 A가 훨씬 가독성이 좋은 코드다.  

</br>

그리고 사실 숙련된 코틀린 개발자도 이런 코드는 익숙하지 않아서 이해하는 데 시간이 꽤 걸릴 것이다.  
숙련된 개발자라고 내내 코틀린만 붙잡고 있는 것은 아니기 때문.  
사용 빈도가 적은 관용구는 코드를 복잡하게 만든다.  
그리고 그런 관용구들을 한 문장 내부에 조합해서 사용하면 복잡성은 훨씬 더 빠르게 증가시킨다.  

</br>

A와 B는 사실 실행결과가 다르다.  
showPerson에서 null이 리턴이 된다면 B구현은 showError()가 발생한다.  
이처럼 익숙치 않은 관용구를 사용하면 이러한 실수를 놓치기 쉽다. 

</br>

기본적으로 '인지 부하'를 줄이는 방향으로 코드를 작성하세요.  
우리 뇌는 패턴을 인식하고, 패턴을 기반으로 프로글매의 작동 방식을 이해합니다.  
'뇌가 프로그램의 작동 방식을 이해하는 과정'을 더 짧게 만드는 것입니다.

### 극단적이 되지 않기

위에서 let이 예상치 못한 결과를 나올 수 있다고 해서 무조건 let을 사용하지 말라는 건 아니다.  
nullable 가변 프로퍼티가 null이 아닐 때만 어떠한 작업을 수행해야 하는 경우가 있을 때  
가변 프로퍼티는 쓰레드와 관련된 문제를 발생시킬 수 있으므로, 스마트 캐스팅이 불가능하다.  
```kotlin
class Person(val name: String)
val person: Person? = null

fun printName() {
    person?.let {
        print(it.name)
    }
}
```

이외에도 다음과 같은 경우 let을 많이 사용한다.  
- 연산을 아규먼트 처리 후로 이동시킬 때
```kotlin
// print(students.filter{}.joinToString{})이란 코드에서 print를 뒤로 이동시킨 것.
students
    .filter { it.result >= 50 }
    .joinToString(separator = "\n") {
        "${it.name} ${it.surname}, ${it.result}"
    }
    .let(::print)
```
- 데코레이터를 사용해서 객체를 랩할 때
```kotlin
val obj = FileInputStream("/file.gz")
    .let(::BufferedInputStream)
    .let(::ZipInputStream)
    .let(::ObjectInputStream)
    .readObject() as SomeObject
```

이 코트들은 디버깅하기 어렵고, 경험이 적은 코틀린 개발자는 이해하기 어렵다. 따라서 비용이 발생한다.  
하지만, 이 비용은 지불할 만한 가치가 있으므로 사용해도 괜찮다.  
문제가 되는 경우는 비용을 지불할 만한 가치가 없는 코드에 비용을 지불하는 경우(정당한 이유 없이 복잡성을 추가할 때)입니다.  

### 컨벤션

```kotlin
val abc = "A" { "B" } and "C"
print(abc)

operator fun String.invoke(f: () -> String): String = 
    this + f()

infix fun String.and(s: String) = this + s
```

위 코트는 아래 설명하는 수많은 규칙을 위반한다.
- 연산자는 의미에 맞게 사용해야 한다. invoke를 이러한 형태로 사용하면 안 된다.
- '람다를 마지막 아규먼트로 사용한다'라는 컨벤션을 여기에 적용하면, 코드가 복잡해진다. invoke 연산자와 함께 이러한 컨벤션을 적용하는 것은 신중해야 한다.
- 현재 코드에서 and라는 함수 이름이 실제 함수 내부에서 이루어지는 처리와 맞지 않는다.
- 문자열을 결합하는 기능은 이미 언어에 내장되어 있다. 이미 있는 것을 다시 만들 필요가 없다.

## ITEM 12. 연산자 오버로드를 할 때 의미에 맞게 사용하라

kotlin에는 다양한 연산자와 대응되는 함수가 있다.  
예로  
- +a -> a.unaryPlus()
- -a -> a.unaryMinus()
- a+b -> a.plus(b)

```kotlin
x + y == z
x.plus(y).equal(z)
(x.plus(y))?.equal(z) ?: (z == null)
```
이는 구체적인 이름을 가진 함수이며, 모든 연산자가 이러한 이름이 나타내는 역할을 할 거라 기대한다.  
이를 마음대로 오버로드하면 안 된다.

<br>

#### 정리
연산자 오버로딩은 그 이름의 의미에 맞게 사용해 주세요. 연산자 의미가 명확하지 않다면, 연산자 오버로딩을 사용하지 않는 것이 좋습니다. 대신 이름이 있는 일반 함수를 사용하기 바랍니다. 꼭 연산자 같은 형태로 사용하고 싶다면, infix 확장 함수 또는 톱레벨 함수를 활용하세요.

## ITEM 13. Unit?을 리턴하지 말라

Unit?으로 Boolean을 표현하는 것은 오해의 소지가 있으며, 예측하기 어려운 오류를 만들 수 있다.  
지금까지 여러 코드를 보면서 Unit?을 쉽게 읽을 수 있는 경우는 거의 보지 못했다.  
따라서 Boolean을 사용하는 형태로 변경하는 게 좋다.  
기본적으로 Unit?을 리턴하거나, 이를 기반으로 연산하지 않는 것이 좋다.  

## ITEM 14. 변수 타입이 명확하지 않은 경우 확실하게 지정하라

```kotlin
val num = 10
val name = "Hyo"
val ids = listOf(12, 112, 554, 4213)

val data = getSomeData()
```

num, name, ids의 비해 data는 어떤 타입인지 쉽게 유추하기 어렵다.  
이 말은 가독성이 떨어진다는 말이다.  
intellij의 경우 idea 자체에서 타입추론을 해주고는 있지만, github과 같은 곳에서는  
해당 타입이 뭔지 알아보기 힘들다.  
이런 경우 타입을 지정해서 코드를 훨씬 쉽게 읽게 해주자.  
```kotlin
val data: UserData = getSomeData()
```

## ITEM 15. 리시버를 명시적으로 참조하라

리시버 (this, it과 같은 것들)

#### 정리
짧게 적을 수 있다는 이유만으로 리시버를 제거하지 말기 바란다.  
여러 개의 리시버가 있는 상황 등에서는 리시버를 명시적으로 적어 주는 것이 좋다.  
리시버를 명시적으로 지정하면, 어떤 리시버의 함수인지를 명확하게 알 수 있으므로, 가독성이 향상된다.  
DSL에서 외부 스코프에 있는 리시버를 명시적으로 적게 강제하고 싶다면, DslMarker 메타 어노테이션을 사용하자.