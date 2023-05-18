package com.studybuddy;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import com.studybuddy.search.Query;
import com.studybuddy.search.SearchParser;
import com.studybuddy.search.Tokenizer;

import org.junit.Test;

public class SearchParserTest {

    public String makeString(Query q) {
        return q.getCollege()+","+q.getCode()+","+q.getCourse()+","+q.getConvener();
    }

    @Test(timeout=1000)
    public void testQueryCode() {
        Tokenizer tk = new Tokenizer("COMP,2100");
        SearchParser sp = new SearchParser(tk);
        Query expected = new Query("comp",2100,null,null);
        Query actual = sp.parseQuery();
        assertEquals(makeString(expected),makeString(actual));

        tk=new Tokenizer("COMP 2100");
        sp = new SearchParser(tk);
        expected = new Query("comp",2100,null,null);
        actual = sp.parseQuery();
        assertEquals(makeString(expected),makeString(actual));
    }

    @Test(timeout=1000)
    public void testQueryCourse() {
        Tokenizer tk=new Tokenizer("COMP 2100, Software Design Methodologies");
        SearchParser sp = new SearchParser(tk);
        Query expected = new Query("comp",2100,"software design methodologies",null);
        Query actual = sp.parseQuery();
        assertEquals(makeString(expected),makeString(actual));
    }

    @Test(timeout=1000)
    public void testQueryConvener() {
        Tokenizer tk =new Tokenizer("COMP 2100, Software Design Methodologies,convener= bernando");
        SearchParser sp = new SearchParser(tk);
        Query expected = new Query("comp",2100,"software design methodologies","bernando");
        Query actual = sp.parseQuery();
        assertEquals(makeString(expected),makeString(actual));

        tk=new Tokenizer("COMP 2100, convener= bernando");
        sp = new SearchParser(tk);
        expected = new Query("comp",2100,null,"bernando");
        actual = sp.parseQuery();
        assertEquals(makeString(expected),makeString(actual));

        tk=new Tokenizer("COMP, 2100, Software Design Methodologies,convener= bernando");
        sp = new SearchParser(tk);
        expected = new Query("comp",2100,"software design methodologies","bernando");
        actual = sp.parseQuery();
        assertEquals(makeString(expected),makeString(actual));

        tk=new Tokenizer("COMP, Software Design Methodologies,convener= bernando");
        sp = new SearchParser(tk);
        expected = new Query("comp",0,"software design methodologies","bernando");
        actual = sp.parseQuery();
        assertEquals(makeString(expected),makeString(actual));
    }

    @Test(timeout=1000)
    public void testIllegalArgumentException() {

        assertThrows(IllegalArgumentException.class, () -> {
            Tokenizer tk=new Tokenizer("CRIM, 999");
            SearchParser sp = new SearchParser(tk);
            sp.parseQuery();
        });

//        assertThrows(IllegalArgumentException.class, () -> {
//            Tokenizer tk=new Tokenizer("DCBA");
//            SearchParser sp = new SearchParser(tk);
//            sp.parseQuery();
//        });

        assertThrows(IllegalArgumentException.class, () -> {
            Tokenizer tk=new Tokenizer("ABCD 1234");
            SearchParser sp = new SearchParser(tk);
            sp.parseQuery();
        });
    }
}
