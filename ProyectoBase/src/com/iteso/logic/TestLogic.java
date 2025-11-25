package com.iteso.logic;

public class TestLogic
{
    public static void main(String[] args)
    {
        Variable va = new Variable('A', true);
        Variable vb = new Variable('B', false);

        Variable vc = new Variable('C', false);
        Variable vd = new Variable('D', true);

        And and = new And(va, vb);
        System.out.printf("A ^ B = "  + and.getValue());
        System.out.printf("\n");

        Or or = new Or(and, vc);
        System.out.printf("(A ^ B)  v C = "  + or.getValue());
        System.out.printf("\n");

        And and2 = new And(and, vc);
        System.out.printf("((A ^ B)  v C ) ^ D = "  + and2.getValue());
        System.out.printf("\n");

    }
}
