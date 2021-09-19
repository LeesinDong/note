package com.leesin.java8.me.design.model.策略;

import java.util.Arrays;
import java.util.List;

public interface Original {

    void compute(int x, int y);

    class Sum implements Original {

        @Override
        public void compute(int x, int y) {
        }
    }

    class Reduction implements Original {

        @Override
        public void compute(int x, int y) {
        }
    }

    class Multiply implements Original {

        @Override
        public void compute(int x, int y) {
        }
    }

    public static void main(String[] args) {
        List<Original> list = Arrays.asList(new Sum(), new Reduction(), new Multiply());
        for (int i = 0; i < list.size(); i++) {
            list.get(i).compute(1, 2);
        }
    }

}
