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