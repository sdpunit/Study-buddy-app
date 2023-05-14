package com.studybuddy.search;

import java.util.ArrayList;
import java.util.Arrays;

public class Colleges {
    public ArrayList<String> colleges = new ArrayList<String>(
            Arrays.asList(
                    "meas",
                    "mgmt",
                    "cbea",
                    "biol",
                    "hist",
                    "phys",
                    "busn",
                    "infs",
                    "asia",
                    "acst",
                    "stat",
                    "engl",
                    "laws",
                    "ling",
                    "comp",
                    "psyc",
                    "econ",
                    "emet",
                    "engn",
                    "finm",
                    "arch",
                    "math",
                    "chem",
                    "clas",
                    "envs",
                    "socy",
                    "socr",
                    "bian",
                    "arth",
                    "idec",
                    "crim",
                    "stst",
                    "poph",
                    "emsc",
                    "anch",
                    "artv",
                    "chmd",
                    "anth",
                    "cecs",
                    "mktg",
                    "crwf",
                    "mmib",
                    "emdv",
                    "busi",
                    "astr",
                    "pasi",
                    "indg",
                    "anip",
                    "nspo",
                    "pols",
                    "humn",
                    "vcpg",
                    "medn",
                    "regn",
                    "pogo",
                    "neur",
                    "intr",
                    "musc",
                    "scom",
                    "dipl",
                    "desn",
                    "musi",
                    "arts",
                    "scnc",
                    "demo",
                    "gend"
            ));

    public ArrayList<String> getColleges() {
        return colleges;
    }

    public boolean contains(String stringToCheck) {
        return colleges.contains(stringToCheck);
    }
}
