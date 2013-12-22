package tests.parameterreader;

/**
 * Created with IntelliJ IDEA.
 * User: fabrice
 * Date: 15.04.13
 * Time: 22:03
 * To change this template use File | Settings | File Templates.
 */
public interface IParameterReader<T> {
    T readValue(String s);
}
