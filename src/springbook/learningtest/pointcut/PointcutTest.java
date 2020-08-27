package springbook.learningtest.pointcut;

import org.junit.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PointcutTest {
    @Test
    public void methodSignaturePointcut() throws SecurityException, NoSuchMethodException {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(public int springbook.learningtest.pointcut.Target.minus(int, int) throws java.lang.RuntimeException)");

        // Target.minus()
        assertThat(pointcut.getClassFilter().matches(Target.class), is(true));
        assertThat(pointcut.getMethodMatcher().matches(Target.class.getMethod("minus", int.class, int.class), null), is(true));

        // Target.plus()
        assertThat(pointcut.getClassFilter().matches(Target.class), is(true));
        assertThat(pointcut.getMethodMatcher().matches(Target.class.getMethod("plus", int.class, int.class), null), is(false));

        // Bean.method()
        // TODO: 클래스를 필터링 하는 것도 getMethodMatcher에서 진행하는 듯. ClassFilter는 항상 true를 반환하는 거 같다.
        assertThat(pointcut.getClassFilter().matches(Bean.class), is(true));
        assertThat(pointcut.getMethodMatcher().matches(Bean.class.getMethod("method"), null), is(false));
    }
}
