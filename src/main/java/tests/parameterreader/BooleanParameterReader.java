package tests.parameterreader;

/**
 * Created with IntelliJ IDEA.
 * User: fabrice
 * Date: 15.04.13
 * Time: 21:57
 * To change this template use File | Settings | File Templates.
 */
public class BooleanParameterReader implements IParameterReader<Boolean> {

    public Boolean readValue(String s) {
        return new Boolean(s);
    }
}
