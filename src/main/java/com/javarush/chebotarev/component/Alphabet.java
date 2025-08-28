package com.javarush.chebotarev.component;

import java.util.HashMap;
import java.util.Map;

public class Alphabet {

    public Alphabet() {
        for (int i = 0; i < CHARACTERS.length; i++) {
            indexMap.put(CHARACTERS[i], i);
        }
    }

    public int getIndex(char character) {
        Integer index = indexMap.get(character);
        return (index != null) ? index : -1;
    }

    public int getLength() {
        return CHARACTERS.length;
    }

    public char getCharacter(int index) {
        return CHARACTERS[index];
    }

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
