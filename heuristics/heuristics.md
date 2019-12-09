Code Quality?

## First Principes

- Coupling: Changes tend to go together in same class/file
- Cohesion: Colocateed things seem like they belong together
- Complexity: Number of cuts/paths thru a method/func/class
- Cognitive Load: How hard is it to read... Boundaries... 

## SOLID

### Single Responsibility - a class/function should have one and only one reason to change aka a single responsibility

- Count the responsibilities
- Do they make sense together
- Coordination class? Controller
- Model class? Represents a domain concept * SRP
- More applicable in the guts of your system... service layers, domain models, 
- Classes either coordinate or the encapsulate biz logic

### Open Closed Principle - A class should be open to extension but closed to modification 

- A little less applicavble
- Refactor to this with the benefit of hindsight
- Speculative Generality
- Refactor: Change Signature
- Look for call sites... what comes before? Is it repetitive and consistent?
- On your own time: look up the gang of four strategy pattern 

### Liskov Substitutability Principle

- When you have multiple classes that implement same interface ONLY act thru the interface, do not cast
- Elevate type expectations to the function/method/ctor interface

## Interface Segration Principle

- Break up your interfaces into bundle of cohesion
- Look for large interfaces
- Code Review: 3-5 methods on an interface 

### Law of Demeter

- NO DEEP DOTTING / CHAINING
- Exception: fluent interfaces... newLoan().withAskOf(300000).forBlah() // Streams / Build Pattern
- self-referential -> returns this;
- loan.getOriginator().setAddress(newAddr); -> loan.changeAddress(newAddr);
- Would you like to know more? Check out the aggregate root pattern from DDD.
- Book: Verner Vaughn DDD Book
- Anti-pattern: Anemic Model
- Collection Pipeline Programming

## Command Query Seperation CQS 

Every method should either be a:

1. Command with destructive side-effects (changing state, calls IO)
2. Query that is idempotent (memoizable)

```
public int recordRolls(int pins) {
  _score += pins;
  return _score;
}

public void recordRolls(int pins);

public int getScore();
```
- Testing with mock objects
- Dial it up to 11 - Command Query Responsibility Segration / Event Sourcing / Event Driven Arch.


## The Hollywood Principle / Tell Don't Ask

- [See Martin's Article](https://martinfowler.com/bliki/TellDontAsk.html)
- Instead of asking about an object to then go ahead and act on it... have the object figure out its own behavior

## Intention Revealing Interfaces

- Variable names make sense
- public void usesWith(object)

## Immutability

## Don't Mock What You Don't Own
- Use an adapter
- Tradeoff: wrap & adapt EVERYTHING
- Hexagonal Architecture
- No mocky third party 

## Code Review: Start with tests...

- One logical assertion / test
- Arrange, Act, Assert
- Test Behavior Not Implementation, especially in the names of tests