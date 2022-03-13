# 1장 출생
## 1.1 -er로 끝나는 이름을 사용하지 마세요.
**클래스 이름을 짓는 잘못된 방식**  
클래스의 객체들이 무엇을 하고 있는(doing)지를 살펴본 후  
기능(functionality)에 기반해서 이름을 짓는 방식은 잘못되었습니다.
```
class CashFormatter {
    private int dollars;
    CashFormatter(int dlr) {
        this.dollars = dls;
    }
    public String format() {
        return String.format("$ %d", this.dollars);
    }
}
```

**클래스 이름을 짓는 올바른 방식**  
클래스 이름은 무엇을 하는지(what he does)가 아니라 무엇인지(what he is)에 기반해야 합니다.  
CashFormmatter라는 이름은 Cash, USDCash, CashInUSD와 같은 이름으로 바꿔야 하고,  
메서드 format()은 usd()로 수정해야 합니다.  
```
class Cash {
    private int dollars;
    Cash(int dlr) {
        this.dollars = dls;
    }
    public String usd() {
        return String.format("$ %d", this.dollars);
    }
}
```

객체는 객체의 외부 세계와 내부 세계를 이어주는 연결장치(connector)가 아닙니다.  
객체는 내부에 캡슐화된 데이터를 다루기 위해 요청할 수 있는 절차의 집합이 아닙니다.  
대신 객체는 캡슐화된 데이터의 대표자(representative)입니다.

그는 리스트이고, 인덱스 번호를 통해 특정 위치의 요소를 선택할 수 있습니다.  
그는 SQL 레코드이고, 임의의 셀에 저장된 정수값을 조회할 수 있습니다.  
그는 픽셀이고, 스스로 색을 바꿀 수 있습니다.  
그는 하나의 파일이고, 디스크에서 내용을 읽어올 수 있습니다.  
그는 인코딩 알고리즘이고, 인코드 작업을 수행할 수 있습니다.  
그는 HTML 문서이고, 브라우저에서 HTML을 렌더링할 수 있습니다.

내가 무엇을 하는 지와 내가 누구인지는 다릅니다.

## 1.2 생성자 하나를 주 생성자로 만드세요.
- ctor == constructor (생성자)  

**예제 코드**
```
class Cash {
    private int dollars;
    Cash(int dlr) {
        this.dollars = dls;
    }
}
```

이 예제의 ctor이 수행하는 하나의 작업은 인자로 전달된 달러를  
dollars라는 이름의 private 정수 프로퍼티에 캡슐화하는 일입니다.  

이 책에서 권장하는 클래스 설계 방식은 많은 수의 ctor과 적은 수의 메서드를 포함하는 것입니다.  
따라서, 2-3개의 메서드와 5-10개의 ctor을 포함하는 것이 적당합니다. (임의로 정한 수)  
여기에서 핵심은 응집도가 높고 견고한 클래스에는  
적은 수의 메서드와 상대적으로 더 많은 수의 ctor이 존재한다는 점입니다.  

다양한 방법으로 Cash 인스턴스를 생성하고 싶은 경우 다음과 같이 여러 종류의 ctor을 활용합니다.  
```
new Cash(30);
new Cash("$29.95");
new Cash(29.95d);
new Cash(29.95f);
new Cash(29.95, "USD");
```
이렇게 생성된 객체들은 모두 동일하며, 동일하게 행동합니다.  
ctor이 많아질수록 클라이언트가 클래스를 더 유연하게 사용할 수 있게 됩니다.  
하지만, 메서드가 많아질수록 클래스를 사용하기는 더 어려워집니다.  
메서드가 많아지면 클래스의 초점이 흐려지고, 단일 책임 원칙(Single Responsibility Principle)을 위반하기 때문입니다.  
이에 비해 ctor이 많아지면 유연성이 향상됩니다.  

ctor의 주된 작업은 제공된 인자를 사용해서 캡슐화하고 있는 프로퍼티를 초기화하는 일입니다.  
이런 초기화 로직을 단 하나의 ctor에만 위치시키고, '주(primary)' ctor이라고 부르기를 권장하며,  
다음 예제처럼 '부(secondary)' ctor이라고 부르는 다른 ctor들이 이 주 ctor을 호출하도록 만들기 바랍니다.  
```
class Cash {
    private int dollars;
    Cash(float dlr) {
        this((int) dlr);
    }
    Cash(String dlr) {
        this(Cash.parse(dlr));
    }
    Cash(int dlr) {
        this.dollars = dls;
    }
}
```
주 ctor을 모든 부 ctor 뒤에 위치시키는 이유는 유지보수성 때문입니다.  
마지막에 정의된 ctor로 곧장 스크롤을 내렸을 때 그곳에 항상 주 ctor이 있다면 유지보수가 편합니다.  

'하나의 주 ctor과 다수의 부 ctor(one primary, many secondary)' 원칙의 핵심은,  
중복 코드를 방지하고 설계를 더 간결하게 만들기 때문에 유지보수성이 향상됩니다.  
유효성 검사 로직이 포함되어야 한다면, 모든 ctor에서 만들어야 되는 중복 코드가 발생하기 때문입니다.  



## 1.3 생성자에 코드를 넣지 마세요.
- 주 ctor은 객체 초기화 프로세스를 시작하는 유일한 장소이기 때문에 제공되는 인자들은 완전해야 합니다.  

이 책의 첫 번째 목표는 OOP에 대한 사고방식과 이해의 깊이를 바꾸는 것입니다.  
두 번째 목표는 실용적인 예제들을 제공하고 이를 통해 새로운 사고방식을 코드에 적용하는 것입니다.  

진정한 객체지향에서 인스턴스화란 더 작은 객체들을 조합해서 더 큰 객체를 만드는 것을 의미합니다.  
객체들을 조합해야 하는 단 하나의 이유는 새로운 계약을 준수하는  
새로운 엔티티(entity)가 필요하기 때문입니다.  

제일 처음 할 일은 객체를 인스턴스화하는 것입니다.  
두 번째 할 일은 객체가 우리를 위해 작업을 하게 만드는 것입니다.  
이 두 단계가 겹쳐서는 안됩니다.  
ctor은 어떤 일을 수행하는 곳이 아니기 때문에 ctor 안에서 인자에게 어떤 작업을 하도록 요청해서는 안됩니다.  

**첫 번째 코드**  
```
class StgringAsInteger implements Number {
    private String text;
    public StringAsInteger(String txt) {
        this.text = txt;
    }
    public int intValue() {
        return Integer.parseInt(this.text);
    }
}
```
**두 번째 코드**  
```
class StgringAsInteger implements Number {
    private int num;
    public StringAsInteger(String txt) {
        this.num = Integer.parseInt(txt);
    }
    public int intValue() {
        return this.num;
    }
}
```
첫 번째 코드는 intValue()를 호출할 때마다 매번 텍스트를 정수로 파싱합니다.  
두 번째 코드는 초기화하는 시점에 단 한 번 텍스트를 파싱하고 있습니다.  
실제로 두 번째 코드가 효율적으로 동작하고, 그렇게 생각할 수 있지만,  
ctor에서 직접 파싱을 수행하는 두 번째 코드는 최적화가 불가능합니다.  
객체를 만들 때마다 매번 파싱이 수행되기 때문에 실행 여부를 제어할 수 없습니다.  
intValue() 를 호출할 필요가 없는 경우에도 CPU는 파싱을 위해 시간을 소모합니다.  

```
Number five = new StringAsInteger("5");
if (/* 무언가 잘못되었다면 */) {
    throw new Exception("어떤 문제");
}
five.intValue();
```

인자를 전달된 상태 그대로 캡슐화하고 나중에 요청이 있을 때 파싱하도록 하면,  
클래스의 사용자들이 파싱 시점을 자유롭게 결정할 수 있게 됩니다.  

파싱이 여러 번 수행되지 않도록 하고 싶다면 **데코레이터(decorator)** 를 추가해서  
최초의 파싱 결과를 캐싱할 수도 있습니다.  
```
class CachedNumber implements Number {
    private Number origin;
    private Collection<Integer> cached = new ArrayList<>(1);
    public CachedNumber(Number num) {
        this.origin = num;
    }
    public int intValue() {
        if (this.cached.isEmpty()) {
            this.cached.add(this.origin.intValue());
        }
        return this.cached.get(0);
    }
}
```
이 예제는 꽤나 원시적인 캐싱 구현체지만 이 코드로부터 영감을 얻기 바랍니다.  
이제 효율적으로 실행될 필요가 있는 객체를 감싸기 위해서 캐싱 데코레이터를 사용할 수 있습니다.  
```
Number num = new CachedNumber(
    new StringAsInteger("123")
);
num.intValue(); // 첫 번째 파싱
num.intValue(); // 파싱하지 않음.
```
주 생성자에서 코드를 없애면 사용자가 쉽게 제어할 수 있는 투명한 객체를 만들 수 있으며,  
객체를 이해하고 재사용하기도 쉬워집니다.  
객체는 요청을 받을 때만 행동하고 그 전에는 어떤 일도 하지 않습니다.  
이 객체들은 좋은 방식으로 매우 '게으릅니다.(lazy)'.  

분명히 파싱이 단 한번만 수행되는 경우도 있습니다.  
이런 경우에도 ctor에서 다른 일을 수행하지 않는 편이 좋은 이유는 무엇일까요?  
수행할 수는 있겠지만, 저는 일관성(uniformity)이라는 측면에서 반대합니다.  
여러분은 이 클래스의 미래에 어떤 일이 일어날 지, 다음 리팩토링 시점에 얼마나 많은  
변경이 더해질지 알 지 못합니다.  
ctor 안에서 어떤 일을 처리하고 있다면 나중에 리팩토링하기가 훨씬 어렵습니다.  
리팩토링을 수행하는 프로그래머는 ctor 내부의 처리 로직을 메서드로 옮기고 나서야  
코드를 실제로 변경할 수 있겠습니다.

# 2장 학습
## 2.1 가능하면 적게 캡슐화하세요.
> 기억하세요. 모든 것은 유지보수성과 관련이 있습니다.  
복잡성은 직접적으로 **유지보수성**에 영향을 미칩니다.

**4개** 또는 그 이하의 객체를 캡슐화할 것을 권장 합니다.  
더 많은 객체를 캡슐화해야 한다면, 클래스에 문제가 있는 것이기 때문에 리팩토링이 필요합니다.  

수십 개의 객체를 캡슐화하고 있는 클래스는 완전히 잘못된 방식으로 구현된 것입니다.  
더 많은 객체가 필요하다면, 클래스를 더 작은 클래스들로 분해해야 합니다.  


내부에 캡슐화된 객체 전체를 가리켜 객체의 '상태' 또는 '식별자'라고 부릅니다.  
```
class Cash {
    private Integer digits;
    private Integer cents;
    private String currency;
}
```  
상태가 동일하면, 식별자도 동일해야 합니다.  
하지만, C++로부터 물려받은 Java 언어의 설계적 결함으로 인해  
동일한 '상태'를 가지고 있더라 하더라도, new 로 두 객체가 생성되어 비교하게 되면  
다른 서로 동일하지 않다는 결과를 얻게 됩니다.  
자바를 비롯한 대부분의 OOP 언어에서 객체는 단지 메서드가 추가된 데이터 집합일 뿐입니다.  
  
객체는 데이터를 저장할 수 있는 껍질(shell)과 유사합니다.  
데이터의 저장 여부와 상관없이 복사본을 비교하는 경우에도 하나의 껍질은 다른 껍질과는 다릅니다.  
```
Object x = new Object();
Object y = new Object();
assert x.equals(y); // 실패
```
두 객체는 서로 다른 껍질이기 때문에 동일하지 않습니다.  
이것이 Java가 객체를 바라보는 방식입니다.  
그리고 동시에 끔찍하게 잘못된 방식입니다.  

> 상태 없는 객체는 존재해서는 안되고, 상태는 객체의 식별자여야 합니다.  

자바의 결함을 해결하기 위해 == 연산자를 사용하지 말고,  
항상 eqauls() 메서드를 오버라이드하길 바랍니다.  

## 2.2 최소한 뭔가는 캡슐화하세요.
## 2.3 항상 인터페이스를 사용하세요.
## 2.4 메서드 이름을 신중하게 선택하세요.
### 2.4.1 빌더는 명사다.
### 2.4.2 조정자는 동사다.
### 2.4.3 빌더와 조정자 혼합하기
### 2.4.4 Boolean 값을 결과로 반환하는 경우
## 2.5 퍼블릭 상수(public constant)를 사용하지 마세요.
### 2.5.1 결합도 증가
### 2.5.2 응집도 저하
## 2.6 불변 객체로 만드세요.
### 2.6.1 식별자 가변성(Identity Mutability)
### 2.6.2 실패 원자성(Failure Atomicity)
### 2.6.3 시간적 결합(Temporal Coupling)
### 2.6.4 부수효과 제거(Side effect-free)
### 2.6.5 NULL 참조 없애기
### 2.6.6 스레드 안전성
### 2.6.7 더 작고 더 단순한 객체
## 2.7 문서를 작성하는 대신 테스트를 만드세요.
## 2.8 모의 객체(Mock) 대신 페이크 객체(Fake)를 사용하세요.
## 2.9 인터페이스를 짧게 유지하고 스마트(smart)를 사용하세요.

# 3장 취업
## 3.1 5개 이하의 public 메서드만 노출하세요.
## 3.2 정적 메서드를 사용하지 마세요.
### 3.2.1 객체 대 컴퓨터 사고(object vs. computer thinking)
### 3.2.2 선언형 스타일 대 명령형 스타일(declarative vs. imperative style)
### 3.2.3 유틸리티 클래스(Utility classes)
### 3.2.4 싱글톤(Singleton) 패턴
### 3.2.5 함수형 프로그래밍
### 3.2.6 조합 가능한 데코레이터
## 3.3 인자의 값으로 NULL을 절대 허용하지 마세요.
## 3.4 충성스러우면서 불변이거나, 아니면 상수이거나
## 3.5 절대 getter와 setter를 사용하지 마세요.
### 3.5.1 객체 대 자료구조
### 3.5.2 좋은 의도, 나쁜 결과
### 3.5.3 접두사에 관한 모든 것
## 3.6 부 ctor 밖에서는 new를 사용하지 마세요.
## 3.7 인트로스펙션과 캐스팅을 피하세요.

# 4장 은퇴
## 4.1 절대 NULL을 반환하지 마세요.
### 4.1.1 빠르게 실패하기 vs. 안전하게 실패하기
### 4.1.2 NULL의 대안
## 4.2 체크 예외(checked exception)만 던지세요.
### 4.2.1 꼭 필요한 경우가 아니라면 예외를 잡지 마세요
### 4.2.2 항상 예외를 체이닝하세요
### 4.2.3 단 한번만 복구하세요
### 4.2.4 관점-지향 프로그래밍을 사용하세요
### 4.2.5 하나의 예외 타입만으로도 충분합니다
## 4.3 final이나 abstract이거나.
## 4.4 RALL를 사용하세요.