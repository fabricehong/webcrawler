package tests;

import tests.parameterreader.BooleanParameterReader;
import tests.parameterreader.IParameterReader;
import tests.parameterreader.IntegerParameterReader;
import tests.parameterreader.StringParameterReader;

import java.util.HashMap;
import java.util.Map;

public class ParameterDescriptionFactory {
    IParameterReader defaultReader = new BooleanParameterReader();

    private static Map<Character, IParameterReader> symbolToParameterReader;

    static {
        symbolToParameterReader = new HashMap<Character, IParameterReader>();
        symbolToParameterReader.put('#', new IntegerParameterReader());
        symbolToParameterReader.put('%', new StringParameterReader());
    }


    public ParameterDescriptionFactory() {
    }

    Parameter createParamDescr(String varDescr) {
        if (varDescr.length() < 1) {
            throw new RuntimeException("parameterreader description is malformed");
        }
        char varName = varDescr.charAt(0);

        IParameterReader reader;
        if (varDescr.length()<2) {
            reader = defaultReader;
        } else {
            reader = determineParameterReader(varDescr.charAt(1));
        }

        return new Parameter(varName, reader);
    }

    private IParameterReader determineParameterReader(Character symbol) {
        IParameterReader iParameterReader = symbolToParameterReader.get(symbol);
        if (iParameterReader==null) {
            throw new RuntimeException("no parameterreader reader for symbol '"+symbol+"'");
        }
        return iParameterReader;
    }
}