package com.iteso.logic;

public class Variable extends Constant
{
    //new Variable(A, true)
    private final char name;

    public Variable(char name, boolean value)
    {
        super(value);
        this.name = name;
    }
}
