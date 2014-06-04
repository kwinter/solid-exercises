TheLadders' SOLID Exercises - The Dependency Inversion Principle
===============

Watch [the code-cast](http://www.cleancoders.com/codecast/clean-code-episode-13/show) then dig into the exercise.

---

At its core, the Dependency Inversion Principle states that "High level policy should not depend on low level detail. Low level detail should depend on high level policy." With that in mind, first take a look at SuggestedArticleExample.

* What direction will the dependencies flow in code that uses this class?
* Does this class represent a violation of the Principle?
* Does the use of ORM tools necessarily cause violations?

Now look at SubscriberArticleManagerImpl. Notice that there are many direct dependencies on systems that should be handled as plug-ins instead.

* What systems / modules does this class depend on?
* Which of these are compile-time dependencies? Which of these are run-time dependencies?

Try refactoring the SubscriberArticleManager so that other modules act as plugins into it. You'll likely have to tangle with the Single Responsibility Principle as well.
