public class ShapesAndRotations {

    public static boolean[][][][] getL(){
        boolean[][][][] LInt =

                {{{{true}, {true}, {true}, {true}}, {{true}, {false}, {false}, {false}}},
                {{{true, true, true, true}}, {{true, false, false, false}}},
                {{{true}, {true}, {true}, {true}}, {{false}, {false}, {false}, {true}}},
                {{{true, true, true, true}}, {{false, false, false, true}}},
                {{{true}, {false}, {false}, {false}}, {{true}, {true}, {true}, {true}}},
                {{{true, false, false, false}}, {{true, true, true, true}}},
                {{{false}, {false}, {false}, {true}}, {{true}, {true}, {true}, {true}}},
                {{{false, false, false, true}}, {{true, true, true, true}}},
                {{{true, true}, {true, false}, {true, false}, {true, false}}},
                {{{true, false, false, false}, {true, true, true, true}}},
                {{{false, true}, {false, true}, {false, true}, {true, true}}},
                {{{true, true, true, true}, {false, false, false, true}}},
                {{{true, true}, {false, true}, {false, true}, {false, true}}},
                {{{true, true, true, true}, {true, false, false, false}}},
                {{{true, false}, {true, false}, {true, false}, {true, true}}},
                {{{false, false, false, true}, {true, true, true, true}}},
                {{{true}, {false}}, {{true}, {false}}, {{true}, {false}}, {{true}, {true}}},
                {{{true, false}}, {{true, false}}, {{true, false}}, {{true, true}}},
                {{{false}, {true}}, {{false}, {true}}, {{false}, {true}}, {{true}, {true}}},
                {{{false, true}}, {{false, true}}, {{false, true}}, {{true, true}}},
                {{{true}, {true}}, {{true}, {false}}, {{true}, {false}}, {{true}, {false}}},
                {{{true, true}}, {{true, false}}, {{true, false}}, {{true, false}}},
                {{{true}, {true}}, {{false}, {true}}, {{false}, {true}}, {{false}, {true}}},
                {{{true, true}}, {{false, true}}, {{false, true}}, {{false, true}}}};

        return LInt;
    }

    public static boolean[][][][] getP(){
        boolean[][][][] PInt =

                {{{{true, true}, {true, true}, {true, false}}},
                {{{true, true, false}, {true, true, true}}},
                {{{false, true}, {true, true}, {true, true}}},
                {{{true, true, true}, {false, true, true}}},
                {{{true, true}, {true, true}, {false, true}}},
                {{{true, true, true}, {true, true, false}}},
                {{{true, false}, {true, true}, {true, true}}},
                {{{false, true, true}, {true, true, true}}},
                {{{true}, {true}, {false}}, {{true}, {true}, {true}}},
                {{{true, true, false}}, {{true, true, true}}},
                {{{false}, {true}, {true}}, {{true}, {true}, {true}}},
                {{{false, true, true}}, {{true, true, true}}},
                {{{true}, {true}, {true}}, {{true}, {true}, {false}}},
                {{{true, true, true}}, {{true, true, false}}},
                {{{true}, {true}, {true}}, {{false}, {true}, {true}}},
                {{{true, true, true}}, {{false, true, true}}},
                {{{true}, {false}}, {{true}, {true}}, {{true}, {true}}},
                {{{true, false}}, {{true, true}}, {{true, true}}},
                {{{false}, {true}}, {{true}, {true}}, {{true}, {true}}},
                {{{false, true}}, {{true, true}}, {{true, true}}},
                {{{true}, {true}}, {{true}, {true}}, {{false}, {true}}},
                {{{true, true}}, {{true, true}}, {{false, true}}},
                {{{true}, {true}}, {{true}, {true}}, {{true}, {false}}},
                {{{true, true}}, {{true, true}}, {{true, false}}}};

        return PInt;
    }

    public static boolean[][][][] getT(){
        boolean[][][][] TInt =

            {{{{true, true, true}, {false, true, false}, {false, true, false}}},
            {{{true, false, false}, {true, true, true}, {true, false, false}}},
            {{{false, true, false}, {false, true, false}, {true, true, true}}},
            {{{false, false, true}, {true, true, true}, {false, false, true}}},
            {{{true}, {false}, {false}}, {{true}, {true}, {true}}, {{true}, {false}, {false}}},
            {{{true, false, false}}, {{true, true, true}}, {{true, false, false}}},
            {{{false}, {false}, {true}}, {{true}, {true}, {true}}, {{false}, {false}, {true}}},
            {{{false, false, true}}, {{true, true, true}}, {{false, false, true}}},
            {{{false}, {true}, {false}}, {{false}, {true}, {false}}, {{true}, {true}, {true}}},
            {{{false, true, false}}, {{false, true, false}}, {{true, true, true}}},
            {{{true}, {true}, {true}}, {{false}, {true}, {false}}, {{false}, {true}, {false}}},
            {{{true, true, true}}, {{false, true, false}}, {{false, true, false}}}};

        return TInt;
    }

    public static boolean[][][][] getA(){
        boolean[][][][] AInt =

                {{{{true,true,true,true},{true,true,true,true}},{{true,true,true,true},{true,true,true,true}}},
                {{{true,true},{true,true},{true,true},{true,true}},{{true,true},{true,true},{true,true},{true,true}}},
                {{{true,true},{true,true}},{{true,true},{true,true}},{{true,true},{true,true}},{{true,true},{true,true}}}};

        return AInt;
    }

    public static boolean[][][][] getB(){
        boolean[][][][] BInt =

                {{{{true,true,true,true},{true,true,true,true},{true,true,true,true}},{{true,true,true,true},{true,true,true,true},{true,true,true,true}}},
                {{{true,true,true},{true,true,true},{true,true,true},{true,true,true}},{{true,true,true},{true,true,true},{true,true,true},{true,true,true}}},
                {{{true,true},{true,true},{true,true}},{{true,true},{true,true},{true,true}},{{true,true},{true,true},{true,true}},{{true,true},{true,true},{true,true}}},
                {{{true,true,true,true},{true,true,true,true}},{{true,true,true,true},{true,true,true,true}},{{true,true,true,true},{true,true,true,true}}},
                {{{true,true,true},{true,true,true}},{{true,true,true},{true,true,true}},{{true,true,true},{true,true,true}},{{true,true,true},{true,true,true}}},
                {{{true,true},{true,true},{true,true},{true,true}},{{true,true},{true,true},{true,true},{true,true}},{{true,true},{true,true},{true,true},{true,true}}}};

        return BInt;
    }

    public static boolean[][][][] getC(){
        boolean[][][][] CInt =

                {{{{true,true,true},{true,true,true},{true,true,true}},{{true,true,true},{true,true,true},{true,true,true}},{{true,true,true},{true,true,true},{true,true,true}}}};

        return CInt;
    }

}
