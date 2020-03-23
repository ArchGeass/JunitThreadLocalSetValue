# JunitThreadLocalSetValue

在使用Junit框架时，怎么给fork出来的项目中的ThreadLocal传值，使得在不改动项目代码的前提下，ThreadLocal获取到Junit中设置的Value?
ps.在尽可能不改动Verify代码的情况下

## 解释:
这是工作中遇到的问题：在正常的流程中，前端调用controller会传递token，然后项目会解析token，再set某些value到{@link #LoginUser}中；
但在Junit测试框架中，调用业务的Service会跳过上述的解析步骤，所以当需要获取LoginUser中的attribute时，就获取不到。
并且项目通过某些方式使得在Junit中调用业务Service会创建一个新的Thread，使得业务Thread与测试代码不在同一个线程中，
所以在测试代码中执行setAttribute是无法在new Thread中获取到对应的attribute.

有没有什么方法在不改动业务代码的情况下，在测试调用时执行某些操作，可以使得新创建的业务线程可以get到测试代码设置的attribute呢...

具体项目代码是如何使得在Junit调用业务代码时，使用新的线程去执行，具体的实现步骤我还没搞懂，只知道是通过proxy实现的

## run
如果不是通过线程池创建，而是通过Spring的@Async实现，又应该如何实现上述效果呢?
[runWith](https://github.com/ArchGeass/JunitThreadLocalSetValue/blob/master/src/test/java/org/github/geass/service/VerifyTest.java)
