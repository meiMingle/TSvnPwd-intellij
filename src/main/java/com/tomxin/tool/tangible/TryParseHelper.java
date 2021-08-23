package com.tomxin.tool.tangible;

//----------------------------------------------------------------------------------------
//	Copyright © 2007 - 2017 Tangible Software Solutions Inc.
//	This class can be used by anyone provided that the copyright notice remains intact.
//
//	This class is used to convert some of the C# TryParse methods to Java.
//----------------------------------------------------------------------------------------
public final class TryParseHelper {
    public static boolean tryParseInt(String s, RefObject<Integer> result) {
        try {
            result.argValue = Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}