package com.javarush.chebotarev;

import java.util.HashMap;
import java.util.Map;

public class Alphabet {

    public static Alphabet getInstance() {
        return instance;
    }

    public boolean contains(char character) {
        return indexMap.containsKey(character);
    }

    public int getIndex(char character) {
        return indexMap.get(character);
    }

    public int getLength() {
        return CHARACTERS.length;
    }

    public char getCharacter(int index) {
        return CHARACTERS[index];
    }

    private Alphabet() {
        for (int i = 0; i < CHARACTERS.length; i++) {
            indexMap.put(CHARACTERS[i], i);
        }
    }

    private static final Alphabet instance = new Alphabet();
    private final Map<Character, Integer> indexMap = new HashMap<>();
    private final char[] CHARACTERS = {
            'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ё', 'Ж',
            'З', 'И', 'Й', 'К', 'Л', 'М', 'Н', 'О',
            'П', 'Р', 'С', 'Т', 'У', 'Ф', 'Х', 'Ц',
            'Ч', 'Ш', 'Щ', 'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я',
            'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж',
            'з', 'и', 'й', 'к', 'л', 'м', 'н', 'о',
            'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц',
            'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я',
            '.', ',', ';', '-', '«', '»', '(', ')', '"', '\'', ':', '!', '?', ' ', '\n'};
}
