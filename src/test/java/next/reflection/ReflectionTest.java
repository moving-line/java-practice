package next.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import next.optional.User;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    public void showClass() {
        Class<Question> clazz = Question.class;
        logger.debug(clazz.getName());

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            logger.debug("field name:{}, modifier:{}", field.getName(), field.getModifiers());
        }

        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        for (Constructor<?> constructor : constructors) {
            logger.debug("constructor name:{}, modifier:{}, parameter type:{}", constructor.getName(), constructor.getModifiers(), constructor.getParameterTypes());

        }

        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            logger.debug("method name:{}, modifier:{}, parameter type:{}", method.getName(), method.getModifiers(), method.getParameterTypes());
        }
    }

    @Test
    @SuppressWarnings("rawtypes")
    public void constructor() throws Exception {
        Class<Question> clazz = Question.class;
        Constructor[] constructors = clazz.getConstructors();
        for (Constructor constructor : constructors) {
            Class[] parameterTypes = constructor.getParameterTypes();
            logger.debug("paramer length : {}", parameterTypes.length);
            for (Class paramType : parameterTypes) {
                logger.debug("param type : {}", paramType);
            }
        }
    }

    @Test
    public void privateFieldAccess() throws Exception {
        Class<Student> clazz = Student.class;
//        Student student = clazz.newInstance(); deprecated!!
        Student student = clazz.getDeclaredConstructor().newInstance();
        Field name = clazz.getDeclaredField("name");
        name.setAccessible(true);
        name.set(student, "새이름");
        logger.debug("name:{}, age:{}", student.getName(), student.getAge());
    }

    @Test
    public void generateConstructor() throws Exception  {
        Class<User> clazz = User.class;
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        logger.debug("생성자 갯수 : {}", constructors.length);

        for (Constructor<?> constructor : constructors) {
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            logger.debug("생성자의 파라미터 타입 : {}", (Object) parameterTypes);

            User user = (User)constructor.newInstance("name", 18);
            logger.debug(user.toString());
        }
    }
}
