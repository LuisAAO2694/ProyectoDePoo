package com.iteso.logic;

public abstract class BinaryOperator extends Operator
{
    //And(A,B)
    private Expresion left; //Desacoplamos, podemos tener cualquier hijo
    private Expresion right;

    public BinaryOperator(Expresion left, Expresion right)
    {
        this.left = left;
        this.right = right;
    }

    public Expresion getLeft()
    {
        return left;
    }

    public Expresion getRight()
    {
        return right;
    }
}
