package com.pxene.pap.common;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;


public class ScriptUtils
{
    public static Object eval(String script) throws ScriptException
    {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        
        ScriptEngine engine = scriptEngineManager.getEngineByName("JavaScript");
        
        Object eval = engine.eval(script);
        
        return eval;
    }
    
    public static boolean judge(String script) throws ScriptException
    {
        return (boolean) eval(script);
    }
    
    public static double compute(String script) throws ScriptException
    {
        return (double) eval(script);
        
    }
}
