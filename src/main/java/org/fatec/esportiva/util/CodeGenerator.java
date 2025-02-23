package org.fatec.esportiva.util;

import lombok.experimental.UtilityClass;

import java.util.UUID;

public class CodeGenerator {
    public static String generateCode(Integer length){
        return UUID.randomUUID().toString().replace("-", "").substring(0, length);
    }
}
