# JunitThreadLocalSetValue

在使用Junit框架时，怎么给fork出来的项目中的ThreadLocal传值，使得在不改动项目代码的前提下，ThreadLocal获取到Junit中设置的Value?
ps.在尽可能不改动Verify代码的情况下

## 解释:
这是工作中遇到的问题：在正常的流程中，前端调用controller会传递token，然后项目会解析token，再set某些value到{@link #LoginUser}中；
但在Junit测试框架中，调用业务的Service会跳过上述的解析步骤，所以当需要获取LoginUser中的attribute时，就获取不到。
并且项目通过某些方式使得在Junit中调用业务Service会创建一个新的Thread，使得业务Thread与测试代码不在同一个线程中，
所以在测试代码中执行setAttribute是无法在new Thread中获取到对应的attribute.

有没有什么方法在不改动业务代码的情况下，在测试调用时执行某些操作，可以使得新创建的业务线程可以get到测试代码设置的attribute呢...

另外遇到的一个小问题是接收submit返回值的`Future`，在执行`Future.get()`方法时为null，还没搞懂原因

## run
如果不是通过线程池创建，而是通过Spring的`@Async`实现，又应该如何实现上述效果呢?

[runWith](https://github.com/ArchGeass/JunitThreadLocalSetValue/blob/Async/src/test/java/org/github/geass/service/VerifyTest.java)

## 想到的一点思路:
既然老师之前的解决思路是在创建新线程调用前，通过某些方式(实现Runnable)调用`setAttribute()`，那么@Async的解决方式应该也可以用同样的思路来解决。
我想到的是：既然@Async是通过cglib代理来实现创建新线程来实现异步调用，那么我可不可以自己在调用异步Service时，通过某些配置在Spring的代理实现中动态加入一些自己的代码呢？或者我在@Async的代理实现之外再自己实现一个代理，以使得我的代理实现了@Async的代理呢...
