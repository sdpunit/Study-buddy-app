package com.studybuddy;


import static com.studybuddy.search.Token.Type.CODE;
import static com.studybuddy.search.Token.Type.COLLEGE;
import static com.studybuddy.search.Token.Type.COLLEGECODE;
import static com.studybuddy.search.Token.Type.CONVENER;
import static com.studybuddy.search.Token.Type.COURSE;
import static org.junit.Assert.assertEquals;

import com.studybuddy.search.Token;
import com.studybuddy.search.Tokenizer;

import org.junit.Test;

public class TokenizerTest {

    private static Tokenizer tokenizer;
    private static final String errorToken = "wrong token";
    private static final String errorType = "wrong token type";

    @Test(timeout=1000)
    public void testTokenizeCode() {
        String s = "2100";
        tokenizer = new Tokenizer(s);
        assertEquals(errorType, CODE, tokenizer.current().getType());
        assertEquals(errorToken, s, tokenizer.current().getToken());

        s = "3600";
        tokenizer = new Tokenizer(s);
        assertEquals(errorType, CODE, tokenizer.current().getType());
        assertEquals(errorToken, s, tokenizer.current().getToken());
    }

    @Test(timeout=1000)
    public void testTokenizeCollege() {
        Token.Type t = COLLEGE;

        String s = "COMP";
        tokenizer = new Tokenizer(s);
        assertEquals(errorType, t, tokenizer.current().getType());
        assertEquals(errorToken, s.toLowerCase(), tokenizer.current().getToken());

        s = "college= COMP";
        tokenizer = new Tokenizer(s);
        assertEquals(errorType, t, tokenizer.current().getType());
        assertEquals(errorToken, s.toLowerCase(), tokenizer.current().getToken());

        s = "college= biOL";
        tokenizer = new Tokenizer(s);
        assertEquals(errorType, t, tokenizer.current().getType());
        assertEquals(errorToken, s.toLowerCase(), tokenizer.current().getToken());

        s = "cRim";
        tokenizer = new Tokenizer(s);
        assertEquals(errorType, t, tokenizer.current().getType());
        assertEquals(errorToken, s.toLowerCase(), tokenizer.current().getToken());
    }

    @Test(timeout=1000)
    public void testTokenizeCollegeCode() {
        Token.Type t = COLLEGECODE;

        String s = "COMP 2100";
        tokenizer = new Tokenizer(s);
        assertEquals(errorType, t, tokenizer.current().getType());
        assertEquals(errorToken, s.toLowerCase(), tokenizer.current().getToken());

        s = "MATH 1013";
        tokenizer = new Tokenizer(s);
        assertEquals(errorType, t, tokenizer.current().getType());
        assertEquals(errorToken, s.toLowerCase(), tokenizer.current().getToken());

        s = "InTr 1013";
        tokenizer = new Tokenizer(s);
        assertEquals(errorType, t, tokenizer.current().getType());
        assertEquals(errorToken, s.toLowerCase(), tokenizer.current().getToken());

        s = "COMP2100";
        tokenizer = new Tokenizer(s);
        assertEquals(errorType, t, tokenizer.current().getType());
        assertEquals(errorToken, s.toLowerCase(), tokenizer.current().getToken());
    }

    @Test(timeout=1000)
    public void testTokenizeCourse() {
        Token.Type t = COURSE;

        String s = "Software Design Methodologies";
        tokenizer = new Tokenizer(s);
        assertEquals(errorType, t, tokenizer.current().getType());
        assertEquals(errorToken, s.toLowerCase(), tokenizer.current().getToken());

        s = "ApPLied MatheMatics I";
        tokenizer = new Tokenizer(s);
        assertEquals(errorType, t, tokenizer.current().getType());
        assertEquals(errorToken, s.toLowerCase(), tokenizer.current().getToken());
    }

    @Test(timeout=1000)
    public void testTokenizeConvener() {
        Token.Type t = CONVENER;

        String s = "convener= bernando";
        tokenizer = new Tokenizer(s);
        assertEquals(errorType, t, tokenizer.current().getType());
        assertEquals(errorToken, s.toLowerCase(), tokenizer.current().getToken());

        s = "CONVENER= Alice";
        tokenizer = new Tokenizer(s);
        assertEquals(errorType, t, tokenizer.current().getType());
        assertEquals(errorToken, s.toLowerCase(), tokenizer.current().getToken());

        s = "COnVEneR= Brett";
        tokenizer = new Tokenizer(s);
        assertEquals(errorType, t, tokenizer.current().getType());
        assertEquals(errorToken, s.toLowerCase(), tokenizer.current().getToken());
    }

    @Test(timeout=1000)
    public void testTokenizeMixed() {
        String s = "2100, Software Design Methodologies, COMP";
        tokenizer = new Tokenizer(s);
        assertEquals(errorType, CODE, tokenizer.current().getType());
        assertEquals(errorToken, "2100", tokenizer.current().getToken());
        tokenizer.next();
        assertEquals(errorType, COURSE, tokenizer.current().getType());
        assertEquals(errorToken, "software design methodologies", tokenizer.current().getToken());
        tokenizer.next();
        assertEquals(errorType, COLLEGE, tokenizer.current().getType());
        assertEquals(errorToken, "comp", tokenizer.current().getToken());

        s = "COMP 2100, Software Design Methodologies, convener= bernando";
        tokenizer = new Tokenizer(s);
        assertEquals(errorType, COLLEGECODE, tokenizer.current().getType());
        assertEquals(errorToken, "comp 2100", tokenizer.current().getToken());
        tokenizer.next();
        assertEquals(errorType, COURSE, tokenizer.current().getType());
        assertEquals(errorToken, "software design methodologies", tokenizer.current().getToken());
        tokenizer.next();
        assertEquals(errorType, CONVENER, tokenizer.current().getType());
        assertEquals(errorToken, "convener= bernando", tokenizer.current().getToken());
    }

    @Test(timeout=1000)
    public void testNullToken() {
        tokenizer = new Tokenizer("COMP 2I00");
        assertEquals(errorType, null, tokenizer.current());

        tokenizer = new Tokenizer("ABCD 2I00");
        assertEquals(errorType, null, tokenizer.current());

        tokenizer = new Tokenizer("ABCD 3455");
        assertEquals(errorType, null, tokenizer.current());

        tokenizer = new Tokenizer("Applied Mathematics 1");
        assertEquals(errorType, null, tokenizer.current());

        tokenizer = new Tokenizer("COMP 2100, Software Design Methodologies, convenner= bernando");
        tokenizer.next();
        tokenizer.next();
        tokenizer.next();
        assertEquals(errorType, null, tokenizer.current());
    }

}
