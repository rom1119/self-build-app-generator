package com.SelfBuildApp.ddd.Support.infrastructure.Generator;

import net.bytebuddy.utility.RandomString;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ShortUUIDGenerator {

    public String generate()
    {
        Random r = new Random();

        String alphabet = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";
        String str = String.valueOf(alphabet.charAt(r.nextInt(alphabet.length())));
        str += RandomString.make(7);

        return str;
    }
}
