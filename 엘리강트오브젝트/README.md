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
따라서, 2~3개의 메서드와 5~10개의 ctor을 포함하는 것이 적당합니다. (임의로 정한 수)  
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

# 2장 학습
## 2.1 가능하면 적게 캡슐화하세요.
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