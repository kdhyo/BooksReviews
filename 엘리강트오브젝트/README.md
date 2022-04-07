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

어떤 부분이 문제일까요?  
정적 메서드가 존재하지 않고 인스턴스 생성과 실행을 엄격하게 분리하는  
순수한 OOP에서는 기술적으로 프로퍼티가 없는 클래스를 만들 수 없기 때문입니다.  

### 프로퍼티가 없는 잘못 설계된 클래스
```
class Year {
    int read() {
        return System.currentTimeMillis()
            / (1000 * 60 * 60 * 24 * 30 * 12) - 1970;
    }
}
```

### 프로퍼티를 추가해 캡슐화한 클래스
```
class Year {
    private Millis millis;
    Year(Millis msec) {
        this.millis = msec;
    }
    int read() {
        return this.millis.read()
            / (1000 * 60 * 60 * 24 * 30 * 12) - 1970; 
    }
}
```

### 완벽한 객체지향 설계를 적용한 모습 1
```
class Year {
    private Number num;
    Year(final Millis msec) {
        this.num = new Min(
            new Div(
                msec,
                new Mul(1000, 60, 60, 24, 30, 12)
            ),
            1970
        );
    }
    int read() {
        return this.num.intValue();
    }
}
```
### 완벽한 객체지향 설계를 적용한 모습 2
```
class Year {
    private Number num;
    Year(final Millis msec) {
        this.num = msec.div(
            1000.mul(60).mul(60).mul(24).mul(30).mul(12)
        ).min(1970);
    }
    int read() {
        return this.num.intValue();
    }
}
```

## 2.3 항상 인터페이스를 사용하세요.

> 애플리케이션 전체를 유지보수 가능하도록 만들기 위해서는 최선을 다해서 객체를 분리해야 합니다.  
기술적인 관점에서 객체 분리란 상호작용하는 다른 객체를 수정하지 않고도  
해당 객체를 수정할 수 있도록 만든다는 것을 의미합니다.  
이를 가능하게 하는 가장 훌륭한 도구가 바로 인터페이스 입니다.  

> 인터페이스를 통해 구현체를 쉽게 바꿀 수 있도록 느슨한 결합을 하도록 해야 합니다.

## 2.4 메서드 이름을 신중하게 선택하세요.
> 메서드의 목적이 무엇인지 확인하세요.  
메서드는 빌더나 조정자 둘 중 하나여야 합니다.  
결코 빌더인 동시에 조정자여서는 안됩니다.  
빌더라면 이름을 명사로, 조정자면 이름을 동사로 지어야 합니다.  
Boolean 값을 반호나한느 빌더는 예외에 속합니다.  
이 경우에는 이름을 형용사로 지어야 합니다.

### 2.4.1 빌더는 명사다.
> 빌더(Builder)란 뭔가를 만들고 새로운 객체를 반호나하는 메서드를 가리킵니다.  
빌더는 void가 될 수 없으며, 이름은 항상 명사여야 합니다.  

```
int pow(int base, in power);
float speed();
Employee employee(int id);
String parsedCell(int x, int y);
```

```
int add(int x, int y);
```
대신
```
int sum(int x, int y);
```
> 객체에게 더하라고(add)요청하는 것이 아닌,  
두 수의 합(sum)을 계산하고 새로운 객체를 반환해 달라고 요청하는 것.

### 2.4.2 조정자는 동사다.
> 조정자(manipulator)는 반환 타입이 항상 void며, 이름은 항상 동사여야 합니다.

```
void save(String content);
void put(String key, Float value);
void remove(Employee emp);
void quicklyPrint(int id);
```

### 2.4.3 빌더와 조정자 혼합하기
### 2.4.4 Boolean 값을 결과로 반환하는 경우
> 반환하기 때문에 빌더에 속하지만, 가독성 측면에서 형용사로 지어야 합니다.  

```
boolean empty(); // is empty
boolean readable(); // is readable
boolean negative(); // is negative
```

## 2.5 퍼블릭 상수(public constant)를 사용하지 마세요.
> 예시 코드  

```
public class Constants {
    public static final String EOL = "\r\n";
}

class Records {
    void write(Writer out) {
        for (Record rec : this.all) {
            out.write(rec.toString());
            out.write(Constants.EOL); // 여기!!
        }
    }
}

class Rows {
    void print(PrintStream pnt) {
        for (Row row : this.fetch()) {
            pnt.printf(
                "{ %s }", row, Constants.EOL // 여기!!
            );
        }
    }
}
```

### 2.5.1 결합도 증가
> 예제 코드 두 클래스는 모두 같은 객체에 의존하고 있으며,  
이 의존성은 하드 코딩되어 있습니다.

> Constants.EOL 을 변경하는 입장에서는 이 값이 어떻게 사용되고 있는지 알 수 없습니다.  
어떤 곳에서는 출력 중 한 줄을 넘기기 위해서 사용하고 있을 수 있으며,
다른 곳에서는 HTTP 프로토콜에 포함된 콘텐츠 한 줄을 종료하기 위해 사용하고 있을 수 있습니다.  

> 많은 객체들이 다른 객체를 사용하는 상황에서 서로를 어떻게 사용하는지 알 수 없다면,  
이 객체들은 매우 강하게 결합되어 있는 것입니다.

### 2.5.2 응집도 저하
> 객체 사이에 데이터를 중복해서는 안됩니다.  
대신 기능을 공유할 수 있도록 새로운 클래스를 만들어야 합니다.  
다시 한 번 강조하지만, 데이터가 아니라 기능을 공유해야 합니다.

예시
```
class EOLString {
    private final String origin;
    EOLString(String src) {
        this.origin = src;
    }

    @Oberride
    String toString() {
        return String.format("%s\r\n", origin);
    }
}

class Recoreds {
    void write(Writer out) {
        for (Record rec : this.all) {
            out.write(new EOLString(rec.toString()));
        }
    }
}

class Rows {
    void print(PrintStream pnt) {
        for (Row row : this.fetch()) {
            pnt.print(
                new EOLString(
                    String.format("{ %s }", row)
                )
            );
        }
    }
}
```

## 2.6 불변 객체로 만드세요.
> 가변 객체 사용을 엄격하게 금지해야 합니다.  
> five 객체는 생성하고나면 fifty가 될 수 없습니다.
> 5를 세팅한 후에 50을 세팅하는 건 말이 안됩니다.
### 2.6.1 식별자 가변성(Identity Mutability)
```
Map<Cash, String> map = new HashMap<>();
Cash five = new Cash("$5");
Cash ten = new Cash("$10");
map.put(five, "five");
map.put(ten, "ten");
five.mul(2);
System.out.println(map); // {$10 => "five", $10 => "ten"}
```
> 이처럼 둘 중 하나를 검색하게 되면 어떤 결과를 얻을지 예측할 수 없습니다.  
> 불변객체를 사용하면 위처럼 값 변경을 컴파일에서 못하도록 막아주기 때문에 불변을 쓰는 게 좋습니다.
### 2.6.2 실패 원자성(Failure Atomicity)
> 가변 객체일 경우
```
class Cash {
    private int dollars;
    private int cents;
    public void mul(int factor) {
        this.dollars *= factor;
        if (뭔가 잘못 됐다면) {
            throw new RuntimeException("oops...");
        }
        this.cents *= factor;
    }
}
```
> 불변 객체일 경우
```
class Cash {
    private final int dollars;
    private final int cents;
    public Cash mul(int factor) {
        if (뭔가 잘못 됐다면) {
            throw new RuntimeException("oops...");
        }
        return new Cash(
            this.dollars * factor,
            this.cents * factor
        );
    }
}
```
> 이처럼 가변 객체일 경우 예외가 발생 시 기존에 변경된 건 변경이 유지되는 현상이 있지만,  
> 불변객체일 경우 성공일 때 새로운 객체를 반환하기 때문에 이러한 문제를 막을 수 있다.  
> 물론, 가변 객체도 막을 수는 있지만 신경을 많이 써야하며 누락할 가능성이 존재한다.
### 2.6.3 시간적 결합(Temporal Coupling)
```
Cash prive = new Cash();
price.setDollars(29);
System.out.println(price); // "$29.00"!
price.setCents(95);
```
> 이처럼 settter 를 사용하게 되면 값 세팅이 늦게 되어 원하던 결과가 아닌 다른 결과를 얻을 수도 있다.  
> 이러한 상황은 컴파일러가 아무런 도움을 줄 수 없습니다.  
> 가변 객체 수가 많은 상황에서 처리하는 연산들의 순서를 일일이 기억해야 한다면  
> 유지보수에 있어서 어려움이 크게 됩니다.
### 2.6.4 부수효과 제거(Side effect-free)
> 객체 내부에 실수를 하여 side effect 가 발생하는 현상을 초반부터 잡을 수 있습니다.
### 2.6.5 NULL 참조 없애기
> Null 을 처음부터 셋팅할 수 없도록 불변객체로 만드는 것이 유지보수에 훨씬 좋습니다.  
> 무분별한 setter 로 인해 null이 들어가는 것을 방지할 수 있습니다.  
> 불변객체로 만들게 되면 작고, 견고하고, 응집도 높은 객체를 생성할 수 밖에 없도록  
> 강제되기 때문에 결과적으로 유지보수하기에 훨씬 쉬운 객레를 만듭니다.
### 2.6.6 스레드 안전성
> 가변 객체는 스레드에 안전하지 못한 객체입니다.  
> 우리는 멀티 쓰레드 환경인 것을 인지해야 합니다.  
> 공유 객체인 경우 가변 객체는 스레드에 안전하지 못하며  
> 스레드 안전을 위해 synchronized 를 사용하는 행위는 성능상 비싼 비용을 초례하고,  
> 데드락이 발생할 수 있습니다.
### 2.6.7 더 작고 더 단순한 객체
> 객체가 단순할수록 유지보수는 쉬워집니다.  
> 최고의 소프트웨어는 이해하고, 수정하고, 문서화하고, 지원하고, 리팩토링하기 쉽습니다.  
> 유지보수성은 현대적인 프로그래밍이 갖춰야 하는 가장 중요한 덕목입니다.  
> 불변 객체를 아주 크게 만드는 일은 불가능하기 때문에,  
> 일반적으로 불변 객체는 가변 객체보다 더 작습니다.  
> 불변 객체가 작을 수 있는 이유는 생성자에서만 상태를 초기화하기 때문입니다.  
## 2.7 문서를 작성하는 대신 테스트를 만드세요.
> 알아보기 힘든 코드를 문서로 정리하는 것보다 리팩토링을 통해 깔끔하게 만드는 것이 중요하다.  
> 깔끔하게 만드는 것에는 `단위테스트` 가 포함이 되어있으며,   
> 훌륭하고 깔끔한 단위테스트를 만들기 위해서는 `메인 메소드`만큼 관심을 기울이는 것이다.
## 2.8 모의 객체(Mock) 대신 페이크 객체(Fake)를 사용하세요.
> 모킹은 나쁜 프랙티스이며, 최후의 수단으로만 사용하여야 한다.  
> 페이크 클래스를 사용하면 테스트를 더 짧게 만들 수 있기 때문에 유지보수성이 눈에 띄게 향상됩니다.  
> 반면에 모킹의 경우 테스트가 장황해지고, 이해하거나 리팩토링하기 어려워집니다.  
> 모킹을 사용하게 되면 단위 테스트를 상호작용에 의존하도록 만듦으로써  
> 리팩토링을 고통스럽고 때로는 불가능하게 만들기 때문입니다.  
> 객체와 의존 대상 사이의 상호작용 방식을 확인하거나 테스트해서는 안됩니다.  
> 이것은 객체가 캡슐화해야 하는 정보입니다. 다시 말해서 객체가 숨겨야 하는 비밀입니다.  
> 페이크 클래스는 인터페이스의 설계에 관해 더 깊이 고민하도록 해줍니다.  
> 인터페이스를 설계하면서 페이크 클래스를 만들다보면 필연적으로 인터페이스의 작성자뿐만  
> 아니라 사용자의 관점에서도 고민하게 됩니다.  
> 인터페이스를 다른 각도에서 바라보고, 테스트 리소스를 사용해서 사용자와 동일한 기능을 구현합니다.
## 2.9 인터페이스를 짧게 유지하고 스마트(smart)를 사용하세요.
> 두 개의 인터페이스가 있는데 각각 5개씩 있다고 생각해봅시다.  
> 그 두 개의 인터페이스를 구현하는 클래스는 총 10개의 public 메소드를 가지게 됩니다.  
> 메소드의 public 수는 작을 수록 응집도가 높고, 유지보수가 높습니다.  
> 인터페이스 또한 작게 유지하는 것이 좋습니다.

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