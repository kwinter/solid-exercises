TheLadders' SOLID Exercises - The Liskov Substitution Principle
===============

Watch [the code-cast](http://www.cleancoders.com/codecast/clean-code-episode-11-p1/show) then dig into the exercise.

---

First take a look at the Environment class. Notice that it extends HashMap<Object, Object>.

* What Liskov Substitution Principle violations are there, if any?

Next see DynamicEnvironment. Notice that it extends Environment.

* What Liskov Substitution Principle violations are there, if any?
* Which heuristics indicate a possible violation?
* If there are violations, which supertype contract(s) is/are being violated?
* What modifications could you make to avoid violations, if any?

Go ahead and try refactoring the code and answer the questions above.
