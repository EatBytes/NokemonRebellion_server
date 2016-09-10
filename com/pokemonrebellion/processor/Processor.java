package com.pokemonrebellion.processor;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

abstract class Processor {
    protected byte[] getOutputStream(int[] x) {
        ByteArrayOutputStream baos= new ByteArrayOutputStream();
        DataOutputStream daos = new DataOutputStream(baos);

        try {
            for (int i: x) {
                daos.writeInt(i);
            }
        } catch (IOException e) {
            e.getStackTrace();
        }

        return baos.toByteArray();
    }
}
