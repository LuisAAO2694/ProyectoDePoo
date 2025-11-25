package com.iteso.logic;

public class Constant extends Operator
{
    private final boolean value;

    public Constant(boolean value)
    {
        this.value = value;
    }

    @Override
    public boolean getValue()
    {
        return value;
    }
}
